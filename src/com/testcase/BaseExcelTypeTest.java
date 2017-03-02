package com.testcase;

import java.io.File;

import org.apache.commons.logging.Log;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.test.basetest.ExcelTypeTest;
import com.test.basetest.HtmlPage;
import com.test.basetest.data.ParamDataFactory;
import com.test.basetest.data.SuiteData;
import com.test.common.LogHelper;

public class BaseExcelTypeTest extends ExcelTypeTest {

	private static Log log = LogHelper.getLog(BaseExcelTypeTest.class);

	@Parameters( { "filename", "table-name-of-page-element" })
	@BeforeTest
	public void beforeTest(@Optional("testcase.xls") String fName,
			@Optional("totalelement;firstpage") String tbNameOfPageEl) {
		log.info("BaseExcelTypeTest beforeTest method");
		this.fName = SuiteData.rootDirOfTestcase + File.separator + fName;
		if (page == null) {
			page = new HtmlPage(getStrOfBrowser(SuiteData.browserType));
			// page = HtmlPage.getInstance(suiteData.browserType);
		}
		this.tbNameOfPageEl = tbNameOfPageEl;
		loadPageDataDb(tbNameOfPageEl, page);
		paramData = ParamDataFactory.createExcelParamData(this.fName);
	}

	@AfterTest
	public void afterTest() {
		log.info("BaseExcelTypeTest afterTest method");
		page.close();
		page = null;
	}

	@AfterSuite
	public void afterSuite() {
	}
}
