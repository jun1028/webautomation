package com.test.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogHelper {

	public static Log getLog(Class<?> obj) {
		return LogFactory.getLog(obj);
	}

}
