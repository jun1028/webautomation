package com.testcase;


import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.test.basetest.BaseTest;
import com.test.basetest.HtmlPage;
import com.test.basetest.data.SuiteData;
import com.test.common.FormatUtil;
import com.test.common.LogHelper;

public class TempleteTestCase2 extends BaseTest  {
	
	private static Log log = LogHelper.getLog(TempleteTestCase2.class);

//	@Test(dataProvider = "createMapObj", timeOut = 30000, priority = 1)
	@Test(timeOut = 30000, priority = 1)
	public void testLogin() {
		testMethod("test");
	}
	
	public boolean test() {
		boolean bResult = true;
		page.openUrl("http://www.moojnn.com");
		page.click("登录注册按钮");
		page.sleep(2);
		page.input("用户名输入框", "18521384218");
		page.input("密码输入框","123456");
		page.click("登录按钮");
		page.click(page.findElementByXpath("//div[@class=\"projectName\"]"));
//		bResult = page.check("TEXT", "数据源");
		return bResult;
	}
	
	@Parameters( {"table-name-of-page-element" })
	@BeforeTest
	public void beforeTest(@Optional("totalelement;firstpage") String tbNameOfPageEl) {
		if (page == null) {
			page = new HtmlPage(getStrOfBrowser(SuiteData.browserType));
			// page = HtmlPage.getInstance(suiteData.browserType);
		}
		this.tbNameOfPageEl = tbNameOfPageEl;
		loadPageDataDb(tbNameOfPageEl, page);
//		fName = SuiteData.rootDirOfTestcase + File.separator + fName;
//		paramData = ParamDataFactory.createExcelParamData(fName);
	}

	@AfterTest
	public void afterTest() {
		page.close();
		page = null;
	}

	@AfterSuite
	public void afterSuite() {
		if (page != null)
			page.close();
	}

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
		return obj;
	}
}
