package com.fsmeeting.live.redis;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fsmeeting.live.common.enums.RedisTimeUnit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * redis 基础操作
 * 
 * @author yicai.liu<moon>
 *
 */
@Component
public class RedisBaseDao {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JedisSentinelPool jedisSentinelPool;

	protected Jedis getJedis() {
		return jedisSentinelPool.getResource();
	}

	/**
	 * 永不超时
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	protected boolean put(String key, String value) {
		return put(key, value, 0);
	}

	/**
	 * 设置键值对的超时秒数
	 * 
	 * @param key
	 * @param value
	 * @param senconds
	 * @return
	 */
	protected boolean put(String key, String value, int senconds) {
		return put(key, value, senconds, RedisTimeUnit.S);
	}

	/**
	 * 细粒度设置键值对的超时信息
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param expire
	 *            过期时间
	 * @param unit
	 *            时间单位
	 * @return
	 */
	protected boolean put(String key, String value, long expire, RedisTimeUnit unit) {
		try (Jedis jedis = getJedis()) {

			if (expire <= 0) {
				jedis.set(key, value);
				return true;
			}

			switch (unit) {
			case S:
				jedis.setex(key, (int) expire, value);
				return true;
			case MS:
				jedis.psetex(key, expire, value);
				return true;
			default:
				logger.error("RedisTimeUnit Unsupport...");
				return false;
			}

		} catch (Throwable t) {
			System.out.println("缓存[" + key + "]失败, value[" + value + "]");
		}
		return false;

	}

	/**
	 * Redis 键的存在性校验
	 * 
	 * @param key
	 * @return 存在：true,不存在：false
	 */
	protected boolean contains(String key) {
		try (Jedis jedis = getJedis()) {
			return jedis.exists(key);
		} catch (Throwable t) {
			logger.error("判断缓存存在失败key[" + key + ", error[" + t + "]");
		}
		return false;
	}

	/**
	 * 获取键对应的值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		if (!contains(key)) {
			return null;
		}
		try (Jedis jedis = getJedis()) {
			return jedis.get(key);
		} catch (Throwable t) {
			logger.error("获取缓存失败key[" + key + ", error[" + t + "]");
		}
		return null;
	}

	/**
	 * 批量移除缓存
	 * 
	 * @param keys
	 * @return
	 */
	protected long remove(String... keys) {
		try (Jedis jedis = getJedis()) {
			long result = jedis.del(keys);
			logger.info("成功删除keys[" + keys + "]");
			return result;
		} catch (Throwable t) {
			logger.error("删除缓存失败key[" + keys + ", error[" + t + "]");
		}
		return 0;
	}

	/**
	 * 模糊查询
	 * 
	 * @param patten
	 * @return
	 */
	public Set<String> keyLike(String pattern) {
		try (Jedis jedis = getJedis()) {
			Set<String> result = jedis.keys(pattern);
			return result;
		} catch (Throwable t) {
			logger.error("模糊查询缓存失败，patten[" + pattern + ", error[" + t + "]");
		}
		return null;
	}

	/**
	 * 值递增,增量为1
	 * 
	 * @param key
	 * @return 递增后的值
	 */
	protected long increase(String key) {
		try (Jedis jedis = getJedis()) {
			return jedis.incr(key);
		} catch (Throwable t) {
			logger.error("值递增操作失败，key[" + key + ", error[" + t + "]");
		}
		return 0;
	}

	/**
	 * 值递增,增量为指定的increment
	 * 
	 * @param key
	 * @param increment
	 *            增量，可正可负
	 * @return 递增后的值
	 */
	protected long increase(String key, long increment) {
		try (Jedis jedis = getJedis()) {
			return jedis.incrBy(key, increment);
		} catch (Throwable t) {
			logger.error("值递增操作失败，key[" + key + "],增量[" + increment + "],error[" + t + "]");
		}
		return 0;
	}

	/**
	 * redis zadd 操作
	 * 
	 * @param key
	 * @param value
	 * @param score
	 * @return 是否添加成功
	 */
	protected boolean zadd(String key, String menber, double score) {

		try (Jedis jedis = getJedis()) {
			return jedis.zadd(key, score, menber) > 0;
		} catch (Throwable t) {
			logger.error("zadd 操作失败，key[" + key + "],value[" + menber + "],error[" + t + "]");
		}
		return false;
	}

	/**
	 * redis zsort 元素批量 remove 操作
	 * 
	 * @param key
	 * @param menbers
	 * @return 是否删除了指定元素
	 */
	protected boolean zremove(String key, String... menbers) {

		try (Jedis jedis = getJedis()) {
			return jedis.zrem(key, menbers) > 0;
		} catch (Throwable t) {
			logger.error("zremove 操作失败，key[" + key + "],value[" + menbers + "],error[" + t + "]");
		}
		return false;
	}

	/**
	 * redis zremrangeByScore 操作
	 * 
	 * @param key
	 * @param scoreStart
	 * @param scoreEnd
	 * @return 删除元素的个数
	 */
	protected long zremrangeByScore(String key, double scoreStart, double scoreEnd) {

		try (Jedis jedis = getJedis()) {
			return jedis.zremrangeByScore(key, scoreStart, scoreEnd);
		} catch (Throwable t) {
			logger.error("zremrangeByScore 操作失败，key[" + key + "],startScore[" + scoreStart + "],endScore[" + scoreEnd
					+ "],error[" + t + "]");
		}
		return 0;
	}

	/**
	 * redis hget 操作
	 * 
	 * @param key
	 * @param field
	 * @return 不存在，为空；存在，field 对应的值。
	 */
	protected String hget(String key, String field) {

		try (Jedis jedis = getJedis()) {
			String result = jedis.hget(key, field); // Filed
			return result;
		} catch (Throwable t) {
			logger.error("hget 操作失败，key[" + key + "],field[" + field + "],error[" + t + "]");
		}
		return null;
	}

	/**
	 * redis hset 操作
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	protected void hset(String key, String field, String value) {

		try (Jedis jedis = getJedis()) {
			jedis.hset(key, field, value);
		} catch (Throwable t) {
			logger.error("hset 操作失败，key[" + key + "],field[" + field + "],value[" + value + "],error[" + t + "]");
		}
	}

	/**
	 * redis hkeys 操作
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	protected Set<String> hkeys(String key) {

		try (Jedis jedis = getJedis()) {
			return jedis.hkeys(key);
		} catch (Throwable t) {
			logger.error("hkeys 操作失败，key[" + key + "],error[" + t + "]");
		}
		return null;
	}

	/**
	 * redis hdel 操作
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	protected boolean hdel(String key, String field) {

		try (Jedis jedis = getJedis()) {
			return jedis.hdel(key, field) > 0;
		} catch (Throwable t) {
			logger.error("hdel 操作失败，key[" + key + "],error[" + t + "]");
		}
		return false;
	}

	/**
	 * redis hexists 操作
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	protected boolean hexists(String key, String field) {

		try (Jedis jedis = getJedis()) {
			return jedis.hexists(key, field);
		} catch (Throwable t) {
			logger.error("hdel 操作失败，key[" + key + "],error[" + t + "]");
		}
		return false;
	}

	/**
	 * redis sadd 批量增加成员操作
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	protected boolean sadd(String key, String... members) {

		try (Jedis jedis = getJedis()) {
			return jedis.sadd(key, members) > 0;
		} catch (Throwable t) {
			logger.error("sismember 操作失败，key[" + key + "],members[" + members + "],error[" + t + "]");
		}
		return false;
	}

	/**
	 * redis srem 批量删除成员 操作
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	protected boolean srem(String key, String... members) {

		try (Jedis jedis = getJedis()) {
			return jedis.srem(key, members) > 0;
		} catch (Throwable t) {
			logger.error("sismember 操作失败，key[" + key + "],members[" + members + "],error[" + t + "]");
		}
		return false;
	}

	/**
	 * redis sismenber 操作
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	protected boolean sismember(String key, String member) {

		try (Jedis jedis = getJedis()) {
			return jedis.sismember(key, member);
		} catch (Throwable t) {
			logger.error("sismember 操作失败，key[" + key + "],member[" + member + "],error[" + t + "]");
		}
		return false;
	}
}