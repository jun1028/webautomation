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
		page.click("登录注册按钮");
		page.sleep(2);
		page.input("用户名输入框", "18521384218");
		page.input("密码输入框","123456");
		page.click("登录按钮");
		bResult = page.check("TEXT", "数据源");
		return bResult;
	}

}
