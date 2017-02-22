package com.broada.spring.condition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * 
 * …Ë÷√frame«–ªª
 * 
 * @author chingsir
 * 
 */
public class ExceptFrame implements ExpectedCondition<WebDriver> {

	private Object par;

	public ExceptFrame(Object par) {
		this.par = par;
	}

	public WebDriver apply(WebDriver d) {
		WebDriver driver = null;
		if (par instanceof String) {
			String frame = (String) par;
			driver = d.switchTo().frame(frame);
		} else if (par instanceof Integer) {
			driver = d.switchTo().frame((Integer) par);
		}
		return driver;
	}
}