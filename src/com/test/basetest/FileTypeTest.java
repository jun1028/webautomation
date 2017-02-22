package com.test.basetest;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.testng.annotations.DataProvider;

import com.test.common.FormatUtil;
import com.test.common.LogHelper;

/***
 * the class provider some function for file type (java class or java scripts)
 * 1) initial login in/out 2) @DataProvider , parameter data
 * 
 * @author water
 * 
 */
public class FileTypeTest extends BaseTest {

	private static Log log = LogHelper.getLog(FileTypeTest.class);

	@DataProvider
	public Object[][] createMapObj(Method m) {
		Object[][] obj = null;
		String mName = m.getName(); // get method name
		log.debug("the method name is: " + mName);
		if (paramData != null) {
			/** get parameter data by method name */
			try {
				mName = FormatUtil.formatShName(mName);
				obj = paramData.getParamDataByKey(mName);
			} catch (Exception e) {
				errMessage.append(e.getMessage());
			}
		}
//		if (obj == null) {
//			errMessage.append("dataprovider create parameter fail \n");
//			errMessage
//					.append("please make sure  the method name is same with sheet name of parameter\n");
//			errMessage.append("the method name " + mName);
//			errMessage.append(" system wiil exit");
//			log.error(errMessage);
//			System.exit(0);
//		}
		return obj;
	}
}
