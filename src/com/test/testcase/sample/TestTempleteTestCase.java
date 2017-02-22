package com.test.testcase.sample;

import java.util.Map;

import org.testng.annotations.Test;

import com.test.basetest.data.SuiteData;
import com.test.testcase.TempleteTestCase;

public class TestTempleteTestCase extends TempleteTestCase {

	@Test(dataProvider = "createMapObj", timeOut = 30000, priority = 1)
	public void testLogin(Object obj) {
		testMethod(obj, "test");
	}
	
	public boolean test(Object obj) {
		boolean bResult = false;
		Map map = (Map) obj;
		page.openUrl(SuiteData.baseUrl);
		page.input("百度输入框", "test");
		page.click("百度提交按钮");
		bResult = true;
		return bResult;
	}

}
