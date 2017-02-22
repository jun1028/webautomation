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
		page.input("�ٶ������", "test");
		page.click("�ٶ��ύ��ť");
		bResult = true;
		return bResult;
	}

}
