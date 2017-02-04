package com.fsmeeting.live.server.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求队列
 * 
 * @author yicai.liu
 * @version
 * @date 2016年11月14日 下午8:49:14
 */
public class RequestQueue {

	private static final Logger LOG = LoggerFactory.getLogger(RequestQueue.class);

	/**
	 * 队列的容量
	 */
	private int capacity;

	/**
	 * 队列消费线程大小
	 */
	private int threads;

	/**
	 * 阻塞队列
	 */
	private BlockingQueue<Task> queue;

	/**
	 * Spring 容器初始加载
	 */
	public void init() {
		queue = new ArrayBlockingQueue<>(capacity);
		final ExecutorService executor = Executors.newFixedThreadPool(threads);
		for (int i = 0; i < threads; i++) {
			executor.submit(new Runnable() {
				public void run() {
					while (true) {
						try {
							Task task = queue.take();
							task.process();
						} catch (Throwable e) {
							LOG.warn("Task execute exception:", e);
						}
					}
				}
			});
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				executor.shutdown();
			}
		}));

	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public boolean offer(Task data) {
		return queue.offer(data);
	}

}