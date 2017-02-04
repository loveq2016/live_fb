package com.fsmeeting.live.common.api.cloud;

import com.fsmeeting.live.common.api.IUserServiceApi;

/**
 * 云会议实现
 * 
 * @author yicai.liu<moon>
 *
 */
public class CloudUserServiceApi implements IUserServiceApi {

	@Override
	public void process() {
		System.out.println(getClass().getSimpleName());
	}
}
