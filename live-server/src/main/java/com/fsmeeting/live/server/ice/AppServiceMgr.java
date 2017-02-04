package com.fsmeeting.live.server.ice;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fastonz.live.sliceprotocol.controller.AMD_AppService_getLiveSrvAddr;
import com.fastonz.live.sliceprotocol.controller.AMD_AppService_getMinLoadService;
import com.fastonz.live.sliceprotocol.controller.AMD_AppService_heartbeat;
import com.fastonz.live.sliceprotocol.controller.AMD_AppService_register;
import com.fastonz.live.sliceprotocol.controller._AppServiceDisp;
import com.fastonz.live.sliceprotocol.model.AppHeartbeatModel;
import com.fastonz.live.sliceprotocol.model.AppModel;
import com.fsmeeting.live.common.enums.Response;
import com.fsmeeting.live.server.queue.RequestQueue;
import com.fsmeeting.live.server.queue.Task;
import com.fsmeeting.live.service.IAppService;

import Ice.Current;

@Component
public class AppServiceMgr extends _AppServiceDisp {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(AppServiceMgr.class);

	@Resource
	private RequestQueue queue;

	@Autowired
	private IAppService appService;

	@Override
	public void getLiveSrvAddr_async(final AMD_AppService_getLiveSrvAddr liveSrvAddr, final int liveRoomId,
			Current cur) {

		boolean enqueue = queue.offer(new Task() {
			@Override
			public void process() {
				String address = null;
				int result = Response.SUCCESS.getCode();
				try {
					address = appService.getLiveSrvAddr(liveRoomId);
					if (StringUtils.isBlank(address)) {
						result = Response.EMPTY_RESULT.getCode();
					}
				} catch (Exception e) {
					result = Response.DB_EXCEPTION.getCode();
					LOG.error(" createGetLiveSrvAddrTask execute fail:", e);
				}
				liveSrvAddr.ice_response(result, address);
			}
		});

		if (!enqueue) {
			LOG.error("队列已满,无法接收服务");
			liveSrvAddr.ice_response(Response.SYSTEM_BUSY.getCode(), null);
		}

	}

	@Override
	public void getMinLoadService_async(final AMD_AppService_getMinLoadService minload, final int appType,
			final String oldAddress, Current cur) {

		boolean enqueue = queue.offer(new Task() {
			@Override
			public void process() {
				String address = null;
				int result = Response.SUCCESS.getCode();
				try {
					address = appService.getMinLoadService(appType, oldAddress);
					if (StringUtils.isBlank(address)) {
						result = Response.EMPTY_RESULT.getCode();
					}
				} catch (Exception e) {
					result = Response.DB_EXCEPTION.getCode();
					LOG.error(" createGetMinLoadServiceTask execute fail:", e);
				}
				minload.ice_response(result, address);
			}
		});

		if (!enqueue) {
			LOG.error("队列已满,无法接收服务");
			minload.ice_response(Response.SYSTEM_BUSY.getCode(), null);
		}
	}

	@Override
	public void heartbeat_async(final AMD_AppService_heartbeat heartbeat, final AppHeartbeatModel model, Current cur) {

		boolean enqueue = queue.offer(new Task() {
			@Override
			public void process() {
				int result = Response.SUCCESS.getCode();
				try {
					boolean success = appService.heartbeat(model);
					if (!success) {
						result = Response.FAIL.getCode();
					}
				} catch (Exception e) {
					result = Response.DB_EXCEPTION.getCode();
					LOG.error(" createHeartbeatTask execute fail:", e);
				}
				heartbeat.ice_response(result);
			}
		});

		if (!enqueue) {
			LOG.error("队列已满,无法接收服务");
			heartbeat.ice_response(Response.SYSTEM_BUSY.getCode());
		}

	}

	@Override
	public void register_async(final AMD_AppService_register register, final AppModel model, Current cur) {

		boolean enqueue = queue.offer(new Task() {
			@Override
			public void process() {
				int result = Response.SUCCESS.getCode();
				try {
					if (StringUtils.isBlank(model.getAppId())) {
						result = Response.PARAM_ILLEGAL.getCode();
					} else {
						if (!appService.register(model)) {
							result = Response.SERVER_REGISTERED.getCode();
						}
					}
				} catch (Exception e) {
					result = Response.DB_EXCEPTION.getCode();
					LOG.error(" createRegistryTask execute fail:", e);
				}
				register.ice_response(result);
			}
		});

		if (!enqueue) {
			register.ice_response(Response.SYSTEM_BUSY.getCode());
		}

	}

}
