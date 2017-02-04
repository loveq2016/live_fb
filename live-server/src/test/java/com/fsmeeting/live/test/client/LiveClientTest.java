package com.fsmeeting.live.test.client;

import org.junit.BeforeClass;

import com.fastonz.live.sliceprotocol.controller.AppServicePrx;
import com.fastonz.live.sliceprotocol.controller.AppServicePrxHelper;
import com.fastonz.live.sliceprotocol.controller.LiveRoomServicePrx;
import com.fastonz.live.sliceprotocol.controller.LiveRoomServicePrxHelper;
import com.fastonz.live.sliceprotocol.controller.UserServicePrx;
import com.fastonz.live.sliceprotocol.controller.UserServicePrxHelper;

import Ice.Communicator;
import Ice.Util;

public class LiveClientTest {

	protected static Communicator ic = null;

	protected static AppServicePrx appService;
	protected static UserServicePrx userService;
	protected static LiveRoomServicePrx liveRoomService;

	@BeforeClass
	public static void init() {

		/**
		 * <pre>
		 * 生产环境测试 
		 *  String endpoint = String.format("%s:%s -h %s -p %s", "FMServiceIceGrid/Locator", "tcp", "120.24.85.241", "10001");
			String endpoint = String.format("%s:%s -h %s -p %s", "FMServiceIceGrid/Locator", "tcp", "192.168.5.56", "10001");
			InitializationData localInitializationData = new InitializationData();
			localInitializationData.properties = Util.createProperties();
			localInitializationData.properties.setProperty("Ice.Default.Locator", endpoint);
			ic = Util.initialize(localInitializationData);
			String str = "";
		 * </pre>
		 */
		/* 开发环境本机测试 */
		ic = Util.initialize();
		//String str = ":default -h 192.168.7.178 -p 33001";
	   String str = ":default -h 192.168.5.157 -p 33001";
		appService = AppServicePrxHelper.checkedCast(ic.stringToProxy("appService" + str));
		userService = UserServicePrxHelper.checkedCast(ic.stringToProxy("userService" + str));
		liveRoomService = LiveRoomServicePrxHelper.checkedCast(ic.stringToProxy("liveRoomService" + str));
		if (null == appService)
			throw new Error("Invalid proxy");

	}

}
