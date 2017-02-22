package com.test.basetest;

import java.io.File;

import org.apache.commons.logging.Log;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.test.basetest.data.ParamDataFactory;
import com.test.basetest.data.SuiteData;
import com.test.common.FormatUtil;
import com.test.common.LogHelper;

public class ExcelTypeTest extends BaseTest {

	private static Log log = LogHelper.getLog(FileTypeTest.class);
	protected String shName = null;
	protected int flag = 0;

	/** -1 ,means parameter object is null */
	@DataProvider
	public Object[][] createMapObj() {
		Object[][] obj = null;
		System.out.println(shName);
		if (paramData != null && shName != null) {
			try {
				obj = paramData.getParamDataByKey(FormatUtil
						.formatShName(shName));
			} catch (Exception e) {
				errMessage.append(e.getMessage());
			}
		}
//		if (obj == null) {
//			errMessage.append("dataprovider create parameter fail \n");
//			errMessage
//					.append("please make sure  the testcase name is same with sheet name of parameter \n");
//			errMessage.append(" the sheet name " + shName + "\n");
//			log.error(errMessage);
//			flag = -1;
//		}
		return obj;
	}

	@Parameters( { "filename", "table-name-of-page-element" })
	@BeforeTest
	public void beforeTest(@Optional("testcase.xls") String fName,
			@Optional("totalelement;firstpage") String tbNameOfPageEl) {
		this.fName = SuiteData.rootDirOfTestcase + File.separator + fName;
		if (page == null) {
			page = new HtmlPage(getStrOfBrowser(SuiteData.browserType));
		}
		this.tbNameOfPageEl = tbNameOfPageEl;
		loadPageDataDb(tbNameOfPageEl, page); //loadÒ³ÃæÔªËØµÄ
		paramData = ParamDataFactory.createExcelParamData(this.fName);
	}

	@AfterTest
	public void afterTest() {
		page.close();
		page = null;
	}
}
