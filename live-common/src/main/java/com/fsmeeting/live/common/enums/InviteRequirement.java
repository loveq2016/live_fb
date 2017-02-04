package com.fsmeeting.live.common.enums;

/**
 * 是否需要邀请码
 * 
 * @author yicai.liu<moon>
 *
 */
public enum InviteRequirement {

	/**
	 * 不需要
	 */
	NO(0),

	/**
	 * 需要
	 */
	YES(1);

	private int code;

	private InviteRequirement(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
