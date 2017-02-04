package com.fsmeeting.live.test.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fastonz.live.sliceprotocol.model.AppHeartbeatModel;
import com.fastonz.live.sliceprotocol.model.AppModel;
import com.fsmeeting.live.common.enums.LiveServerType;

import Ice.StringHolder;

public class AppServiceTest extends LiveClientTest {

	private static final Logger LOG = LoggerFactory.getLogger(AppServiceTest.class);
	private static final Random RMD = new Random();

	@Test
	public void registry() {
		for (int i = 0; i < 15; i++) {
			AppModel model = new AppModel();
			model.setAddress("Address:" + RMD.nextInt());
			model.setAppId("appId:" + RMD.nextInt());
			model.setAppType(RMD.nextInt(5));
			model.setDesc("service");
			model.setWeight(10000);
			appService.register(model);
			LOG.info(model.getAppId() + "/" + model.getAppType() + "/" + model.getAddress());
		}
	}

	@Test
	public void heartbeat() throws Exception {
		List<String> appList = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			AppModel model = new AppModel();
			model.setAddress("Address:" + RMD.nextInt());
			model.setAppId("appId:" + RMD.nextInt());
			model.setAppType(RMD.nextInt(5));
			model.setDesc("service");
			model.setWeight(10000);
			appService.register(model);
			appList.add(model.getAppId());
			LOG.info(model.getAppId() + "/" + model.getAppType() + "/" + model.getAddress());
		}

		while (true) {
			for (String appId : appList) {
				AppHeartbeatModel model = new AppHeartbeatModel();
				model.setAppId(appId);
				model.setCurLoad(RMD.nextInt(2000));
				appService.heartbeat(model);
			}
			TimeUnit.SECONDS.sleep(5);
		}
	}

	@Test
	public void getMinLoad() {

		StringHolder holder = new StringHolder();
		appService.getLiveSrvAddr(LiveServerType.LIVE.getCode(), holder);
		System.out.println(holder.value);

	}

}
