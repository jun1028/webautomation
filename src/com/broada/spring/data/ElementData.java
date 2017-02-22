package com.broada.spring.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.broada.spring.condition.ExceptElement;
import com.broada.spring.core.DbReader;
import com.broada.spring.core.YamlReader;
import com.broada.spring.utils.Utils;

/**
 * 
 * @author chingsir
 * 
 */
public class ElementData extends Utils {
	@SuppressWarnings("rawtypes")
	private HashMap hm;
	private WebDriver browser;

	public ElementData(File f, WebDriver browser)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		this.browser = browser;
		YamlReader yr = new YamlReader(f);
		this.hm = yr.getDataMap();
	}

	public ElementData(String tableName, WebDriver browser) throws Exception {
		this.browser = browser;
		DbReader dr = new DbReader(tableName);
		this.hm = dr.getDataMap();
	}

	@SuppressWarnings("rawtypes")
	public HashMap getDataHash() {
		return this.hm;
	}

	public String getXpathValue(String key) {
		String xpathValue = null;
		HashMap op = (HashMap) hm.get(key);
		Object keyXpath = "xpath";
		if (op != null && op.containsKey(keyXpath)) {
			xpathValue = (String) op.get(keyXpath);
			xpathValue = xpathValue.trim();
		}
		return xpathValue;
	}

	public Map getKeyValue(String key) {
		return (HashMap) hm.get(key);
	}

	@SuppressWarnings("rawtypes")
	public WebElement getData(String key) throws TimeoutException {
		HashMap op = (HashMap) hm.get(key);
		WebElement element = null;
		String selecter = null;
		Object keyID = "id";
		Object keyXpath = "xpath";
		Object keyName = "name";
		Object keyCSS = "css";
		if (op == null) {
			return null;
		}
		WebDriverWait wait = new WebDriverWait(this.browser, TIME_OUT);
		By by = null;
		if (op.containsKey(keyID)) {
			selecter = (String) op.get(keyID);
			selecter = selecter.trim();
			by = By.id(selecter);
			ExceptElement ee = new ExceptElement(by);
			element = wait.until(ee);
		} else if (op.containsKey(keyXpath)) {
			selecter = (String) op.get(keyXpath);
			selecter = selecter.trim();
			by = By.xpath(selecter);
			ExceptElement ee = new ExceptElement(by);
			element = wait.until(ee);
		} else if (op.containsKey(keyName)) {
			selecter = (String) op.get(keyName);
			selecter = selecter.trim();
			by = By.name(selecter);
			ExceptElement ee = new ExceptElement(by);
			element = wait.until(ee);
		} else if (op.containsKey(keyCSS)) {
			selecter = (String) op.get(keyCSS);
			selecter = selecter.trim();
			by = By.cssSelector(selecter);
			ExceptElement ee = new ExceptElement(by);
			element = wait.until(ee);
		} else {
			return null;
		}
		return element;
	}

	/**
	 * 获取元素，元素定位中含有参数
	 * 
	 * @param key
	 * @param parameter
	 *            [%id%] [%name%] [%css%] [%xpath%]
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public WebElement getData(String key, String parameter) {
		HashMap op = (HashMap) hm.get(key);
		WebElement element = null;
		String selecter = null;
		Object keyID = "id";
		Object keyXpath = "xpath";
		Object keyName = "name";
		Object keyCSS = "css";
		if (op == null) {
			return null;
		}
		WebDriverWait wait = new WebDriverWait(this.browser, TIME_OUT);
		By by = null;
		if (op.containsKey(keyID)) {
			selecter = (String) op.get(keyID);
			String temp = selecter;

			temp = temp.replaceAll("%id%", parameter);
			System.out.println(temp);
			by = By.id(temp);
			ExceptElement ee = new ExceptElement(by);
			element = wait.until(ee);
		} else if (op.containsKey(keyXpath)) {
			selecter = (String) op.get(keyXpath);
			String temp = selecter.trim();
			temp = temp.replaceAll("%xpath%", parameter);
			by = By.xpath(temp);
			ExceptElement ee = new ExceptElement(by);
			element = wait.until(ee);
		} else if (op.containsKey(keyName)) {
			selecter = (String) op.get(keyName);
			String temp = selecter.trim();
			temp = temp.replaceAll("%name%", parameter);
			by = By.name(temp);
			ExceptElement ee = new ExceptElement(by);
			element = wait.until(ee);
		} else if (op.containsKey(keyCSS)) {
			selecter = (String) op.get(keyCSS);
			String temp = selecter.trim();
			temp = temp.replaceAll("%css%", parameter);
			by = By.cssSelector(temp);
			ExceptElement ee = new ExceptElement(by);
			element = wait.until(ee);
		} else {
			return null;
		}
		return element;
	}

	@SuppressWarnings("rawtypes")
	public Object getFrame(String framekey) throws NumberFormatException {
		HashMap hf = (HashMap) this.hm.get(framekey);
		Object keyID = "id";
		Object keyName = "Name";
		Object keyIndex = "index";
		if (hf == null) {
			return null;
		}
		if (hf.containsKey(keyID)) {
			return (String) hf.get(keyID);
		} else if (hf.containsKey(keyName)) {
			return (String) hf.get(keyName);

		} else if (hf.containsKey(keyIndex)) {
			String value = (String) hf.get(keyID);
			Integer iv = -1;
			iv = Integer.parseInt(value);
			return iv;
		}
		return null;
	}

}
