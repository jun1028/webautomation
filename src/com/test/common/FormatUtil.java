package com.test.common;

public class FormatUtil {

	/***
	 * remove _testcase , _params or test section!
	 * 
	 * @param key
	 * @return
	 */
	public static String formatShName(String key) {
		key = key.toLowerCase();
		// remove "_testcase" string
		key = key.replaceFirst(DataConsts.TAG_SHEETTESTCASESUF, ""); 
		//remove "test" string
		key = key.replaceFirst(DataConsts.TAG_TESTMETHODPRF, ""); 
		// remove "_params" string
		key = key.replaceFirst(DataConsts.TAG_SHEETPARAMSUF, ""); 
		return key;
	}

}
