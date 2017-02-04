package com.fsmeeting.live.common.serializer;

import org.nustaq.serialization.FSTConfiguration;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *  de.ruedigermoeller fst 序列化
 * 		优点：速度较快,可读性差
 * 		缺点: 序列化后二进制长度，占用存储空间较小
 * </pre>
 * 
 * @author yicai.liu<moon>
 *
 */
@Component("fstSerializer")
public class FSTSerializer implements ISerialization<Object, byte[]> {

	public static final byte ID = 0x00;

	private static FSTConfiguration configuration = FSTConfiguration.createStructConfiguration();

	@Override
	public byte[] serialize(Object object) throws Exception {
		return configuration.asByteArray(object);
	}

	@Override
	public Object deserialize(byte[] bytes) throws Exception {
		return configuration.asObject(bytes);
	}

	@Override
	public byte getContentTypeId() {
		return ID;
	}

}
