/**
 * 
 */
package com.test.basetest;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.jetty7.util.log.Log;

import com.broada.spring.BS2AutoTest;
import com.broada.spring.BrowserType;
import com.test.basetest.check.BaseCheck;
import com.test.fixture.IActions;


/**
 * The page class is responsible for operation page element, simulate user
 * operate
 * 
 * @author water
 * 
 */
public class HtmlPage extends BS2AutoTest implements IActions {

	// private static HtmlPage htmlPage;

	public HtmlPage() {
		super();
	}

	public HtmlPage(BrowserType bt) {
		super(bt);
	}
	
	public BrowserType getBrowserType(String bt) {
		BrowserType type = BrowserType.FIREFOX; 
		if (bt != null && !bt.equals("")) {
			bt = bt.toLowerCase(); } 
		if (bt.equals("ie")) {
			type = BrowserType.IE; }
		else if(bt.equals("chrome")) {
			type = BrowserType.CHROME;}
		else {
			type = BrowserType.FIREFOX;
		}
		return type;
	}
	
	public WebElement findElementByCss(String cssValue) {
		WebElement el = null;
		el = browser.findElement(By.cssSelector(cssValue));
		return el;
	}

	public WebElement findElementByXpath(String xpathValue) {
		WebElement el = null;
		el = browser.findElement(By.xpath(xpathValue));
		return el;
	}

	public WebElement findElementById(String idValue) {
		WebElement el = null;
		el = browser.findElement(By.id(idValue));
		return el;
	}

	public void selectLi(String key, String value) {
		sleep(2); 
		List options = this.getElementsByXpath(key);
		if (options != null) {
			for (int i = 0; i < options.size(); i++) {
				WebElement option = (WebElement) options.get(i);
				if (value.equalsIgnoreCase(option.getText())) {
					option.click();
					break;
				}
			}
		}
	}

	@Override
	public void input(String key, String value) {
		this.ClearAndSetText(key, value);
	}

	public void inputNoClear(String key, String value) {
		super.input(key, value);
	}

	public void ClearAndSetText(String key, String text) {
		WebElement element = getElement(key);
		if (element == null) {
			String message = "get element fail!the key is " + key;
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		Actions navigator = new Actions(this.browser);
		String value = element.getAttribute("value");
		Keys[] keys = new Keys[value.length()];
		for (int i = 0; i < keys.length; i++) {
			keys[i] = Keys.BACK_SPACE;
		}
		navigator.click(element);
		navigator.sendKeys(Keys.chord(keys));
		navigator.sendKeys(text);
		navigator.perform();
	}

	public void quit() {
		browser.quit();
		this.browser = null;
		this.currentBrowser = null;
	}
	
	@Override
	public void close() {
		super.close();
		this.browser = null;
		this.currentBrowser = null;
	}
	
	public void closeDriver() {
		WebDriver driver = this.browser;
		String currentHandle = driver.getWindowHandle();
		Iterator windowIterator = driver.getWindowHandles().iterator();
		while (windowIterator.hasNext()) {
			String windowHandle = (String) windowIterator.next();
			System.out.println(windowHandle);
			if (!windowHandle.equals(currentHandle)) {
				driver.switchTo().window(windowHandle);
				driver.close();
			}
		}
		driver.close();
	}
	
	@Override
	public boolean alertExists(int seconds) {
		boolean bExist = false;
		long start = System.currentTimeMillis();
		while ((System.currentTimeMillis() - start) < seconds * 1000) {
			try {
				browser.switchTo().alert();
				bExist = true;
			} catch (NoAlertPresentException e) {
				log.error(e); // need implement
			} catch (Exception e) {
				log.error(e);
			}
		}
		return bExist;
	}

	@Override
	public void screenShot(String message) {
		super.screenShot(message);
	}
	public void reOpen() {
		WebDriver driver = this.browser;
		String currentHandle = driver.getWindowHandle();
		driver.close();
		driver.switchTo().window(currentHandle);
	}
	
	// process modal dialog
	public boolean closeNewWindow() {
		try {
			WebDriver driver = this.browser;
			String currentHandle = driver.getWindowHandle();
			Iterator windowIterator = driver.getWindowHandles().iterator();
			while (windowIterator.hasNext()) {
				String windowHandle = (String) windowIterator.next();
				System.out.println(windowHandle);
				if (!windowHandle.equals(currentHandle)) {
					driver.switchTo().window(windowHandle);
					driver.close();
					break;
				}
			}
			driver.switchTo().window(currentHandle);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	@Override
	public void click(String element) {
		super.click(element);
	}

	@Override
	public void doubleClick(String element) {
		super.doubleClick(element);
	}

	@Override
	public void moveOn(String elment) {
		super.moveOn(elment);
	}

	/***
	 * @param checkType
	 *            check type, (URL, TEXT, ELEMENT)
	 */
	public boolean check(String checkType, String expected) {
		boolean bResult = false;
		bResult = BaseCheck.parseExpected(checkType, expected, this);
		return bResult;
	}


	@Override
	public void open(String url) {
		openUrl(url);
	}

	@Override
	public void select(String obj) {
	}
}
