package com.example.tests;

import org.testng.annotations.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class TestSelenium {

	@Test
	public void f() throws Exception {
		Selenium brower = new DefaultSelenium("localhost", 4444, "iexplore",
				"http://www.baidu.com");
		brower.start("http://www.baidu.com");
	}
}
