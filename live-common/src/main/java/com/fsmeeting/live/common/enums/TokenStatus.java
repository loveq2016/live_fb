package com.fsmeeting.live.common.enums;

/**
 * Token 状态码
 * 
 * @author yicai.liu<moon>
 *
 */
public enum TokenStatus {

	/**
	 * 未激活
	 */
	INACTIVE(0),

	/**
	 * 激活
	 */
	ACTIVE(1),

	/**
	 * 已删除
	 */
	DELETE(-1);

	private int code;

	private TokenStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
