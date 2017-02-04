package com.fsmeeting.live.redis;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fsmeeting.live.common.Constants;

/**
 * 用户对redis数据访问接入层
 * 
 * @author yicai.liu<moon>
 *
 */
@Component
public class UserRedisDao extends RedisBaseDao {

	private Logger logger = LoggerFactory.getLogger(UserRedisDao.class);

	// ID 范围[MIN_USERID,MAX_USERID]
	private static final long MIN_USERID = 0x50000000L;
	private static final long MAX_USERID = 0x5FFFFFFFL;

	/**
	 * 从redis获取用户ID
	 * 
	 * 
	 * @return
	 */
	public synchronized long getUserId() {// TODO 要分布式锁
		String id = (String) get(Constants.Redis.Key.LIVE_USERID_INCR);// 获取当前ID
		Long userId = null;
		if (StringUtils.isNotBlank(id)) {
			try {
				userId = Long.parseLong(id);
			} catch (NumberFormatException e) {
				logger.error("用户ID获取数据错误", e);
			}
		}
		// ID不存在 or ID值不在指定范围
		if (null == userId || userId < MIN_USERID || userId >= MAX_USERID) {
			put(Constants.Redis.Key.LIVE_USERID_INCR, String.valueOf(MIN_USERID - 1));
		}
		// ID递增
		return increase(Constants.Redis.Key.LIVE_USERID_INCR);
	}

}
