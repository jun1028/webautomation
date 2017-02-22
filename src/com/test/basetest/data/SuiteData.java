package com.test.basetest.data;

import java.io.File;

import com.test.common.ProperUtil;

public class SuiteData {
	/** retry counts after test fail, default don't retry */
	public static int retries      = 1;
	public static int sleepTime    = 1;
	/** default browser */
	public static String browserType        = "chrome";
	public static String rootDirOfTestcase  = "testcases"; 
	public static String baseUrl            = "http://www.baidu.com";
	static {
		ProperUtil p = new ProperUtil("conf" + File.separator
				+ "conf.properties");
		retries       = Integer.valueOf(p.getProperty("AutoTest.retries"));
		sleepTime     = Integer.valueOf(p.getProperty("AutoTest.sleepTime"));
		browserType        = p.getProperty("AutoTest.browserType");
		rootDirOfTestcase  = p.getProperty("AutoTest.dirOfTestcase");
		baseUrl            = p.getProperty("AutoTest.baseUrl");
	}

}
