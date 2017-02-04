package com.fsmeeting.live.common.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Token 基础信息:t_tokeninfo
 * 
 * @author yicai.liu<moon>
 *
 */
public class TokenInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * token
	 */
	private String token;

	/**
	 * 直播ID
	 */
	private Integer liveId;

	/**
	 * 用户代理服务器ID
	 */
	private String appId;

	/**
	 * 过期时间
	 */
	private Integer expires;

	/**
	 * 状态(0:未激活，1：激活，-1：被删除)
	 */
	private Integer status;

	/**
	 * 激活时间（userProxy验证通过时间
	 */
	private Date activeTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getLiveId() {
		return liveId;
	}

	public void setLiveId(Integer liveId) {
		this.liveId = liveId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getExpires() {
		return expires;
	}

	public void setExpires(Integer expires) {
		this.expires = expires;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

}
