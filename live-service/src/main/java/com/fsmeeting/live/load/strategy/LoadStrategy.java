package com.fsmeeting.live.load.strategy;

import com.fsmeeting.live.common.bean.LiveService;

/**
 * 负载策略
 * 
 * @author yicai.liu<moon>
 *
 */
public abstract class LoadStrategy {

	/**
	 * 获取负载服务
	 * 
	 * @param params
	 *            预留
	 * @return T 对象
	 */
	public abstract LiveService getService(int appType, String oldAddress, Object... params);

	public LiveService getService(int appType, Object... params) {
		return getService(appType, null, params);
	}
}
