package com.test.testcase.sample;


import org.testng.annotations.Test;

import com.test.testcase.TempleteTestCase;

public class TestTempleteTestCase extends TempleteTestCase {

//	@Test(dataProvider = "createMapObj", timeOut = 30000, priority = 1)
	@Test(timeOut = 30000, priority = 1)
	public void testLogin() {
		testMethod("test");
	}
	
	public boolean test() {
		boolean bResult = false;
		page.openUrl("http://www.moojnn.com");
		page.click("��¼ע�ᰴť");
		page.sleep(2);
		page.input("�û��������", "18521384218");
		page.input("���������","123456");
		page.click("��¼��ť");
		bResult = page.check("TEXT", "����Դ");
		return bResult;
	}

}
