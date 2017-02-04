package com.fsmeeting.live.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.fastonz.live.sliceprotocol.model.AppHeartbeatModel;
import com.fsmeeting.live.common.Constants;
import com.fsmeeting.live.common.bean.LiveService;

/**
 * App redis 数据访问层
 * 
 * @author yicai.liu
 * @version
 * @date 2016年11月13日 下午10:11:20
 */
@Component
public class AppRedisDao extends RedisBaseDao {

	/**
	 * 判断app是否已经注册：当且仅当有历史注册、且活跃
	 * 
	 * @param appId
	 *            服务唯一标识
	 * @return
	 */
	public boolean isRegistered(String appId) {
		return contains(Constants.Redis.Key.SERVICE_ACTIVE_PRIFIXER + appId);
	}

	public boolean save(LiveService liveService, int expireSenconds) {
		String liveServiceStr = JSON.toJSONString(liveService);
		return put(Constants.Redis.Key.SERVICE_ACTIVE_PRIFIXER + liveService.getAppId(), liveServiceStr,
				expireSenconds);
	}

	public boolean update(AppHeartbeatModel model) {
		String appId = model.getAppId();
		int curLoad = model.getCurLoad();

		String strVal = get(Constants.Redis.Key.SERVICE_ACTIVE_PRIFIXER + appId);
		LiveService service = JSON.parseObject(strVal, LiveService.class);
		service.setCurLoad(curLoad);
		service.setLastActiveTime(new Date());
		return save(service, Constants.SERVICE_HEATBEAT_RATE);

	}

	public List<LiveService> getAllService() {
		Set<String> appIds = keyLike(Constants.Redis.Key.SERVICE_ACTIVE_PRIFIXER + "*");
		List<LiveService> services = new ArrayList<>();
		for (String appId : appIds) {
			String strVal = get(appId);
			LiveService service = JSON.parseObject(strVal, LiveService.class);
			services.add(service);

		}
		return services;
	}

	public LiveService getService(String id) {
		return JSON.parseObject(Constants.Redis.Key.SERVICE_ACTIVE_PRIFIXER + id, LiveService.class);
	}

}