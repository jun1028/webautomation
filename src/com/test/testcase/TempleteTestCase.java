package com.test.testcase;

import java.io.File;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.test.basetest.FileTypeTest;
import com.test.basetest.HtmlPage;
import com.test.basetest.data.ParamDataFactory;
import com.test.basetest.data.SuiteData;

public abstract class TempleteTestCase extends FileTypeTest {

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
