package com.fsmeeting.live.server.ice;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fastonz.live.sliceprotocol.controller.AMD_LiveRoomService_activeLiveRoom;
import com.fastonz.live.sliceprotocol.controller.AMD_LiveRoomService_getLiveRoom;
import com.fastonz.live.sliceprotocol.controller.AMD_LiveRoomService_releaseLiveRoom;
import com.fastonz.live.sliceprotocol.controller._LiveRoomServiceDisp;
import com.fastonz.live.sliceprotocol.model.LiveRoomModel;
import com.fsmeeting.live.common.enums.Response;
import com.fsmeeting.live.server.queue.RequestQueue;
import com.fsmeeting.live.server.queue.Task;
import com.fsmeeting.live.service.ILiveRoomService;

import Ice.Current;

@Component
public class LiveRoomServiceMgr extends _LiveRoomServiceDisp {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(LiveRoomServiceMgr.class);

	@Autowired
	private ILiveRoomService liveRoomService;

	@Resource
	private RequestQueue queue;

	@Override
	public void activeLiveRoom_async(final AMD_LiveRoomService_activeLiveRoom activeLiveRoom, final int liveRoomId,
			Current cur) {

		boolean enqueue = queue.offer(new Task() {
			@Override
			public void process() {
				int result = Response.SUCCESS.getCode();
				try {
					liveRoomService.activeLiveRoom(liveRoomId);
				} catch (Exception e) {
					result = Response.DB_EXCEPTION.getCode();
					LOG.error("createActiveLiveRoomTask execute fail:", e);
				}
				activeLiveRoom.ice_response(result);
			}
		});

		if (!enqueue) {
			activeLiveRoom.ice_response(Response.SYSTEM_BUSY.getCode());
		}

	}

	@Override
	public void getLiveRoom_async(final AMD_LiveRoomService_getLiveRoom getLiveRoom, final int liveRoomId,
			Current cur) {

		boolean enqueue = queue.offer(new Task() {
			@Override
			public void process() {
				int result = Response.SUCCESS.getCode();
				LiveRoomModel room = null;
				try {
					room = liveRoomService.getLiveRoom(liveRoomId);
					if (null == room) {
						result = Response.EMPTY_RESULT.getCode();
					}
				} catch (Exception e) {
					result = Response.DB_EXCEPTION.getCode();
					LOG.error("createGetLiveRoomTask execute fail:", e);
				}
				getLiveRoom.ice_response(result, room);
			}
		});

		if (!enqueue) {
			getLiveRoom.ice_response(Response.SYSTEM_BUSY.getCode(), null);
		}

	}

	@Override
	public void releaseLiveRoom_async(final AMD_LiveRoomService_releaseLiveRoom releaseLiveRoom, final int liveRoomId,
			Current cur) {

		boolean enqueue = queue.offer(new Task() {
			@Override
			public void process() {
				int result = Response.SUCCESS.getCode();
				try {
					liveRoomService.releaseLiveRoom(liveRoomId);
				} catch (Exception e) {
					result = Response.DB_EXCEPTION.getCode();
					LOG.error("createReleaseLiveRoomTask execute fail:", e);
				}
				releaseLiveRoom.ice_response(result);
			}
		});

		if (!enqueue) {
			releaseLiveRoom.ice_response(Response.SYSTEM_BUSY.getCode());
		}

	}

}
