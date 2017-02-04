package com.fsmeeting.live.common.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 直播房间基础信息 :t_liveroom
 * 
 * @author yicai.liu<moon>
 *
 */
public class LiveRoom implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private int id;

	/**
	 * 直播名称
	 */
	private String liveName;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 允许最大用户数
	 */
	private int maxUserCount;

	/**
	 * 1：需要邀请码。0：不需要
	 */
	private int verifyMode;

	/**
	 * 邀请码
	 */
	private String inviteCode;

	/**
	 * 会议室ID
	 */
	private int confRoomId;

	/**
	 * 导播ID
	 */
	private int directorId;

	/**
	 * 直播URI
	 */
	private String liveURI;

	/**
	 * 直播地点
	 */
	private String liveAddress;

	/**
	 * 直播服务器地址
	 */
	private String liveSrvAddr;

	/**
	 * 直播信息
	 */
	private String liveInfo;

	/**
	 * 企业ID
	 */
	private int companyId;

	/**
	 * 部门ID
	 */
	private int depId;

	/**
	 * 创建时间
	 */
	private Date creationTime;

	/**
	 * 创建者
	 */
	private int creatorId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLiveName() {
		return liveName;
	}

	public void setLiveName(String liveName) {
		this.liveName = liveName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getMaxUserCount() {
		return maxUserCount;
	}

	public void setMaxUserCount(int maxUserCount) {
		this.maxUserCount = maxUserCount;
	}

	public int getVerifyMode() {
		return verifyMode;
	}

	public void setVerifyMode(int verifyMode) {
		this.verifyMode = verifyMode;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getLiveURI() {
		return liveURI;
	}

	public void setLiveURI(String liveURI) {
		this.liveURI = liveURI;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	public String getLiveSrvAddr() {
		return liveSrvAddr;
	}

	public void setLiveSrvAddr(String liveSrvAddr) {
		this.liveSrvAddr = liveSrvAddr;
	}

	public String getLiveInfo() {
		return liveInfo;
	}

	public void setLiveInfo(String liveInfo) {
		this.liveInfo = liveInfo;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public int getConfRoomId() {
		return confRoomId;
	}

	public void setConfRoomId(int confRoomId) {
		this.confRoomId = confRoomId;
	}

	public int getDirectorId() {
		return directorId;
	}

	public void setDirectorId(int directorId) {
		this.directorId = directorId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getDepId() {
		return depId;
	}

	public void setDepId(int depId) {
		this.depId = depId;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

}
