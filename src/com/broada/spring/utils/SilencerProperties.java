package com.broada.spring.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;

public class SilencerProperties {
	private static HashMap<String, String> map;

	private SilencerProperties() {
		map = new HashMap<String, String>();
		loadFile();

	}

	// modify by wury
	private static SilencerProperties silencerProperties = null;

	public static SilencerProperties getInstance() {
		if (silencerProperties == null) {
			silencerProperties = new SilencerProperties();
		}
		return silencerProperties;

	}

	private void loadFile() {
		String filePath = System.getProperty("user.dir")
				+ "/conf/silencer.properties";
		InputStream in = null;
		InputStreamReader ir = null;
		LineNumberReader input = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			ir = new InputStreamReader(in);
			input = new LineNumberReader(ir);
			String line;
			line = input.readLine();

			while (line != null) {
				parceLine(line);
				line = input.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ir != null) {
					ir.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void parceLine(String line) {
		String str = line;
		String tmp = str.trim();
		if (tmp.startsWith("#") || tmp.isEmpty()) {
			return;
		}
		String[] value = tmp.split("=", 2);
		map.put(value[0].trim(), value[1].trim());

	}

	public String getString(String key) {
		return map.get(key);
	}
}
