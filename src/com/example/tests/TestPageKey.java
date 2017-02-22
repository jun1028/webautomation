package com.example.tests;

import com.broada.spring.BrowserType;
import com.test.basetest.HtmlPage;

public class TestPageKey {
	public static HtmlPage page = new HtmlPage();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		page.elementExists("");
	}

	public boolean elementExists(String key) {
		boolean bResult = false;
		bResult = page.elementExists(key);
		if (bResult) {
			System.out.println("the key " + key + " exists");
		} else
			System.out.println("the key " + key + " does not exists");
		return bResult;
	}
}
