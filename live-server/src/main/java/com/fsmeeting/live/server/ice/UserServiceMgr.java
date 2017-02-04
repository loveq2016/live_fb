package com.fsmeeting.live.server.ice;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fastonz.live.sliceprotocol.controller.AMD_UserService_activeToken;
import com.fastonz.live.sliceprotocol.controller.AMD_UserService_releaseToken;
import com.fastonz.live.sliceprotocol.controller.AMD_UserService_releaseTokenByLiveRoom;
import com.fastonz.live.sliceprotocol.controller.AMD_UserService_tokenHeartbeat;
import com.fastonz.live.sliceprotocol.controller._UserServiceDisp;
import com.fastonz.live.sliceprotocol.model.TokenModel;
import com.fsmeeting.live.common.enums.Response;
import com.fsmeeting.live.server.queue.RequestQueue;
import com.fsmeeting.live.server.queue.Task;
import com.fsmeeting.live.service.IUserService;

import Ice.Current;

@Component
public class UserServiceMgr extends _UserServiceDisp {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceMgr.class);

	@Resource
	private RequestQueue queue;

	@Autowired
	private IUserService userService;

	@Override
	public void releaseTokenByLiveRoom_async(final AMD_UserService_releaseTokenByLiveRoom releaseTokenByLiveRoom,
			final int liveRoomId, Current arg2) {

		boolean enqueue = queue.offer(new Task() {
			@Override
			public void process() {
				int result = Response.SUCCESS.getCode();
				try {
					userService.releaseTokenByLiveRoom(liveRoomId);
				} catch (Exception e) {
					result = Response.DB_EXCEPTION.getCode();
					LOG.error(" releaseTokenByLiveRoom Task execute fail:", e);
				}
				releaseTokenByLiveRoom.ice_response(result);
			}
		});

		if (!enqueue) {
			releaseTokenByLiveRoom.ice_response(Response.SYSTEM_BUSY.getCode());
		}

	}

	@Override
	public void releaseToken_async(final AMD_UserService_releaseToken releaseToken, final List<TokenModel> tokens,
			Current arg2) {

		boolean enqueue = queue.offer(new Task() {
			@Override
			public void process() {
				int result = Response.SUCCESS.getCode();
				try {
					userService.releaseToken(tokens);
				} catch (Exception e) {
					result = Response.DB_EXCEPTION.getCode();
					LOG.error(" releaseToken task execute fail:", e);
				}
				releaseToken.ice_response(result);
			}
		});

		if (!enqueue) {
			releaseToken.ice_response(Response.SYSTEM_BUSY.getCode());
		}

	}

	@Override
	public void tokenHeartbeat_async(final AMD_UserService_tokenHeartbeat tokenHeartbeat, final String token,
			final int liveRoomId, Current arg3) {

		boolean enqueue = queue.offer(new Task() {
			@Override
			public void process() {
				int result = Response.SUCCESS.getCode();
				try {
					userService.tokenHeartbeat(token, liveRoomId);
				} catch (Exception e) {
					result = Response.DB_EXCEPTION.getCode();
					LOG.error(" tokenHeartbeat task execute fail:", e);
				}
				tokenHeartbeat.ice_response(result);
			}
		});

		if (!enqueue) {
			tokenHeartbeat.ice_response(Response.SYSTEM_BUSY.getCode());
		}

	}

	@Override
	public void activeToken_async(final AMD_UserService_activeToken activeToken, final int userId, final String token,
			final int liveRoomId, Current arg3) {

		boolean enqueue = queue.offer(new Task() {
			@Override
			public void process() {
				int result = Response.SUCCESS.getCode();
				try {
					boolean success = userService.activeToken(token, liveRoomId);
					if (!success) {
						result = Response.TOKEN_INVALID.getCode();
					}
				} catch (Exception e) {
					result = Response.DB_EXCEPTION.getCode();
					LOG.error("createActiveTokenTask execute fail:", e);
				}
				activeToken.ice_response(result);
			}
		});

		if (!enqueue) {
			activeToken.ice_response(Response.SYSTEM_BUSY.getCode());
		}

	}

}
