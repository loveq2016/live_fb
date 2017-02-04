package com.fsmeeting.live.common.bean.vo;

import java.io.Serializable;

/**
 * 用户登录页面请求参数封装
 * 
 * @author yicai.liu<moon>
 *
 */
public class LoginRequestVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nickName;

	private String inviteCode;

	private String ticket;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

}
