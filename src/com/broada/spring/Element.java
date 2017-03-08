package com.broada.spring;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.broada.spring.condition.ExceptElement;
import com.broada.spring.data.ElementData;
import com.conf.Messages;

public class Element extends Browser {

	public Element() {
		super();
	}

	public Element(BrowserType bt, Object... args) {
		super(bt, args);
	}

	public String getXpathValue(String key) {
		String xpath = null;
		for (ElementData trace : this.traceList) {
			xpath = trace.getXpathValue(key);
			if (xpath != null)
				break;
		}
		return xpath;
	}

	public Map getKeyValue(String key) {
		Map value = null;
		for (ElementData trace : this.traceList) {
			value = trace.getKeyValue(key);
			if (value != null)
				break;
		}
		return value;
	}

	/**
	 * 获取Yaml文件中指定key的元素
	 * 
	 * @param key
	 *            在yaml文件中的元素
	 * @return
	 */
	public WebElement getElement(String key) {
		WebElement element = null;
		try {
			for (ElementData trace : this.traceList) {
				element = trace.getData(key);
				if (element != null) {
					break;
				}
			}
		} catch (TimeoutException e) {
			String message = "获取元素[" + key + "]失败";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
			return null;
		}
		if (element == null) {
			String message = "获取元素[" + key + "]失败";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
			return null;
		}
		// try {
		// this.waitUntilElementVisible(element);
		/**
		 * the method return boolean value. and never throw the exception
		 * modify :
		 * 	comment try statement and add if statement ,if not waitUntilElementVisibl,then element = null;
		 * @author water
		 * @date 2013.12.24
		 */
		if (!this.waitUntilElementVisible(element))
			element = null;
		// } catch (ElementNotVisibleException ene) {
		//            this.log.error(Messages.getString("AutoTest.getElment") + key //$NON-NLS-1$
		//                    + Messages.getString("AutoTest.fail")); //$NON-NLS-1$
		// String message = "获取元素[" + key + "]失败";
		// this.screenShot(message);
		// throwException(message);
		// }
		return element;
	}

	public WebElement getElement(String key, String parameter) {
		WebElement element = null;
		try {
			for (ElementData trace : this.traceList) {
				element = trace.getData(key, parameter);
				if (element != null) {
					break;
				}
			}
		} catch (TimeoutException e) {
			String message = "获取元素[" + key + "]失败";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
			return null;
		}
		if (element == null) {
			String message = "获取元素[" + key + "]失败";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
			return null;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (ElementNotVisibleException ene) {
			this.log.error(Messages.getString("AutoTest.getElment") + key //$NON-NLS-1$
					+ Messages.getString("AutoTest.fail")); //$NON-NLS-1$
			String message = "获取元素[" + key + "]失败";
			this.screenShot(message);
			throwException(message);
		}
		return element;
	}

	/**
	 * 根据提供xpath获取当前页面上的元素 主要用于提供参数化的元素
	 * 
	 * @param xpath
	 * @return
	 */
	public WebElement getElementByXpath(String xpath, String des) {
		WebElement we = null;
		if (xpath == null || xpath.length() == 0) {
			this.log.error("xpath为空。定位元素失败");
		} else {
			try {
				WebDriverWait wait = new WebDriverWait(this.browser, 30);
				By by = By.xpath(xpath);
				ExceptElement ee = new ExceptElement(by);
				we = wait.until(ee);
				this.log.info(des + "成功");
			} catch (TimeoutException nee) {
				this.log.error("通过xpath:[" + xpath + des + "]获取页面元素失败");
				String message = "通过xpath" + des;
				this.screenShot(message);
				throwException("通过xpath:[" + xpath + des + "]获取页面元素失败");
			}
		}
		return we;
	}

	/**
	 * 通过JavaScript获取元素 eg: String jscode=
	 * "var inp = document.getElementById('kw'); return inp;";
	 * 
	 * WebElement we = autoTest.getElementByJavaScript(jscode);
	 * we.sendKeys("WebElement");
	 * 
	 * @param jscode
	 * @return
	 */
	public WebElement getElementByJavaScript(String jscode) {
		WebElement jelement = null;
		jelement = (WebElement) this.executeJavaScript(jscode);
		return jelement;
	}

	/**
	 * 获取对应标签的元素列表
	 * 
	 * @param tag
	 * @return
	 */
	public List<WebElement> getElementsByTag(String tag) {
		return this.getBrowser().findElements(By.tagName(tag));
	}

	public List<WebElement> getElementsByXpath(String keyXpath) {
		List lResult = null;
		String xpath = this.getXpathValue(keyXpath);
		if (xpath != null) {
			lResult = this.getBrowser().findElements(By.xpath(xpath));
		}
		return lResult;
	}

	/**
	 * 获取Yaml文件中索引key元素的文本内容。
	 * 
	 * @param key
	 * @return 返回文本内容
	 */
	public String getText(String key) {
		WebElement element = this.getElement(key);
		return element.getText();
	}

	public String getText(String key, String parameter) {
		WebElement element = this.getElement(key, parameter);
		return element.getText();
	}

	/**
	 * 判断指定的对象是否存在。
	 * 
	 * @param key
	 *            yaml文件定位元素的key
	 */
	public boolean elementExists(String key) {
		boolean exist = true;
		try {
			exist = this.getElement(key) != null ? true : false;
		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}

	public boolean elementExists(String key, String parameter) {
		boolean exist = true;
		try {
			exist = this.getElement(key, parameter) != null ? true : false;
		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}

	public boolean exists(By by) {
		boolean exist = true;
		try {
			WebElement element;
			element = this.browser.findElement(by);
			exist = element != null ? true : false;
			if (element != null) {
				exist = element.isDisplayed() ? true : false;
			}
		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}

	/**
	 * 判断指定的对象是否存在。
	 * 
	 * @param xpath
	 *            元素的xpath定位字符串
	 */
	public boolean elementExistsByXpath(String xpath) {
		boolean exist = true;
		try {
			exist = this.getElementByXpath(xpath, "元素是否存在") != null ? true
					: false;
		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}

	/**
	 * 元素是否可见
	 * 
	 * @param element
	 * @return
	 */
	public boolean elementExists(WebElement element) {
		boolean exist = true;
		if (element == null) {
			exist = false;
		}
		return exist;
	}

	/**
	 * 指定yaml文件中的索引为key元素，获取该元素propertyNam的属性值
	 * 
	 * @param key
	 *            获取Yaml中元素的索引
	 * @param propertyName
	 *            获取属性对象
	 * @return
	 */
	public String getElementCSSValue(String key, String propertyName) {
		String value = null;
		WebElement element = this.getElement(key);
		value = element.getCssValue(propertyName);
		return value;
	}

	/**
	 * 在指定时间内循环等待，直到对象可见，超时之后直接抛出对象不可见异常信息。
	 * 
	 * @param element
	 *            the WebElement to be judged
	 * @param timeout
	 *            timeout setting in seconds
	 */
	protected boolean waitUntilElementVisible(WebElement element, int timeout) {
		long start = System.currentTimeMillis();
		boolean isDisplayed = false;
		while (!isDisplayed
				&& ((System.currentTimeMillis() - start) < timeout * 1000)) {
			isDisplayed = (element == null) ? false : element.isDisplayed();
		}
		if (!isDisplayed) {
			String error = "当前元素在" + timeout + "秒里不能识别";
			this.log.error(error);
			this.screenShot(error);
		}
		return isDisplayed;

	}

	/**
	 * 在指定时间内循环等待，直到对象可见，使用用户指定的默认超时设置。
	 * 
	 * @param element
	 *            the WebElement to be judged
	 */
	protected boolean waitUntilElementVisible(WebElement element) {
		return waitUntilElementVisible(element, maxWaitfor);
	}

	/**
	 * 将元素只读属性取消
	 * 
	 * @param key
	 */
	public void writenableElement(String key) {
		WebElement element = this.getElement(key);

		String arg = "arguments[0].readOnly = \"\"";// HIGHLIGHT_BORDER
		this.executeJavaScript(arg, element);
	}

	public void writenableElement(String key, String parameter) {
		WebElement element = this.getElement(key, parameter);
		String arg = "arguments[0].readOnly = \"\"";// HIGHLIGHT_BORDER
		this.executeJavaScript(arg, element);
	}
}
