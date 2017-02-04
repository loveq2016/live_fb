package com.fsmeeting.live.common.serializer;

import java.nio.charset.Charset;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.fsmeeting.live.common.bean.LiveRoom;

/**
 * <pre>
 *  alibaba fast json 
 * 		优点：速度快,可读性强（利于界面查询）
 * 		缺点:字符存储略大，占网络
 * 	无妨，时间重要
 * </pre>
 * 
 * @author yicai.liu<moon>
 *
 */
@Component("fastJsonSerializer")
public class FastJsonSerializer implements ISerialization<Object, byte[]> {

	public static final byte ID = 0x01;

	@Override
	public byte getContentTypeId() {
		return ID;
	}

	@Override
	public byte[] serialize(Object object) throws Exception {
		return JSON.toJSONBytes(object);
	}

	@Override
	public Object deserialize(byte[] input) throws Exception {
		return JSON.parse(input);
	}

	public static void main(String[] args) throws Exception {
		LiveRoom room = new LiveRoom();
		room.setCompanyId(ID);
		room.setCreationTime(new Date());
		room.setStartTime(new Date());
		room.setLiveAddress("localhost");
		System.out.println(room);
		ISerialization<Object, byte[]> serializer = new FastJsonSerializer();
		byte[] stringJson = serializer.serialize(room);
		String test = new String(stringJson, Charset.defaultCharset());
		System.out.println(test);
		//System.out.println(stringJson + "\n" + ".................");
		LiveRoom o = JSON.parseObject(stringJson, LiveRoom.class);
		//System.out.println(o.getLiveAddress());
		byte[] a = serializer.serialize("abc");
		//System.out.println(serializer.deserialize(a));
	}
}
