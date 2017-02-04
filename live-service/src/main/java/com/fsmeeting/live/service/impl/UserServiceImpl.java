package com.fsmeeting.live.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastonz.live.sliceprotocol.model.TokenModel;
import com.fsmeeting.live.common.bean.LiveRoom;
import com.fsmeeting.live.common.bean.LiveService;
import com.fsmeeting.live.common.bean.ResponseMsg;
import com.fsmeeting.live.common.bean.vo.LoginRequestVO;
import com.fsmeeting.live.common.bean.vo.LoginResponseVO;
import com.fsmeeting.live.common.enums.Increament;
import com.fsmeeting.live.common.enums.InviteRequirement;
import com.fsmeeting.live.common.enums.LiveServerType;
import com.fsmeeting.live.common.enums.Response;
import com.fsmeeting.live.common.util.IDGenerator;
import com.fsmeeting.live.common.util.TokenUtils;
import com.fsmeeting.live.load.strategy.LoadStrategyContext;
import com.fsmeeting.live.mapper.UserMapper;
import com.fsmeeting.live.redis.LiveRoomRedisDao;
import com.fsmeeting.live.redis.TokenRedisDao;
import com.fsmeeting.live.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private LoadStrategyContext loadStrategyContext;

	@Autowired
	private TokenRedisDao tokenRedisDao;

	@Autowired
	private LiveRoomRedisDao liveRoomRedisDao;

	@Override
	public ResponseMsg<LiveRoom> getLiveRoomInfo(String liveURI) {
		ResponseMsg<LiveRoom> result = new ResponseMsg<>();
		LiveRoom room = userMapper.getLiveRoomInfo(liveURI);
		result.setData(room);
		return result;
	}

	@Override
	public ResponseMsg<LoginResponseVO> login(String liveURI, LoginRequestVO request) {

		ResponseMsg<LoginResponseVO> result = new ResponseMsg<>();
		LoginResponseVO loginResp = new LoginResponseVO();
		result.setData(loginResp);

		if (StringUtils.isBlank(request.getNickName())) {
			result.setCode(Response.NICKNAME_NOT_NULL.getCode());
			LOG.error("昵称不能为空！");
			result.setMsg("昵称不能为空！");
			return result;
		}
		// 1 获取直播房间信息
		LiveRoom room = getLiveRoomInfo(liveURI).getData();

		if (null == room) {
			result.setCode(Response.ROOM_NOT_EXIST.getCode());
			LOG.error("直播房间不存在！");
			result.setMsg("直播房间不存在！");
			return result;
		}

		// 2 输入校验（邀请码）
		if (InviteRequirement.YES.getCode() == room.getVerifyMode()) {
			String inviteCode = request.getInviteCode();
			if (!inviteCode.equals(room.getInviteCode())) {
				result.setCode(Response.INVALID_INVITE_CODE.getCode());
				LOG.error("邀请码不正确！");
				result.setMsg("邀请码不正确！");
				return result;
			}
		}

		// 3 点数校验
		int liveRoomId = room.getId();
		long roomUserCount = countRoomUsers(liveRoomId);
		if (roomUserCount >= room.getMaxUserCount()) {
			result.setCode(Response.ROOM_FULL.getCode());
			LOG.error("房间人数已满！");
			result.setMsg("房间人数已满！");
			return result;
		}

		// 4 分配用户ID、token TODO 都要保证唯一性
		int userId = IDGenerator.generate();
		String token = TokenUtils.generate();

		// 5 分派用户代理服务器appId
		LiveService proxy = loadStrategyContext.getService(LiveServerType.PROXY.getCode());
		if (null == proxy) {
			result.setCode(Response.SERVER_NOT_EXIST.getCode());
			LOG.error("昵称不能为空！");
			result.setMsg("代理服务器不存在！");
			return result;
		}

		String appId = proxy.getAppId();
		LOG.info("Proxy info:appId=" + appId + ",address=" + proxy.getAddress());
		loginResp.setRoom(room);
		loginResp.setProxyAddr(proxy.getAddress());
		loginResp.setToken(token);
		loginResp.setUserId(userId);

		// 6 保存token信息
		tokenRedisDao.saveRoomToken(token, liveRoomId);

		return result;
	}

	@Override
	public boolean activeToken(String token, int liveRoomId) {
		boolean success = tokenRedisDao.validateRoomToken(token, liveRoomId);// 校验token
		if (!success) {
			LOG.error("bad token:" + token + "in room:" + liveRoomId);
			return false;
		}
		LOG.info("active token:" + token + "in room:" + liveRoomId);
		tokenRedisDao.token2Room(token, liveRoomId); // 保活房间token
		increaseRoomUserCount(liveRoomId, 1); // 房间点数+1
		return true;
	}

	@Override
	public boolean releaseToken(List<TokenModel> tokens) {
		LOG.info("batch release token start");
		for (TokenModel model : tokens) {
			String token = model.getToken();
			int liveRoomId = model.getLiveRoomId();
			tokenRedisDao.removeToken(token, liveRoomId);// 删除token
			liveRoomRedisDao.processRoomPointIncreament(liveRoomId, 1, Increament.MINUS);// 房间点数-1
		}
		LOG.info("release token count:" + tokens.size());
		return true;
	}

	/**
	 * 
	 * 冗余设计TODO ,do nothing,房间下全量token释放是房间释放的事情
	 */
	@Override
	public boolean releaseTokenByLiveRoom(int liveRoomId) {
		LOG.info("release tokens in room:" + liveRoomId);
		// TODO
		// tokenRedisDao.releaseRoomTokens(liveRoomId);// 房间token
		return true;
	}

	@Override
	public boolean tokenHeartbeat(String token, int liveRoomId) {
		LOG.info("token:" + token + " heartbeat in room:" + liveRoomId);
		return tokenRedisDao.token2Room(token, liveRoomId);
	}

	/**
	 * 计算当前房间人数
	 * 
	 * @param liveRoomId
	 * @return
	 */
	public long countRoomUsers(int liveRoomId) {
		LOG.info("count live room:" + liveRoomId + " users.");
		return liveRoomRedisDao.getRoomPoint(liveRoomId);
	}

	/**
	 * 增加房间用户数
	 * 
	 * @param liveRoomId
	 * @param count
	 * @return
	 */
	public long increaseRoomUserCount(int liveRoomId, long count) {
		return liveRoomRedisDao.processRoomPointIncreament(liveRoomId, count, Increament.PLUS);
	}

}
