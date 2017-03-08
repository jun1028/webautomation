package com.test.basetest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;

import com.broada.spring.BrowserType;
import com.broada.spring.utils.Utils;
import com.test.basetest.data.ElementData;
import com.test.basetest.data.IParamData;
import com.test.basetest.data.SuiteData;
import com.test.common.DataConsts;
import com.test.common.LogHelper;

/***
 * the class correspond to a test path in SUT(system under test),by passing
 * parameter to achieve test multiple test case. the parameter in sheet of excel
 * map a test method name by sheet name
 * 
 * @author water
 * 
 */
public class BaseTest extends ElementData implements ITest {

	private static Log log = LogHelper.getLog(BaseTest.class);

	protected StringBuffer errMessage = new StringBuffer();
	protected String curParamShName = "";
	protected int curRetryies = 0;
	/** test parameter or test case excel or xml file name */
	protected String fName = "";
	/**
	 * the key of the map is method name ,the value is parameter sheet name in
	 * excel file!
	 */
	public HtmlPage page = null;
	/** test case parameter */
	public IParamData paramData = null;

	public String tbNameOfPageEl = "";

	/** don't need parameter object! default null */
	public boolean testMethod(String testName) {
		return testMethod(null, testName);
	}

	public boolean testMethod(Object obj, String testName) {
		boolean bResult = false;
		for (curRetryies = 0; curRetryies < SuiteData.retries; curRetryies++) {
			StringBuffer sb = new StringBuffer();
			sb.append("the ");
			sb.append(curRetryies);
			Object temp = null;
			log.debug("invoke test method!" + testName);
			if (obj == null) {
				temp = invokeMethod(this, testName);
			} else {
				Object[] args = { obj };
				temp = invokeMethod(this, testName, args);
			}
			if (temp != null) {
				bResult = Boolean.parseBoolean(temp.toString());
			}
			sb.append(" retry result is ");
			sb.append(bResult);
			log.debug(sb);
			if (bResult)
				break; // if test pass, break
			else {
				page.sleep(20);
				page.browserRefresh();
				initLogout();
				//this.initAfterFail();
			}
		}
		page.browserRefresh();
		return bResult;
	}

	public void init() {
		log.debug("initial test enviroment!");
		if (page == null) {
			page = new HtmlPage(getStrOfBrowser(SuiteData.browserType));

		} else {
			page.browserRefresh();
		}
	}

	public void initLogin() {
	}

	public void intitLogin(Map map) {
	}

	public void initAfterFail() {
		page.closeDriver();
	}

	public void initLogout() {
	}

	public BrowserType getStrOfBrowser(String bt) {
		BrowserType type = BrowserType.FIREFOX; // default firefox
		if (bt != null && !bt.equals("")) {
			bt = bt.toLowerCase();
			if (bt.equals("ie"))
				type = BrowserType.IE;
			else if (bt.equals("chrome"))
				type = BrowserType.CHROME;
			else if (bt.equals("httpunit"))
				type = BrowserType.HTTPUNIT;
			else
				type = BrowserType.FIREFOX;
		}
		return type;
	}

	public String closeAlertAndGetItsText() {
		String alertText = page.getTextOfAlert();
		page.acceptAlert();
		return alertText;
	}

	public static Object invokeMethod(Object obj, String methodName,
			Object[] args) {
		Class<? extends Object> ownerClass = obj.getClass();
		Object result = null;
		for (Method m : ownerClass.getMethods()) {
			if (methodName.equalsIgnoreCase(m.getName())) {
				try {
					result = m.invoke(obj, args);
				} catch (IllegalArgumentException e) {
					log.debug("illegal argument!, "
							+ "maybe exsits the same method name in class");
					log.error(e);
				} catch (IllegalAccessException e) {
					log.error(e);
				} catch (InvocationTargetException e) {
					log.error("method name:" + m.getName());
					log.error(e);
				}
			}
		}
		return result;
	}

	public static Object invokeMethod(Object obj, String methodName) {
		Object oResult = null;
		Class<? extends Object> ownerClass = obj.getClass();
		Method method = null;
		try {
			method = ownerClass.getMethod(methodName);
		} catch (SecurityException e) {
			log.error(e);
		} catch (NoSuchMethodException e) {
			log.error(e);
		}
		try {
			oResult = method.invoke(obj);
		} catch (IllegalArgumentException e) {
			log.error(e);
		} catch (IllegalAccessException e) {
			log.error(e);
		} catch (InvocationTargetException e) {
			log.error("method name:" + methodName);
			log.error(e);
		}
		return oResult;
	}

	public String formatFailMessage(String failMess) {
		StringBuffer sb = new StringBuffer();
		sb.append("\nThe total of retries is :" + SuiteData.retries);
		sb
				.append(" The " + DataConsts.lOrdinal[curRetryies]
						+ " test fail! \n");
		sb.append("please check png file: ");
		sb.append(Utils.getDate() + failMess + "."
				+ "png in pngs file folder\n");
		return sb.toString();
	}

	public boolean checkResult(Object obj) {
		boolean bResult = false;
		return bResult;
	}
}
