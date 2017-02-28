package com.broada.spring.data;

import java.io.File;

import com.utilhelper.ProperUtil;


/**
 * Definition for getting message string from properties
 * 
 */
public class Messages {
	
	private static ProperUtil p = new ProperUtil("conf" + File.separator
			+ "conf.properties");

	public static String getString(String key) {
		return p.getProperty(key);
	}
	
}