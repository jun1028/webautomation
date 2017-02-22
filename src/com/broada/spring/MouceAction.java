package com.broada.spring;

import java.util.HashMap;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.broada.spring.data.ElementData;
import com.broada.spring.data.Messages;

public class MouceAction extends Element {
	public MouceAction() {
		super();
	}

	public MouceAction(BrowserType bt, Object... args) {
		super(bt, args);
	}

	/**
	 * 单击元素
	 * 
	 * @param element
	 */
	public void click(WebElement element) {
		this.highLight(element);
		element.click();
	}

	/**
	 * 获取当前元素的描述信息
	 * 
	 * @param key
	 *            在yaml文件中的元素
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected String getElementDes(String key) {
		HashMap currentElement = null;
		String des = null;
		for (ElementData trace : this.traceList) {
			HashMap hm = trace.getDataHash();
			currentElement = (HashMap) hm.get(key);
			if (currentElement != null) {
				break;
			}
		}
		if (currentElement != null) {
			des = (String) currentElement.get("des"); //$NON-NLS-1$
		}
		return des;
	}

	/**
	 * 点击定位到Key的元素
	 * 
	 * @param key
	 *            在yaml文件中的元素
	 */
	public void click(String key) {
		try {
			WebElement element = this.getElement(key);
			if (element == null) {
				String message = "点击元素失败";
				this.log.error(message);
				throwException(message);
				return;
			}
			this.highLight(element);
			element.click();
		} catch (StaleElementReferenceException sere) {
			String error = "点击元素" + key + "失败";
			this.log.error(error);
			throwException(error);
			return;
		}
		this.log
				.info(Messages.getString("AutoTest.click") + "[" + this.getElementDes(key) + "]"); //$NON-NLS-1$
	}

	/**
	 * 点击操作，操作对象的定位中含有参数
	 * 
	 * @param key
	 * @param parameter
	 */
	public void click(String key, String parameter) {
		try {
			WebElement element = this.getElement(key, parameter);
			if (element == null) {
				String message = "点击元素失败";
				this.log.error(message);
				throwException(message);
				return;
			}
			this.highLight(element);
			element.click();
		} catch (StaleElementReferenceException sere) {
			String error = "点击元素" + key + "失败";
			this.log.error(error);
			throwException(error);
			return;
		}
		this.log
				.info(Messages.getString("AutoTest.click") + "[" + this.getElementDes(key) + "]"); //$NON-NLS-1$

	}

	/**
	 * 点击元素
	 * 
	 * @param xpath
	 *            通过xpath定位元素
	 * @param des
	 *            描述信息
	 */
	public void clickByXpath(String xpath, String des) {
		WebElement element = this.getElementByXpath(xpath, des);
		if (element == null) {
			String message = "点击元素失败";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		element.click();
		this.log.info(des + " ok");

	}

	/**
	 * 获取动作 将要对元素的单击，双击等
	 * 
	 * @see {org.openqa.selenium.interactions.Actions.Actions(WebDriver driver)}
	 * 
	 * @return
	 */
	protected Actions getActions() {
		return new Actions(this.browser);
	}

	/**
	 * 通过数据文件提供的数据，将动作元素sourcekey的元素拖拽到目标targetKey的位置 释放。
	 * 
	 * @param soureceKey
	 *            被拖动动作元素
	 * @param targetKey
	 *            目标元素
	 */
	public void dragAndDrop(String soureceKey, String targetKey) {
		WebElement source = this.getElement(soureceKey);
		WebElement target = this.getElement(targetKey);
		this.dragAndDrop(source, target);
	}

	/**
	 * 将元素source拖拽到target的元素里，并释放
	 * 
	 * @param source
	 * @param target
	 */
	public void dragAndDrop(WebElement source, WebElement target) {
		if (source == null || target == null) {
			String message = "拖拽失败";
			this.log.error(message);
			throwException(message);
			return;
		}
		Actions acts = this.getActions();
		acts.dragAndDrop(source, target).perform();
	}

	/**
	 * 双击定位到key的元素
	 * 
	 * @param key
	 *            在yaml文件中的元素
	 */
	public void doubleClick(String key) {
		WebElement element = this.getElement(key);
		this.doubleClick(element);
	}

	public void doubleClick(String key, String paramter) {
		WebElement element = this.getElement(key, paramter);
		this.doubleClick(element);
	}

	/**
	 * 双击元素
	 * 
	 * @param xpath
	 *            需要定位元素的xpath
	 * @param des
	 *            描述信息
	 */
	public void doubleClickByXpath(String xpath, String des) {
		WebElement element = this.getElementByXpath(xpath, des);
		this.doubleClick(element);
	}

	/**
	 * 双击元素
	 * 
	 * @param element
	 *            需要处理的元素
	 */
	public void doubleClick(WebElement element) {
		if (element == null) {
			String message = "双击失败";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		Actions act = this.getActions();
		this.log.info("执行鼠标双击");
		act.doubleClick(element).perform();
	}

	/**
	 * 点击元素，该元素是通过javascript获得
	 * 
	 * 本方法，主要是用来点击一些元素不在可见区域或者是一些特殊要求的元素 eg: 点击百度提交按钮 String jscode=
	 * "var inp = document.getElementById('su'); return inp;";
	 * 
	 * autoTest.clickByJavaScript(jscode);
	 * 
	 * @param jscode
	 */
	public void clickByJavaScript(String jscode) {
		WebElement jelement = this.getElementByJavaScript(jscode);
		this.highLight(jelement);
		jelement.click();
	}

	/**
	 * 右键点击元素
	 * 
	 * @param key
	 *            在yaml文件中的元素
	 */
	public void rightClick(String key) {
		WebElement we = this.getElement(key);
		this.rightClick(we);
	}

	public void rightClick(String key, String parameter) {
		WebElement we = this.getElement(key, parameter);
		this.rightClick(we);
	}

	/**
	 * 右键点击元素
	 * 
	 * @param key
	 *            在yaml文件中的元素
	 */
	public void rightClick(WebElement element) {
		Actions act = new Actions(this.browser);
		WebElement we = element;
		if (we == null) {
			String message = "右键点击失败";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(we);
		act.contextClick(we).perform();
	}

	/**
	 * 右键点击通过xpath定位的元素
	 * 
	 * @param xpath
	 *            定位元素的xpath
	 */
	public void rightClickByXpath(String xpath) {
		WebElement we = this.getElementByXpath(xpath, "右键点击");
		this.rightClick(we);
	}

	/**
	 * 鼠标移动到指定元素上
	 * 
	 * @param element
	 *            鼠标需要悬停的元素
	 */
	public void moveOn(WebElement element) {
		if (element == null) {
			String message = "鼠标悬停失败";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		Actions act = new Actions(this.browser);
		act.moveToElement(element).perform();
	}

	/**
	 * 鼠标移动到指定元素上
	 * 
	 * @param key
	 *            需要悬停的元素在yaml文件索引
	 */
	public void moveOn(String key) {
		WebElement element = this.getElement(key);
		this.moveOn(element);
	}

	public void moveOn(String key, String parameter) {
		WebElement element = this.getElement(key, parameter);
		this.moveOn(element);
	}

	/**
	 * 鼠标移动到指定元素上
	 * 
	 * @param xpath
	 *            定位元素的xpath
	 */
	public void moveOnByXpath(String xpath) {
		WebElement element = this.getElementByXpath(xpath, "鼠标悬停");
		this.moveOn(element);
	}

	/**
	 * 鼠标点击不释放
	 */
	public void clickAndHold() {
		Actions act = new Actions(this.browser);
		act.clickAndHold().perform();
	}

	/**
	 * 鼠标点击元素不释放
	 */
	public void clickAndHold(WebElement element) {
		if (element == null) {
			String message = "鼠标左键按下不释放失败";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		Actions act = new Actions(this.browser);
		act.clickAndHold(element).perform();
	}

	/**
	 * 鼠标点击元素不释放
	 */
	public void clickAndHold(String key) {
		WebElement element = this.getElement(key);
		this.clickAndHold(element);
	}

	/**
	 * 鼠标点击元素不释放
	 */
	public void clickAndHold(String key, String parameter) {
		WebElement element = this.getElement(key, parameter);
		this.clickAndHold(element);
	}

	/**
	 * 鼠标点击元素不释放
	 */
	public void clickAndHoldByXpath(String xpath) {
		WebElement element = this.getElementByXpath(xpath, "鼠标左键按下不释放");
		this.clickAndHold(element);
	}

	/**
	 * 鼠标释放
	 */
	public void release() {
		Actions act = new Actions(this.browser);
		act.release().perform();
	}

	/**
	 * 鼠标释放
	 */
	public void release(WebElement element) {
		if (element == null) {
			String message = "鼠标释放失败";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		Actions act = new Actions(this.browser);
		act.release(element).perform();
	}

	/**
	 * 鼠标释放
	 */
	public void release(String key) {
		WebElement element = this.getElement(key);
		this.release(element);
	}

	/**
	 * 鼠标释放
	 */
	public void release(String key, String parameter) {
		WebElement element = this.getElement(key, parameter);
		this.release(element);
	}

	/**
	 * 鼠标释放
	 */
	public void releaseByXpath(String xpath) {
		WebElement element = this.getElementByXpath(xpath, "鼠标释放");
		this.release(element);
	}
}
