package com.fsmeeting.live.generator.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.fsmeeting.live.generator.IGenerator;

/**
 * 用户ID生成器,Range:[0x50000000,0x5FFFFFFF]
 * 
 * @author yicai.liu<moon>
 *
 */
@Component("userIDMemGenerator")
public class UserIDMemGenerator implements IGenerator<Integer> {

	/**
	 * ID操作原子性
	 */
	private AtomicInteger userId = new AtomicInteger(0x4FFFFFFF);

	public int getUserId() {
		return userId.get();
	}

	@Override
	public Integer generate() {
		userId.compareAndSet(0x5FFFFFFF, 0x4FFFFFFF);
		return userId.incrementAndGet();
	}

	public static void main(String[] args) {
		int poolSize = 200;
		final UserIDMemGenerator ge = new UserIDMemGenerator();
		System.out.println("init:" + ge.getUserId());
		ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
		for (int i = 0; i < poolSize; i++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 10000; i++) {// 1W
						ge.generate();
						try {
							TimeUnit.MILLISECONDS.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}

		executorService.shutdown();
		System.out.println(".......");
		while (!executorService.isTerminated()) {

		}
		System.out.println("final:" + ge.getUserId());
	}
}
