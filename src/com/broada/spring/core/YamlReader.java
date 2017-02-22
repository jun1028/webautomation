package com.broada.spring.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.ho.yaml.Yaml;

/**
 * 
 * @author chingsir
 * 
 */
public class YamlReader {

	private File file;

	public YamlReader(File f) {
		this.file = f;
	}

	/**
	 * ¶ÁÈ¡yamlÎÄ¼þ
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public HashMap getDataMap() throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		String encoding = "GBK";
		InputStreamReader read = null;
		HashMap map = null;
		read = new InputStreamReader(new FileInputStream(file), encoding);
		if (read != null) {
			map = Yaml.loadType(read, HashMap.class);
			read.close();
		}
		return map;
	}
}
