package com.fsmeeting.live.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fsmeeting.live.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserServiceTest {

	@Autowired
	private IUserService userService;
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceTest.class);

	@Test
	public void testLogin() {
		try {
			userService.login("", null);
		} catch (Exception e) {
			LOG.error("...", e);
		}
	}

}
