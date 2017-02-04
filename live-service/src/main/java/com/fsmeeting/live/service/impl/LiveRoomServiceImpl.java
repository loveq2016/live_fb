package com.fsmeeting.live.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastonz.live.sliceprotocol.model.LiveRoomModel;
import com.fsmeeting.live.common.bean.SystemInfo;
import com.fsmeeting.live.common.enums.Increament;
import com.fsmeeting.live.common.util.DateTimeUtils;
import com.fsmeeting.live.mapper.LiveRoomMapper;
import com.fsmeeting.live.redis.LiveRoomRedisDao;
import com.fsmeeting.live.redis.TokenRedisDao;
import com.fsmeeting.live.service.ILiveRoomService;

@Service
public class LiveRoomServiceImpl implements ILiveRoomService {

	private static final Logger log = LoggerFactory.getLogger(LiveRoomServiceImpl.class);

	@Autowired
	private LiveRoomRedisDao liveRoomRedisDao;

	@Autowired
	private TokenRedisDao tokenRedisDao;

	@Autowired
	private LiveRoomMapper liveRoomMapper;

	@Autowired
	private SystemInfo systemInfo;

	@Override
	public LiveRoomModel getLiveRoom(int liveRoomId) {

		LiveRoomModel room = liveRoomMapper.getLiveRoom(liveRoomId);
		if (null == room) {
			return room;
		}
		room.setStartTime(DateTimeUtils.format(room.getStartTime(), DateTimeUtils.DATEANDTIME));
		room.setEndTime(DateTimeUtils.format(room.getEndTime(), DateTimeUtils.DATEANDTIME));
		room.setConfRoomAddr(systemInfo.getPrepositive());
		return room;
	}

	/**
	 * 
	 * 冗余设计TODO ,do nothing, 运行时房间信息只有存不存在(活or死) 的道理
	 */
	@Override
	public boolean activeLiveRoom(int liveRoomId) {
		// TODO
		log.info("Active LiveRoom:" + liveRoomId);
		return true;
	}

	@Override
	public boolean releaseLiveRoom(int liveRoomId) {
		liveRoomRedisDao.removeRoomRuntime(liveRoomId);
		tokenRedisDao.releaseRoomTokens(liveRoomId);
		log.info("Delete LiveRoom:" + liveRoomId);
		return true;
	}

	@Override
	public boolean calibrateRuntimeRoomPoint() {
		Set<String> roomIds = liveRoomRedisDao.runtimeRooms();
		for (String roomId : roomIds) {
			int liveRoomId = Integer.parseInt(roomId);
			long point = invalidLiveRoomUsers(liveRoomId);
			if (point > 0) {
				liveRoomRedisDao.processRoomPointIncreament(liveRoomId, point, Increament.MINUS);
				log.info("清除房间：" + liveRoomId + "无效用户点数:" + point);
			}
		}
		log.info("成功校准" + roomIds.size() + "个房间的点数");
		return true;
	}

	/**
	 * 计算房间失效的用户数
	 */
	private long invalidLiveRoomUsers(int liveRoomId) {
		return tokenRedisDao.removeInvalidToken(liveRoomId);
	}

}
