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
				// key 不存在时，为 key设置指定的值,设置成功，返回 1。 设置失败，返回 0
				if (jedis.setnx(lockKey, identifier) == 1) {
					jedis.expire(lockKey, lockExpire);
					retIdentifier = identifier;
				}
				// key 不存在时，返回 -2, key存在但没有设置剩余生存时间时，返回 -1,否则，以秒为单位，返回 key
				// 的剩余生存时间。
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
				// Redis Watch 命令用于监视一个(或多个) key，如果在事务执行之前这个(或这些) key
				// 被其他命令所改动，那么事务将被打断。总是返回 OK
				jedis.watch(lockKey);
				if (identifier.equals(jedis.get(lockKey))) {
					// 用于标记一个事务块的开始,事务块内的多条命令会按照先后顺序被放进一个队列当中，最后由 EXEC
					// 命令原子性(atomic)地执行。总是返回 OK
					Transaction trans = jedis.multi();
					trans.del(lockKey);
					// 执行所有事务块内的命令,返回事务块内所有命令的返回值，按命令执行的先后顺序排列。
					// 当操作被打断时（比如另一个客户端修改了lockName的值），事务执行失败，返回空值 null
					List<Object> results = trans.exec();
					if (results == null) {
						continue;
					}
					retFlag = true;
				}
				// 取消 WATCH 命令对所有 key 的监视,总是返回 OK
				jedis.unwatch();
				break;
			}
		} catch (Exception e) {
			throw new JedisException("Redis 释放分布式锁异常", e);
		}
		return retFlag;
	}

}
