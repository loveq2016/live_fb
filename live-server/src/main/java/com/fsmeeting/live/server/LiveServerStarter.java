package com.fsmeeting.live.server;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fsmeeting.live.server.ice.AppServiceMgr;
import com.fsmeeting.live.server.ice.LiveRoomServiceMgr;
import com.fsmeeting.live.server.ice.UserServiceMgr;

import Ice.ObjectImpl;

/**
 * ICE 直播服务端启动类
 * 
 * @author yicai.liu<moon>
 *
 */
public class LiveServerStarter extends Ice.Application {

	private Logger log = Logger.getLogger(LiveServerStarter.class);

	public int run(String[] args) {
		if (args.length > 0) {
			System.err.println(appName() + ": too many arguments");
			return 1;
		}

		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		ObjectImpl appService = (AppServiceMgr) context.getBean("appServiceMgr");
		ObjectImpl userService = (UserServiceMgr) context.getBean("userServiceMgr");
		ObjectImpl liveRoomService = (LiveRoomServiceMgr) context.getBean("liveRoomServiceMgr");

		String endpoints = getEndpoints();
		Ice.ObjectAdapter adapter = communicator().createObjectAdapterWithEndpoints("LiveServerAdapter", endpoints);
		// Ice.Properties properties = communicator().getProperties();

		// adapter.add(appService,
		// communicator().stringToIdentity(properties.getProperty("appServiceIdentity")));
		adapter.add(appService, communicator().stringToIdentity("appService"));
		adapter.add(userService, communicator().stringToIdentity("userService"));
		adapter.add(liveRoomService, communicator().stringToIdentity("liveRoomService"));
		adapter.activate();
		communicator().waitForShutdown();
		return 0;
	}

	private String getEndpoints() {
		String endpoints = "";
		try {
			endpoints = loadProperties().getProperty("fmservice.adapter.endpoints");
			if (endpoints.isEmpty() || endpoints.contains("localhost")) {
				endpoints = buildEndpointsByIp();
			}
		} catch (Exception e) {
			log.error("获取配置fmservice.adapter.endpoints信息异常，Exception:", e);
			endpoints = buildEndpointsByIp();
		}
		return endpoints;
	}

	private String buildEndpointsByIp() {
		return "tcp -h " + getHostAddress() + " -p 33001";
	}

	private String getHostAddress() {
		String ip = "";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress();
			log.info("*******************InetAddress.getLocalHost=" + ip);
		} catch (UnknownHostException e1) {
			log.error("获取本机ip地址异常，Exception:", e1);
		}
		return ip;
	}

	public static void main(String[] args) {
		LiveServerStarter app = new LiveServerStarter();
		int status = app.main("Server", args);
		System.exit(status);

	}

	public Properties loadProperties() {
		ClassLoader classLoader;
		Properties properties;

		classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = LiveServerStarter.class.getClassLoader();
		}

		InputStream input = classLoader.getResourceAsStream("config.cfg");
		properties = new Properties();
		try {
			properties.load(input);
		} catch (Exception e) {
			log.warn("catch an exception when loading properties. ", e);

			throw new IllegalArgumentException("fail when load properties");
		}

		return properties;
	}

}
