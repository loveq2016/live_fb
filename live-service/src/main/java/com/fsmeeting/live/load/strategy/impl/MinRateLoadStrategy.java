package com.fsmeeting.live.load.strategy.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.fsmeeting.live.common.Constants;
import com.fsmeeting.live.common.bean.LiveService;
import com.fsmeeting.live.load.strategy.LoadStrategy;
import com.fsmeeting.live.redis.RedisBaseDao;

/**
 * 代理服务器最小策略
 * 
 * @author yicai.liu<moon>
 *
 */
@Component("minRateLoad")
public class MinRateLoadStrategy extends LoadStrategy {

	private static final Logger LOG = LoggerFactory.getLogger(MinRateLoadStrategy.class);

	@Autowired
	private RedisBaseDao redisBaseDao;

	@Override
	public LiveService getService(int appType, String oldAddress, Object... params) {
		// 获取所有存活的服务 TODO 模糊匹配改造
		Set<String> appIds = redisBaseDao.keyLike(Constants.Redis.Key.SERVICE_ACTIVE_PRIFIXER + "*");
		
		List<LiveService> services = new ArrayList<>();
		for (String appId : appIds) {
			String strVal = redisBaseDao.get(appId);
			LiveService service = JSON.parseObject(strVal, LiveService.class);
			if ((appType == service.getAppType()) && (null == oldAddress || !oldAddress.equals(service.getAddress()))) {
				services.add(service);
			}
		}
		if (services.size() <= 0) {
			LOG.info("负载地址未找到!");
			return null;
		}

		Collections.sort(services, new Comparator<LiveService>() {

			@Override
			public int compare(LiveService left, LiveService right) {

				BigDecimal leftLoadRate = new BigDecimal(Integer.toString(left.getCurLoad()))
						.divide(new BigDecimal(Integer.toString(left.getLoad())), 4, RoundingMode.HALF_UP);

				BigDecimal rightLoadRate = new BigDecimal(Integer.toString(right.getCurLoad()))
						.divide(new BigDecimal(Integer.toString(right.getLoad())), 4, RoundingMode.HALF_UP);

				return leftLoadRate.compareTo(rightLoadRate);
			}

		});

		LOG.info("Pick the first one:" + new BigDecimal(services.get(0).getCurLoad())
				.divide(new BigDecimal(services.get(0).getLoad()), 4, RoundingMode.HALF_UP).toString());

		return services.get(0);

	}

}
