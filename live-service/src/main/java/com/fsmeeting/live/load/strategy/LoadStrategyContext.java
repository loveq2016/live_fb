package com.fsmeeting.live.load.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fsmeeting.live.common.bean.LiveService;

/**
 * 策略上下文角色：持有一个Strategy的引用
 * 
 * @author yicai.liu<moon>
 *
 */

@Component
public class LoadStrategyContext {

	/**
	 * 策略
	 */
	@Autowired
	@Qualifier("minRateLoad")
	private LoadStrategy strategy;

	public LiveService getService(int appType, String oldAddress, Object... params) {
		return strategy.getService(appType, oldAddress, params);
	}

	public LiveService getService(int appType, Object... params) {
		return getService(appType, null, params);
	}
}
