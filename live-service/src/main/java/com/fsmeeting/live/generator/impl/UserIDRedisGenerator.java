package com.fsmeeting.live.generator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fsmeeting.live.generator.IGenerator;
import com.fsmeeting.live.redis.UserRedisDao;

/**
 * 用户ID生成器
 * 
 * @author yicai.liu<moon>
 *
 */
@Component("userIDRedisGenerator")
public class UserIDRedisGenerator implements IGenerator<Integer> {

	@Autowired
	private UserRedisDao userRedisDao;

	@Override
	public Integer generate() {
		return (int) userRedisDao.getUserId();
	}

	public static void main(String[] args) {

	}
}
