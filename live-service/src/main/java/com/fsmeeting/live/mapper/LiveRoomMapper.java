package com.fsmeeting.live.mapper;

import org.apache.ibatis.annotations.Param;

import com.fastonz.live.sliceprotocol.model.LiveRoomModel;
import com.fsmeeting.live.common.bean.LiveRoomRuntime;

/**
 * 直播房间数据库操作
 * 
 * @author yicai.liu<moon>
 *
 */
public interface LiveRoomMapper {

	/**
	 * 查看直播服务器
	 * 
	 * @param liveRoomId
	 *            房间号
	 * @param status
	 *            房间状态
	 * @return
	 */
	LiveRoomRuntime getLiveService(@Param("liveRoomId") int liveRoomId, @Param("status") int status);

	/**
	 * 获取房间信息
	 * 
	 * @param liveRoomId
	 *            房间ID
	 * @return
	 */
	LiveRoomModel getLiveRoom(int liveRoomId);

	/**
	 * 激活直播房间
	 * 
	 * @param liveRoomId
	 *            房间ID
	 * @return
	 */
	int activeLiveRoom(int liveRoomId);

	/**
	 * 删除直播房间运行时信息
	 * 
	 * @param liveRoomId
	 * @return
	 */
	int deleteRuntimeInfo(int liveRoomId);

	/**
	 * 记录直播房间/直播服务器关系
	 * 
	 * @param room
	 * @return
	 */
	int addRoomRuntime(LiveRoomRuntime room);

	/**
	 * 更新直播房间服务器地址
	 * 
	 * @param inactiveRoom
	 */
	int updateLiveAddress(LiveRoomRuntime inactiveRoom);

}
