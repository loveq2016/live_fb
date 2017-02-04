package com.fsmeeting.live.common.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统常量
 * 
 * @author yicai.liu<moon>
 *
 */
@Component("systemInfo")
public class SystemInfo {

	/**
	 * 前置服务器的地址
	 */
	@Value("${server.prepositive}")
	private String prepositive;

	
	public String getPrepositive() {
		return prepositive;
	}

	public void setPrepositive(String prepositive) {
		this.prepositive = prepositive;
	}

}
