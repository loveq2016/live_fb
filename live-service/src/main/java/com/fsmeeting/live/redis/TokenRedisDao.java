package com.fsmeeting.live.redis;

import org.springframework.stereotype.Component;

import com.fsmeeting.live.common.Constants;

/**
 * token 缓存访问层
 * 
 * @author yicai.liu<moon>
 *
 */
@Component
public class TokenRedisDao extends RedisBaseDao {

	/**
	 * 存储token到对应的直播房间
	 * 
	 * @param token
	 * @param liveRoomId
	 * @return
	 */
	public boolean token2Room(String token, int liveRoomId) {
		return zadd(Constants.Redis.Key.TOKENS_SORTED_SET_IN_LIVEROOM_PREFIXER + liveRoomId, token,
				System.currentTimeMillis());
	}

	/**
	 * 删除房间下的某个token
	 * 
	 * @param token
	 * @param liveRoomId
	 * @return
	 */
	public boolean removeToken(String token, int liveRoomId) {
		return zremove(Constants.Redis.Key.TOKENS_SORTED_SET_IN_LIVEROOM_PREFIXER + liveRoomId, token);
	}

	/**
	 * 删除房间
	 * 
	 * @param liveRoomId
	 */
	public void releaseRoomTokens(int liveRoomId) {
		remove(Constants.Redis.Key.TOKENS_SORTED_SET_IN_LIVEROOM_PREFIXER + liveRoomId);
		remove(Constants.Redis.Key.TOKENS_UNSORTED_SET_IN_LIVEROOM_PREFIXER + liveRoomId);
	}

	/**
	 * 计算房间下的失效用户点数
	 * 
	 * @param liveRoomId
	 */
	public long removeInvalidToken(int liveRoomId) {
		return zremrangeByScore(Constants.Redis.Key.TOKENS_SORTED_SET_IN_LIVEROOM_PREFIXER + liveRoomId, 0,
				System.currentTimeMillis() - Constants.TOKEN_HEATBEAT_RATE * 60 * 1000);

	}

	/**
	 * 保存token,用于校验,校验通过后，删除？TODO 这个token 存多久呢？
	 * 
	 * @param token
	 *            assume unique
	 * @param liveRoomId
	 */
	public boolean saveRoomToken(String token, int liveRoomId) {
		return sadd(Constants.Redis.Key.TOKENS_UNSORTED_SET_IN_LIVEROOM_PREFIXER + liveRoomId, token);
	}

	/**
	 * 校验 token&房间id的合法性
	 * 
	 * @param token
	 * @param liveRoomId
	 * @return
	 */
	public boolean validateRoomToken(String token, int liveRoomId) {
		return sismember(Constants.Redis.Key.TOKENS_UNSORTED_SET_IN_LIVEROOM_PREFIXER + liveRoomId, token);
	}
}
