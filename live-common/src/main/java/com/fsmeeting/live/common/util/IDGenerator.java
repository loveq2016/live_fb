package com.fsmeeting.live.common.util;

import java.util.Random;

/**
 * ID 生成策略，后面改进
 * 
 * @author yicai.liu<moon>
 *
 */
public class IDGenerator {

	private static Random rnd = new Random();

	/**
	 * ID生成
	 * 
	 * @return
	 */
	public static int generate() {
		return Math.abs(rnd.nextInt());
	}

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			System.out.println(generate());
		}
	}
}
