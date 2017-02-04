package com.fsmeeting.live.common.lock;

/**
 * 实现该接口时，请仔细考虑如下几个方面
 * 
 * <pre>
 * 分布式锁
 * 		1 	获取锁
 * 		2 	释放锁
 * 				a 	正常释放
 * 				b	异常释放
 * 						合理的设置锁的超时时间
 * 		3	如何得知锁已经释放？
 * 				a	没有获取到锁的客户端不断尝试获取锁
 * 						数据库、redis
 * 				b	 服务器端通知客户端锁被释放了
 * 						zookeeper
 * </pre>
 * 
 * @author yicai.liu<moon>
 *
 */
public interface IDistributionLock {

	/**
	 * 获取分布式锁
	 * 
	 * @param lockName
	 *            竞争获取锁key
	 * @param acquireTimeoutMS
	 *            获取锁超时时间(单位：毫秒)
	 * @param lockTimeoutMS
	 *            锁的超时时间(单位：毫秒)
	 * @return
	 */
	String acquire(String lockName, long acquireTimeoutMS, long lockTimeoutMS);

	/**
	 * 释放锁
	 * 
	 * @param lockName
	 *            竞争获取锁key
	 * @param identifier
	 *            释放锁标识
	 * @return 成功：true,失败:false
	 */
	boolean release(String lockName, String identifier);
}
