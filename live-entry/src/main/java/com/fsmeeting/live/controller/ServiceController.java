package com.fsmeeting.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fsmeeting.live.common.bean.LiveService;
import com.fsmeeting.live.redis.AppRedisDao;

@RestController
@RequestMapping("/live")
public class ServiceController {

	@Autowired
	private AppRedisDao appRedisDao;

	@RequestMapping(value = "/services", method = RequestMethod.GET)
	public List<LiveService> getServices() {
		return appRedisDao.getAllService();
	}

	@RequestMapping(value = "/services/{id}", method = RequestMethod.GET)
	public LiveService getService(@PathVariable String id) {
		return appRedisDao.getService(id);
	}
}
