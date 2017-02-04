package com.fsmeeting.live.common.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 直播房间运行时信息 :t_liveroom_runtime
 * 
 * @author yicai.liu<moon>
 *
 */
public class LiveRoomRuntime implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private int id;

	/**
	 * 直播房间ID
	 */
	private int liveRoomId;

	/**
	 * 分配时间
	 */
	private Date assignTime;

	/**
	 * 激活时间
	 */
	private Date activeTime;

	/**
	 * 状态
	 */
	private int status;

	/**
	 * 直播服务
	 * 
	 */
	private LiveService livingService;

	public LiveService getLivingService() {
		return livingService;
	}

	public void setLivingService(LiveService livingService) {
		this.livingService = livingService;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLiveRoomId() {
		return liveRoomId;
	}

	public void setLiveRoomId(int liveRoomId) {
		this.liveRoomId = liveRoomId;
	}

	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
