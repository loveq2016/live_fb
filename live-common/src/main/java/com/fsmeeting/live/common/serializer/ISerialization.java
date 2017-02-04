package com.fsmeeting.live.common.serializer;

/**
 * 序列化
 * 
 * @author yicai.liu<moon>
 * @param <O>
 *            源对象
 * @param <D>
 *            目标对象
 * @param Object
 *
 */
public interface ISerialization<O, D> {

	/**
	 * 获取序列化方式标志
	 * 
	 * @return
	 */
	byte getContentTypeId();

	/**
	 * 序列化：源对象——>目标对象
	 * 
	 * @param from
	 * @return
	 * @throws Exception
	 */
	D serialize(O from) throws Exception;

	/**
	 * 反序列化：目标对象——>源对象
	 * 
	 * @param to
	 * @return
	 * @throws Exception
	 */
	O deserialize(D to) throws Exception;
}