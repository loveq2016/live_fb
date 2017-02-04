
package com.fsmeeting.live.common.enums;

/**
 * 响应编码:（HTTP\TCP）共用
 * 
 * @author yicai.liu<moon>
 *
 */
public enum Response {

	/**
	 * 成功
	 */
	SUCCESS(0),

	/**
	 * 未知错误
	 */
	FAIL(1),

	/************************* Server ERROR *************************/
	/**
	 * 远程服务调用出错
	 */
	RCP_EXCPTION(1001),

	/**
	 * 服务端资源不可用
	 */
	SERVER_RESOURCE_DISABLED(1002),

	/**
	 * 服务调用未授权
	 */
	UNAUTHORIZED(1003),

	/**
	 * 参数不合法
	 */
	PARAM_ILLEGAL(1004),

	/**
	 * 缺少必要的参数
	 */
	NEED_MORE_PARAM(1005),

	/**
	 * 系统繁忙
	 */
	SYSTEM_BUSY(1006),

	/**
	 * 数据库拒绝访问
	 */
	DB_ACCESSDENIED(1007),

	/**
	 * 数据库执行异常
	 */
	DB_EXCEPTION(1008),

	/**
	 * 非法请求
	 */
	REQUEST_ILLEGAL(1009),
	/**
	 * 请求超时
	 */
	REQUEST_TIMEOUT(1010),

	/************************* Business ERROR *************************/
	/**
	 * Token已经被使用
	 */
	TOKEN_USED(2001),

	/**
	 * Token已经过期
	 */
	TOKEN_EXPIRED(2002),
	/**
	 * Token不合法
	 */
	TOKEN_INVALID(2003),

	/**
	 * 服务器已经注册
	 */
	SERVER_REGISTERED(2004),

	/**
	 * 邀请码不正确
	 */
	INVALID_INVITE_CODE(2005),

	/**
	 * 房间人数已满
	 */
	ROOM_FULL(2006),

	/**
	 * 服务器不存在
	 */
	SERVER_NOT_EXIST(2007),

	/**
	 * 直播房间不存在
	 */
	ROOM_NOT_EXIST(2008),

	/**
	 * 昵称不能为空
	 */
	NICKNAME_NOT_NULL(2009),

	/**
	 * 响应结果集为空
	 */
	EMPTY_RESULT(2010);

	private int code;

	private Response(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
