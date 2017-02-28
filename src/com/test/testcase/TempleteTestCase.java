package com.test.testcase;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.test.basetest.BaseTest;
import com.test.basetest.HtmlPage;
import com.test.basetest.data.SuiteData;
import com.test.common.FormatUtil;
import com.test.common.LogHelper;

public abstract class TempleteTestCase extends BaseTest {
	
	private static Log log = LogHelper.getLog(TempleteTestCase.class);
	
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
	
	//@Optional("testcase.xls") String fName,
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

}
