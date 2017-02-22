package com.broada.spring.condition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * 设置元素查找，元素存在
 * 
 * @author Administrator
 * 
 */
public class ExceptElement implements ExpectedCondition<WebElement> {
	private By by;

	public ExceptElement(By by) {
		this.by = by;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	public WebElement apply(WebDriver d) {
		WebElement element = null;
		element = d.findElement(by);
		return element;
	}
}