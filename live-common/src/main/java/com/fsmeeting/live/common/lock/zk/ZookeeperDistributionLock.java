package com.fsmeeting.live.common.lock.zk;

import com.fsmeeting.live.common.lock.IDistributionLock;

/**
 * 分布式锁之Zookeeper实现
 * 
 * @author yicai.liu<moon>
 *
 */
public class ZookeeperDistributionLock implements IDistributionLock {

	@Override
	public String acquire(String lockName, long acquireTimeoutMS, long lockTimeoutMS) {
		return null;
	}

	@Override
	public boolean release(String lockName, String identifier) {
		return false;
	}

}
