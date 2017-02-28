package com.utilhelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProperUtil {

	private String fName = "";
	private Properties properties = null;

	public ProperUtil(String fName) {
		this.fName = fName;
	}

	public String getProperty(String key) {
		String result = "";
		if (this.properties == null) {
			try {
				Properties properties = new Properties();
				FileInputStream fis = new FileInputStream(fName);
				properties.load(fis);
				this.properties = properties;
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		result = properties.getProperty(key).trim();
		return result;
	}
}
