package com.test.basetest.data;

import com.conf.Messages;

public class SuiteData {
	/** retry counts after test fail, default don't retry */
	public static int retries      = Integer.valueOf(Messages.getString("AutoTest.retries"));
	public static int sleepTime    = Integer.valueOf(Messages.getString("AutoTest.sleepTime"));
	/** default browser */
	public static String browserType        = Messages.getString("AutoTest.browserType");
	public static String rootDirOfTestcase  = Messages.getString("AutoTest.dirOfTestcase");
	public static String baseUrl            = Messages.getString("AutoTest.baseUrl");

}
