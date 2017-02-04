package com.fsmeeting.live.common.bean;

import com.fsmeeting.live.common.enums.Response;

/**
 * Controller 返回统一对象
 * 
 * @author yicai.liu<moon>
 *
 */
public class ResponseMsg<H> {

	/**
	 * 业务数据
	 */
	private H data;

	/**
	 * 响应码
	 */
	private int code = Response.SUCCESS.getCode();// 服务器返回状态码

	/**
	 * 信息：错误信息、附属信息
	 */
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public H getData() {
		return data;
	}

	public void setData(H data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
