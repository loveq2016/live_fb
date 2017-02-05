package com.fsmeeting.live.generator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fsmeeting.live.common.Constants;
import com.fsmeeting.live.generator.IGenerator;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

/**
 * 用户ID生成器,Range:[0x50000000,0x5FFFFFFF]
 * 
 * @author yicai.liu<moon>
 *
 */
@Component("userIDRedisGenerator")
public class UserIDRedisGenerator implements IGenerator<Integer> {
	private static final Logger LOG = LoggerFactory.getLogger(UserIDRedisGenerator.class);

	@Autowired
	private JedisSentinelPool pool; // 依赖连接池

	@Override
	public Integer generate() {

		Integer incrID = Constants.USERID_RANGE_MIN;
		String lockKey = Constants.Redis.Key.LIVE_USERID_INCR;

		// CAS 乐观锁操作
		try (Jedis jedis = pool.getResource()) {

			while (true) {
				jedis.watch(lockKey);

				Transaction trans = jedis.multi();

				String curIdStr = trans.get(lockKey).get();// compare
				Integer curId = Constants.USERID_RANGE_MIN - 1;
				if (null != curIdStr) {
					try {
						curId = Integer.parseInt(curIdStr);
					} catch (Exception e) {
						LOG.error("pasrse exception:", e);
					}
				}
				if (curId <= Constants.USERID_RANGE_MIN || curId >= Constants.USERID_RANGE_MAX) {
					trans.set(lockKey, String.valueOf(Constants.USERID_RANGE_MIN));// set
				} else {
					incrID = trans.incr(lockKey).get().intValue();
				}

				if (null == trans.exec()) {
					continue;
				}

				jedis.unwatch();
				break;
			}

		} catch (Exception e) {
			throw new JedisException("Redis 获取User ID异常", e);
		}

		return incrID;
	}

	public static void main(String[] args) {

	}
}
