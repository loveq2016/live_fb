package com.fsmeeting.live.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fsmeeting.live.service.ILiveRoomService;

/**
 * 直播服务调度任务
 * 
 * @author yicai.liu<moon>
 *
 */
@Component
public class LiveServiceTask {
	private static final Logger log = LoggerFactory.getLogger(LiveServiceTask.class);

	@Autowired
	private ILiveRoomService liveRoomService;

	/**
	 * <pre>
	 * 房间点数校准,发现异常数据，清理
	 * 
	 * 频率：1 f/min
	 * </pre>
	 */
	@Scheduled(fixedRate = 10000)
	public void calibrateLiveRoomUsers() {
		long start = System.currentTimeMillis();
		log.info("任务[caculateLiveRoomUsers]执行开始......");
		liveRoomService.calibrateRuntimeRoomPoint();
		log.info("任务[caculateLiveRoomUsers]耗时：" + (System.currentTimeMillis() - start) + "ms");
		log.info("任务[caculateLiveRoomUsers]执行结束......");
	}

	/**
	 * 搞个定时任务清除作废的token TODO
	 */
	public void clearInvalidTokens() {

	}
}
