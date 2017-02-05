package com.fsmeeting.live.common.lock.redis;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fsmeeting.live.common.lock.IDistributionLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

/**
 * 分布式锁之Redis基于哨兵实现
 * 
 * @author yicai.liu<moon>
 *
 */
@Component
public class RedisDistributionLock implements IDistributionLock {

	@Autowired
	private JedisSentinelPool pool; // 依赖连接池

	@Override
	public String acquire(String lockName, long acquireTimeoutMS, long lockTimeoutMS) {

		String retIdentifier = null;
		try (Jedis jedis = pool.getResource();) {

			String identifier = UUID.randomUUID().toString();// 唯一码
			String lockKey = "lock:" + lockName;
			int lockExpire = (int) (lockTimeoutMS / 1000);

			long end = System.currentTimeMillis() + acquireTimeoutMS;
			while (System.currentTimeMillis() < end) {
				if (jedis.setnx(lockKey, identifier) == 1) {
					jedis.expire(lockKey, lockExpire);
					retIdentifier = identifier;
				}
				if (jedis.ttl(lockKey) == -1) {
					jedis.expire(lockKey, lockExpire);
				}
				TimeUnit.MILLISECONDS.sleep(10);// 睡一会

			}
		} catch (Exception e) {
			throw new JedisException("Redis 获取分布式锁异常", e);
		}
		return retIdentifier;

	}

	@Override
	public boolean release(String lockName, String identifier) {

		String lockKey = "lock:" + lockName;
		boolean retFlag = false;
		try (Jedis jedis = pool.getResource()) {
			while (true) {
				jedis.watch(lockKey);
				if (identifier.equals(jedis.get(lockKey))) {
					Transaction trans = jedis.multi();
					trans.del(lockKey);
					List<Object> results = trans.exec();
					if (results == null) {
						continue;
					}
					retFlag = true;
				}
				jedis.unwatch();
				break;
			}
		} catch (Exception e) {
			throw new JedisException("Redis 释放分布式锁异常", e);
		}
		return retFlag;
	}

}
