package com.fsmeeting.live.generator.impl;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fsmeeting.live.generator.IGenerator;

/**
 * token 生成器
 * 
 * @author yicai.liu<moon>
 *
 */
@Component
public class TokenGenerator implements IGenerator<String> {

	private Random rnd = new Random();

	/**
	 * 生成唯一串System.currentTimeMillis()+YAN+uniq+id
	 * 
	 * @return
	 */
	@Override
	public String generate() {
		long time = System.currentTimeMillis();
		long rmdString = rnd.nextLong();
		String uuid = UUID.randomUUID().toString();
		return MD5(uuid + time + rmdString);
	}

	/**
	 * MD5
	 * 
	 * @param orgin
	 * @return
	 */
	public final static String MD5(String orgin) {
		try {
			byte[] btInput = orgin.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			return byte2hex(mdInst.digest());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将字节数组转换成16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuilder sbDes = new StringBuilder();
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				sbDes.append("0");
			}
			sbDes.append(tmp);
		}
		return sbDes.toString();
	}

	public static void main(String[] args) {
		System.out.println(new TokenGenerator().generate());
	}

}
