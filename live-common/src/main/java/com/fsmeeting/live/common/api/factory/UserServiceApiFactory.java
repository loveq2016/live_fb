package com.fsmeeting.live.common.api.factory;

import com.fsmeeting.live.common.api.IUserServiceApi;
import com.fsmeeting.live.common.enums.API;

/**
 * API 工厂
 * 
 * @author yicai.liu<moon>
 *
 */
public class UserServiceApiFactory {

	/**
	 * 获取API具体实现
	 * 
	 * @param api
	 *            api类型
	 * @return
	 */
	public static IUserServiceApi create(API api) {
		try {
			Object obj = api.getCode().newInstance();
			if (obj instanceof IUserServiceApi) {
				return (IUserServiceApi) obj;
			}
			throw new RuntimeException("API not found");
		} catch (Exception e) {
			throw new RuntimeException("API not found", e);
		}

	}

	public static void main(String[] args) {
		UserServiceApiFactory.create(API.CLOUD).process();
	}
}
