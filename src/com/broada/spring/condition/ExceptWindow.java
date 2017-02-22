package com.broada.spring.condition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * …Ë÷√¥∞ø⁄«–ªª
 * 
 * @author Administrator
 * 
 */
public class ExceptWindow implements ExpectedCondition<WebDriver> {
	private String id;

	public ExceptWindow(String id) {
		this.id = id;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	public WebDriver apply(WebDriver d) {
		WebDriver newWindow = null;
		newWindow = d.switchTo().window(id);
		return newWindow;
	}
}