package com.test.basetest.data;

import java.util.Iterator;

public interface IParamData {
	/**
	 * data(parameter) provider for test case 
	 * see at {@link http://testng.org/doc/documentation-main.html#parameters-dataproviders}
	 * 
	 * @param shName
	 *            sheet name in excel file
	 * @param fName
	 *            excel file name
	 * @return
	 * */
	public Object[][] getParamDataByShName(String fName, String shName);

	/**
	 * @param key
	 *            sheet name or method name
	 * @return
	 */
	public Object[][] getParamDataByKey(String key);

	/***/
	public Iterator<Object[]> getIParamDataByShName(String shName);

	public Iterator<Object[]> getIParamDataByKey(String key);
}
