package com.fsmeeting.live.common.enums;

import com.fsmeeting.live.common.api.cloud.CloudUserServiceApi;
import com.fsmeeting.live.common.api.cloud.LiveUserServiceApi;

public enum API {

	/**
	 * 云会议API
	 */
	CLOUD(CloudUserServiceApi.class),
	/**
	 * 直播API
	 */
	LIVE(LiveUserServiceApi.class);

	private Class<?> code;

	/**
	 * 
	 * @param code
	 *            枚举API对应的实现类
	 */
	private API(Class<?> code) {
		this.code = code;
	}

	public Class<?> getCode() {
		return code;
	}
}
