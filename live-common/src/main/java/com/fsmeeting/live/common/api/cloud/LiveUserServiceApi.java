package com.fsmeeting.live.common.api.cloud;

import com.fsmeeting.live.common.api.IUserServiceApi;

/**
 * 直播API实现
 * 
 * @author yicai.liu<moon>
 *
 */
public class LiveUserServiceApi implements IUserServiceApi {

	@Override
	public void process() {
		System.out.println(getClass().getSimpleName());
	}

}
