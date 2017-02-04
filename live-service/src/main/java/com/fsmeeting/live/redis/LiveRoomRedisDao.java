package com.fsmeeting.live.redis;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.fsmeeting.live.common.Constants;
import com.fsmeeting.live.common.bean.LiveRoomRuntime;
import com.fsmeeting.live.common.bean.LiveService;
import com.fsmeeting.live.common.enums.Increament;
import com.fsmeeting.live.common.enums.LiveRoomStatus;

/**
 * 直播房间 redis 数据访问层
 * 
 * @author yicai.liu
 * @version
 * @date 2016年11月13日 下午10:11:20
 */
@Component
public class LiveRoomRedisDao extends RedisBaseDao {

	/**
	 * 获取直播运行时房间信息
	 * 
	 * @param liveRoomId
	 * @return
	 */
	public LiveRoomRuntime getLiveRoomRuntime(int liveRoomId) {
		String strVal = hget(Constants.Redis.Key.LIVEROOM_INFO_MAP, String.valueOf(liveRoomId));
		return JSON.parseObject(strVal, LiveRoomRuntime.class);
	}

	/**
	 * 保存直播运行时房间信息
	 * 
	 * @param liveRoomId
	 * @param liveService
	 * @return
	 */
	public LiveRoomRuntime markLiveRoomRuntime(int liveRoomId, LiveService liveService) {
		LiveRoomRuntime runtime = new LiveRoomRuntime();
		runtime.setLiveRoomId(0);
		runtime.setActiveTime(new Date());
		runtime.setLivingService(liveService);
		runtime.setStatus(LiveRoomStatus.ACTIVE.getCode());
		String value = JSON.toJSONString(runtime);
		hset(Constants.Redis.Key.LIVEROOM_INFO_MAP, String.valueOf(liveRoomId), value);
		return runtime;
	}

	/**
	 * 保存直播房间用户点数
	 * 
	 * @param liveRoomId
	 * @return
	 */
	public long getRoomPoint(int liveRoomId) {
		String point = get(Constants.Redis.Key.TOKEN_COUNT_IN_LIVEROOM_PREFIXER + liveRoomId);
		if (StringUtils.isBlank(point)) {
			return 0;
		}
		return Long.parseLong(point);

	}

	/**
	 * 获取正在运行的房间列表
	 * 
	 * @return
	 */
	public Set<String> runtimeRooms() {
		return hkeys(Constants.Redis.Key.LIVEROOM_INFO_MAP);
	}

	/**
	 * 删除房间动态信息
	 * 
	 * @param liveRoomId
	 * @return
	 */
	public boolean removeRoomRuntime(int liveRoomId) {
		hdel(Constants.Redis.Key.LIVEROOM_INFO_MAP, String.valueOf(liveRoomId));
		remove(Constants.Redis.Key.TOKEN_COUNT_IN_LIVEROOM_PREFIXER + liveRoomId);
		return true;
	}

	/**
	 * room point + -
	 * 
	 * @param liveRoomId
	 * @param point
	 * @param symbol
	 *            点数操作方式
	 * @return 做增量操作后的值
	 */
	public long processRoomPointIncreament(int liveRoomId, long point, Increament symbol) {
		switch (symbol) {
		case MINUS:
			point *= -1;
			break;
		default:
			break;
		}
		if (point == 1) {
			return increase(Constants.Redis.Key.TOKEN_COUNT_IN_LIVEROOM_PREFIXER + liveRoomId);
		}
		return increase(Constants.Redis.Key.TOKEN_COUNT_IN_LIVEROOM_PREFIXER + liveRoomId, point);
	}

}