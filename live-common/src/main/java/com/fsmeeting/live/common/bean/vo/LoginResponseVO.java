package com.fsmeeting.live.common.bean.vo;

import java.io.Serializable;

import com.fsmeeting.live.common.bean.LiveRoom;

/**
 * 登录返回封装对象
 * 
 * @author yicai.liu<moon>
 *
 */
public class LoginResponseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private int userId;

	/**
	 * token
	 */
	private String token;

	/**
	 * 代理服务器地址
	 */
	private String proxyAddr;

	/**
	 * 直播房间
	 */
	private LiveRoom room;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getProxyAddr() {
		return proxyAddr;
	}

	public void setProxyAddr(String proxyAddr) {
		this.proxyAddr = proxyAddr;
	}

	public LiveRoom getRoom() {
		return room;
	}

	public void setRoom(LiveRoom room) {
		this.room = room;
	}

}
