package com.fsmeeting.live.service;

import java.util.List;

import com.fastonz.live.sliceprotocol.model.TokenModel;
import com.fsmeeting.live.common.bean.LiveRoom;
import com.fsmeeting.live.common.bean.ResponseMsg;
import com.fsmeeting.live.common.bean.vo.LoginRequestVO;
import com.fsmeeting.live.common.bean.vo.LoginResponseVO;

/**
 * 用户业务
 * 
 * @author yicai.liu<moon>
 *
 */
public interface IUserService {

	/**
	 * 获取直播房间信息
	 * 
	 * @param 直播URL
	 */
	ResponseMsg<LiveRoom> getLiveRoomInfo(String liveURI);

	/**
	 * 登陆业务
	 * 
	 * @param liveURI
	 *            直播URL
	 * @param bean
	 *            登录请求post信息
	 * @return
	 */
	ResponseMsg<LoginResponseVO> login(String liveURI, LoginRequestVO bean);

	/**
	 * 激活用户（校验用户）
	 * 
	 * @param token
	 * @param userId
	 * @return
	 */
	boolean activeToken(String token, int liveRoomId);

	/**
	 * 释放某个房间的token
	 * 
	 * @param liveRoomId
	 * @return
	 */
	boolean releaseTokenByLiveRoom(int liveRoomId);

	/**
	 * 批量释放token
	 * 
	 * @param tokens
	 * @return
	 */
	boolean releaseToken(List<TokenModel> tokens);

	/**
	 * 心跳保活token
	 * 
	 * @param token
	 * @param liveRoomId
	 * @return
	 */
	boolean tokenHeartbeat(String token, int liveRoomId);

}
