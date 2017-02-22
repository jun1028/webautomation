package com.broada.spring.data;

import java.util.HashMap;

import org.apache.commons.logging.Log;

/**
 * 
 * @author chingsir
 * 
 */
public class InputData {

	private ExcelReader inputdata;
	private HashMap<String, String> dataMap;
	private HashMap<String, String> exptMap;
	private Log log;
	private String flag;

	public InputData(String f, Log log) {
		this.log = log;
		this.inputdata = new ExcelReader(f, this.log);
		this.dataMap = this.inputdata.getDataMap();
		this.exptMap = this.inputdata.getExpectMap();
		this.flag = inputdata.getFlag();
	}

	public String getInputData(String key) {
		return this.dataMap.get(key);
	}

	public String getExpData(String key) {
		return this.exptMap.get(key);
	}

	public String getFlag() {
		return this.flag;
	}
}
