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
	 * ��ȡYaml�ļ���ָ��key��Ԫ��
	 * 
	 * @param key
	 *            ��yaml�ļ��е�Ԫ��
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
			String message = "��ȡԪ��[" + key + "]ʧ��";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
			return null;
		}
		if (element == null) {
			String message = "��ȡԪ��[" + key + "]ʧ��";
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
		// String message = "��ȡԪ��[" + key + "]ʧ��";
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
			String message = "��ȡԪ��[" + key + "]ʧ��";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
			return null;
		}
		if (element == null) {
			String message = "��ȡԪ��[" + key + "]ʧ��";
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
			String message = "��ȡԪ��[" + key + "]ʧ��";
			this.screenShot(message);
			throwException(message);
		}
		return element;
	}

	/**
	 * �����ṩxpath��ȡ��ǰҳ���ϵ�Ԫ�� ��Ҫ�����ṩ��������Ԫ��
	 * 
	 * @param xpath
	 * @return
	 */
	public WebElement getElementByXpath(String xpath, String des) {
		WebElement we = null;
		if (xpath == null || xpath.length() == 0) {
			this.log.error("xpathΪ�ա���λԪ��ʧ��");
		} else {
			try {
				WebDriverWait wait = new WebDriverWait(this.browser, 30);
				By by = By.xpath(xpath);
				ExceptElement ee = new ExceptElement(by);
				we = wait.until(ee);
				this.log.info(des + "�ɹ�");
			} catch (TimeoutException nee) {
				this.log.error("ͨ��xpath:[" + xpath + des + "]��ȡҳ��Ԫ��ʧ��");
				String message = "ͨ��xpath" + des;
				this.screenShot(message);
				throwException("ͨ��xpath:[" + xpath + des + "]��ȡҳ��Ԫ��ʧ��");
			}
		}
		return we;
	}

	/**
	 * ͨ��JavaScript��ȡԪ�� eg: String jscode=
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
	 * ��ȡ��Ӧ��ǩ��Ԫ���б�
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
	 * ��ȡYaml�ļ�������keyԪ�ص��ı����ݡ�
	 * 
	 * @param key
	 * @return �����ı�����
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
	 * �ж�ָ���Ķ����Ƿ���ڡ�
	 * 
	 * @param key
	 *            yaml�ļ���λԪ�ص�key
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
	 * �ж�ָ���Ķ����Ƿ���ڡ�
	 * 
	 * @param xpath
	 *            Ԫ�ص�xpath��λ�ַ���
	 */
	public boolean elementExistsByXpath(String xpath) {
		boolean exist = true;
		try {
			exist = this.getElementByXpath(xpath, "Ԫ���Ƿ����") != null ? true
					: false;
		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}

	/**
	 * Ԫ���Ƿ�ɼ�
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
	 * ָ��yaml�ļ��е�����ΪkeyԪ�أ���ȡ��Ԫ��propertyNam������ֵ
	 * 
	 * @param key
	 *            ��ȡYaml��Ԫ�ص�����
	 * @param propertyName
	 *            ��ȡ���Զ���
	 * @return
	 */
	public String getElementCSSValue(String key, String propertyName) {
		String value = null;
		WebElement element = this.getElement(key);
		value = element.getCssValue(propertyName);
		return value;
	}

	/**
	 * ��ָ��ʱ����ѭ���ȴ���ֱ������ɼ�����ʱ֮��ֱ���׳����󲻿ɼ��쳣��Ϣ��
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
			String error = "��ǰԪ����" + timeout + "���ﲻ��ʶ��";
			this.log.error(error);
			this.screenShot(error);
		}
		return isDisplayed;

	}

	/**
	 * ��ָ��ʱ����ѭ���ȴ���ֱ������ɼ���ʹ���û�ָ����Ĭ�ϳ�ʱ���á�
	 * 
	 * @param element
	 *            the WebElement to be judged
	 */
	protected boolean waitUntilElementVisible(WebElement element) {
		return waitUntilElementVisible(element, maxWaitfor);
	}

	/**
	 * ��Ԫ��ֻ������ȡ��
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
