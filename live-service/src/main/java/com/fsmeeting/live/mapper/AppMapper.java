package com.fsmeeting.live.mapper;

import com.fastonz.live.sliceprotocol.model.AppModel;

/**
 * APP 数据库操作
 * 
 * @author yicai.liu<moon>
 *
 */
public interface AppMapper {

	/**
	 * 查看服务数目
	 * 
	 * @param model
	 *            服务
	 * @return
	 */
	int countServer(AppModel model);

	/**
	 * 注册服务
	 * 
	 * @param model
	 *            服务
	 */
	void registerServer(AppModel model);
}
