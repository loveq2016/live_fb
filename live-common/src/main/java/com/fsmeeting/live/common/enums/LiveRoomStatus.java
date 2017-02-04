package com.fsmeeting.live.common.enums;

/**
 * 房间状态
 * 
 * @author yicai.liu<moon>
 *
 */
public enum LiveRoomStatus {

	/**
	 * 激活
	 */
	ACTIVE(1),

	/**
	 * 未激活
	 */
	INACTIVE(0);

	private int code;

	private LiveRoomStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
