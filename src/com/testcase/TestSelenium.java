package com.testcase;

import org.testng.annotations.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class TestSelenium {

	@Test
	public void f() throws Exception {
		Selenium brower = new DefaultSelenium("localhost", 4444, "firefox",
				"http://www.baidu.com");
		brower.start("http://www.baidu.com");
		brower.open("http://www.baidu.com");
	}
}
