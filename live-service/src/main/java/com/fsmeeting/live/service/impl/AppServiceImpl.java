package com.fsmeeting.live.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastonz.live.sliceprotocol.model.AppHeartbeatModel;
import com.fastonz.live.sliceprotocol.model.AppModel;
import com.fsmeeting.live.common.Constants;
import com.fsmeeting.live.common.bean.LiveRoomRuntime;
import com.fsmeeting.live.common.bean.LiveService;
import com.fsmeeting.live.common.enums.LiveServerType;
import com.fsmeeting.live.load.strategy.LoadStrategyContext;
import com.fsmeeting.live.redis.AppRedisDao;
import com.fsmeeting.live.redis.LiveRoomRedisDao;
import com.fsmeeting.live.service.IAppService;

@Service("appService")
public class AppServiceImpl implements IAppService {

	private static final Logger LOG = LoggerFactory.getLogger(AppServiceImpl.class);

	@Autowired
	private AppRedisDao appRedisDao;

	@Autowired
	private LiveRoomRedisDao liveRoomRedisDao;

	@Autowired
	private LoadStrategyContext loadStrategyContext;

	@Override
	public boolean register(AppModel model) throws Exception {

		LiveService liveService = new LiveService();
		liveService.setAddress(model.getAddress());
		liveService.setAppId(model.getAppId());
		liveService.setAppType(model.getAppType());
		liveService.setLoad(model.getWeight());
		liveService.setDesc(model.getDesc());

		// 服务重启但未超时，需要更新服务信息
		if (appRedisDao.isRegistered(liveService.getAppId())) {
			LOG.info("服务" + liveService.getAppId() + "已经注册");
			appRedisDao.save(liveService, Constants.SERVICE_HEATBEAT_RATE);
			return false;
		}
		boolean success = appRedisDao.save(liveService, Constants.SERVICE_HEATBEAT_RATE);

		if (success) {
			LOG.info("服务" + liveService.getAppId() + "注册成功");
			return true;
		}
		LOG.info("服务" + liveService.getAppId() + "注册失败");
		return false;

	}

	@Override
	public boolean heartbeat(AppHeartbeatModel model) throws Exception {

		String appId = model.getAppId();
		if (!appRedisDao.isRegistered(appId)) {
			LOG.info("心跳检测：服务(" + appId + ")未注册！");
			return false;
		}
		LOG.info("感受心跳：" + appId + ",当前负载：" + model.getCurLoad());
		return appRedisDao.update(model);

	}

	@Override
	public String getMinLoadService(int appType, String oldAddress) {

		LiveService service = loadStrategyContext.getService(appType, oldAddress);
		if (null == service) {
			LOG.info(" Serivice message not found . Type: " + appType);
			return oldAddress;
		}
		return service.getAddress();
	}

	@Override
	public String getLiveSrvAddr(int liveRoomId) {

		// 房间与直播服务器 已经存在
		LiveRoomRuntime room = liveRoomRedisDao.getLiveRoomRuntime(liveRoomId);

		if (null != room) {
			if (null == room.getLivingService() || !appRedisDao.isRegistered(room.getLivingService().getAppId())) {
				LOG.info("live serivice : " + room.getLivingService().getAppId() + "unregistered! need registered.");
				room = null;// 需要更新绑定关系（房间——直播服务）
			}
		}

		if (null == room) {// 获取新的直播服务器
			LiveService liveService = loadStrategyContext.getService(LiveServerType.LIVE.getCode());
			if (null == liveService) {
				LOG.info("Live Service not found!");
				return null;
			} else {
				room = liveRoomRedisDao.markLiveRoomRuntime(liveRoomId, liveService);
			}
			if (null == room) { // 未找到直播服务器地址
				LOG.error("redis save live runtime info error");
				return null;
			}
		}

		return room.getLivingService().getAddress();
	}

}
