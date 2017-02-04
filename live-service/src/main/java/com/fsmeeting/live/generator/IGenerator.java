package com.fsmeeting.live.generator;

/**
 * 生成器
 * 
 * @author yicai.liu<moon>
 * @param <T>
 *
 */
public interface IGenerator<T> {

	/**
	 * 生成内容<T>
	 * 
	 * @param params
	 * @return
	 */
	public T generate();
}
