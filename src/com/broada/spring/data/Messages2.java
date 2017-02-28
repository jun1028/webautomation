package com.broada.spring.data;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

/**
 * Definition for getting message string from properties
 * 
 * @author chingsir
 * 
 */
public class Messages2 {
	private static final String BUNDLE_NAME = "conf"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Messages2() {
	}

	public static String getString(String key) {
		String value = RESOURCE_BUNDLE.getString(key);
		try {
			String keyValue = new String(value.getBytes("ISO-8859-1"), "GBK");
			return keyValue;
		} catch (UnsupportedEncodingException e) {
			return '!' + key + '!';
		}
	}
}