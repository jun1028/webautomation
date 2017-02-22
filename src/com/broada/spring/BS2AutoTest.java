package com.broada.spring;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.broada.spring.condition.ExceptFrame;
import com.broada.spring.data.ElementData;
import com.broada.spring.data.Messages;

public class BS2AutoTest extends MouceAction {

	/**
	 * 该构造方法是用以默认选择IE为启动
	 */
	public BS2AutoTest() {
		super();
	}

	/**
	 * 设置浏览器类型 {BrowserType {IE, FIREFOX, CHROME,HTTPUNIT }} <br>
	 * <br>
	 * Using eg: BrowserType.IE,BrowserType.FIREFOX
	 */
	public BS2AutoTest(BrowserType bt, Object... args) {
		super(bt, args);
	}

	/**
	 * 设置焦点在元素上
	 * 
	 * @param key
	 */
	public void focusOnWithXpath(String xpath) {
		WebElement element = this.getElementByXpath(xpath, "设置焦点");
		this.focusOn(element);
	}

	/**
	 * 设置焦点在元素上
	 * 
	 * @param key
	 */
	public void focusOn(String key) {
		WebElement element = this.getElement(key);
		this.focusOn(element);
	}

	public void focusOn(String key, String parameter) {
		WebElement element = this.getElement(key, parameter);
		this.focusOn(element);
	}

	public void focusOn(WebElement element) {
		String message = "元素设置焦点失败";
		try {
			if (element == null) {
				this.log.error(message);
				throwException(message);
				return;
			}
			this.highLight(element);
			this.executeJavaScript("arguments[0].focus()", element);
		} catch (StaleElementReferenceException sere) {
			this.log.error(message);
			throwException(message);
			return;
		}
	}

	/**
	 * 通过javaScript获取输入框或者文本框，然后向输入框输入给定的内容
	 * 
	 * @param jscode
	 *            定位输入框或者文本框的javascript脚本
	 * @param value
	 *            需要输入的数据
	 */
	public void inputByJavaScript(String jscode, String value) {
		WebElement jelement = this.getElementByJavaScript(jscode);
		if (jelement == null) {
			this.log.error("获取元素失败，无法输入");
			return;
		}
		this.highLight(jelement);
		jelement.sendKeys(value);
	}

	/**
	 * 在等到指定对象可见之后在该对象上做清理操作，一般用于输入框和选择框。
	 * 
	 * @param key
	 *            在yaml文件中的元素
	 */
	public void clear(String key) {
		WebElement element = this.getElement(key);
		this.clear(element);
	}

	public void clear(WebElement element) {
		if (element == null) {
			String message = "清除输入内容或者取消选择失败";
			this.log.error(message);
			throwException(message);
			return;
		}
		try {
			waitUntilElementVisible(element);
			element.clear();
		} catch (ElementNotVisibleException e) {
			String message = "元素在当前页面上不可见";
			this.log.error(message);
			throwException(message);
		} catch (Exception e) {
			String message = "clear 操作失败";
			this.log.error(message);
			throwException(message);
		}
	}

	public void clear(String key, String parameter) {
		WebElement element = this.getElement(key, parameter);
		this.clear(element);
	}

	/**
	 * 在等到指定对象可见之后在该对象上做清理操作，一般用于输入框和选择框。
	 * 
	 * @param xpath
	 *            需要定位元素的xpath
	 */
	public void clearByXpath(String xpath) {
		WebElement element = this.getElementByXpath(xpath, "清空或者取消选择");
		this.clear(element);
	}

	/**
	 * 向定位到key的输入框中直接输入数据
	 * 
	 * @param key
	 *            在yaml文件中的元素
	 * @param value
	 *            输入的数据
	 */
	public void input(String key, String value) {
		WebElement element = getElement(key);
		if (element == null) {
			String message = "获取元素失败，无法输入";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		element.sendKeys(value);
		this.log
				.info("[" + this.getElementDes(key) + "]" + Messages.getString("input") //$NON-NLS-1$
						+ "\"" + value + "\"");
	}

	public void input(String key, String parameter, String value) {
		WebElement element = getElement(key, parameter);
		if (element == null) {
			String message = "获取元素失败，无法输入";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		element.sendKeys(value);
		this.log
				.info("[" + this.getElementDes(key) + "]" + Messages.getString("input") //$NON-NLS-1$
						+ "\"" + value + "\"");
	}

	/**
	 * 选择frame或者Iframe
	 * 
	 * @param key
	 *            在yaml文件中存储的frame
	 */
	public WebDriver selectFrame(String key) {
		Object ov = null;
		WebDriver innerFrame = null;
		try {
			for (ElementData trace : this.traceList) {
				ov = trace.getFrame(key);
				if (ov != null) {
					break;
				}
			}

		} catch (NumberFormatException e) {
			String message = "yaml文件中 Fram " + key + "提供参数有误";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
			// this.close();
		}
		if (ov == null) {
			String message = Messages.getString("AutoTest.swFail");
			this.log.error(message); //$NON-NLS-1$
			this.screenShot(message);
			throwException(message);
		}
		WebDriverWait wait = new WebDriverWait(this.browser, TIME_OUT);
		if (ov instanceof String) {
			try {
				ExceptFrame ef = new ExceptFrame(ov);
				innerFrame = wait.until(ef);
			} catch (TimeoutException nfe) {
				String title = "获取Frame " + key + "失败";
				this.screenShot(title);
				throwException(title);
			}
		} else {
			try {
				ExceptFrame ef = new ExceptFrame(ov);
				innerFrame = wait.until(ef);
			} catch (TimeoutException nfe) {
				String title = "获取Frame " + key + "失败";
				this.screenShot(title);
				throwException(title);
			}
		}
		return innerFrame;
	}

	/**
	 * 将当前执行的driver，选择到包含指定元素的frame上。
	 * 
	 * @param driver
	 * @param element
	 */
	public WebDriver selectFrame(WebDriver driver, WebElement element) {
		WebDriver innerFrame = null;
		if (driver == null || element == null) {
			this.log.error("获取元素失败，无法操作");
			throwException("切换frame或者Iframe失败");
			return null;
		}
		try {
			innerFrame = driver.switchTo().frame(element);
		} catch (NoSuchFrameException e) {
			String message = "需要切换的frame或者Iframe不存在";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
		} catch (StaleElementReferenceException e) {
			String message = "目标frame中不能依赖元素定位";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
		}
		return innerFrame;
	}

	/**
	 * 选择包含元素的frame
	 * 
	 * @param element
	 *            frame中的元素
	 * @return
	 */
	public WebDriver selectFrame(WebElement element) {
		return this.selectFrame(this.browser, element);
	}

	/**
	 * 选择包含元素的frame。
	 * 
	 * @param elementKey
	 *            需要定位元素的key yaml文件中的key
	 */
	public void selectFrameByElement(String elementKey) {
		WebElement element = this.getElement(elementKey);
		this.selectFrame(this.browser, element);
	}

	/**
	 * 切换至frame
	 * 
	 * @param par
	 */
	public WebDriver selectFrame(Object par) {
		WebDriverWait wait = new WebDriverWait(this.browser, TIME_OUT);
		this.sleep(2);
		boolean isOk = true;
		WebDriver innerFrame = null;
		if (par instanceof Integer) {
			try {
				Integer it = (Integer) par;
				ExceptFrame ef = new ExceptFrame(par);
				innerFrame = wait.until(ef);
				it++;
				this.log.info("切换至第" + it + "frame");
			} catch (TimeoutException nfe) {
				isOk = false;
			}
		} else {
			try {
				ExceptFrame ef = new ExceptFrame(par);
				innerFrame = wait.until(ef);
				this.log.info("切换至 frame '" + par + "'");
			} catch (TimeoutException nfe) {
				isOk = false;
			}
		}
		if (!isOk) {
			String error = "切换至 frame'" + par + "'失败";
			this.log.error(error);
			this.screenShot(error);
			throwException(error);
		}
		return innerFrame;
	}

	/**
	 * 按照指定序号选择yaml文件中的key元素下拉列表中的选项。
	 * 
	 * @param key
	 *            yaml文件定位元素的key
	 * @param index
	 *            需要选择的选项的索引值
	 */
	public void selectItemByIndex(String key, int index) {
		WebElement element = this.getElement(key);
		if (element == null) {
			this.log.error("获取元素失败，无法操作");
			throwException("操作失败");
			return;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (Exception e) {
			throwException("操作失败");
		}
		Select select = null;
		this.highLight(element);
		try {
			select = new Select(element);
			select.selectByIndex(index);
		} catch (UnexpectedTagNameException utne) {
			this.log.error("通过" + key + "获取的元素" + this.getElementDes(key)
					+ ",不是HTML表单的下拉列表");
			throwException("下拉列表操作失败");
		} catch (NoSuchElementException nee) {
			this.log.error("控件" + this.getElementDes(key) + ",不能通过索引" + index
					+ "获取Options的值");
			throwException("下拉列表操作失败");
		}
	}

	/**
	 * 按照指定序号选择给定xpath的下拉列表中的选项。
	 * 
	 * @param xpath
	 *            String 定位下拉列表的xpath
	 * @param index
	 *            int 需要索引选型值的索引
	 */
	public void selectXpathItemByIndex(String xpath, int index) {
		WebElement element = this.getElementByXpath(xpath, "定位列表框");
		if (element == null) {
			this.log.error("获取元素失败，无法操作");
			return;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (Exception e) {
			String error = "当前元素不可见";
			log.error("当前元素不可见");
			this.screenShot(error);
			throwException("下拉列表操作失败");
		}
		Select select = null;
		this.highLight(element);
		try {
			select = new Select(element);
			select.selectByIndex(index);
		} catch (UnexpectedTagNameException utne) {
			String error = "当前元素不是HTML表单的下拉列表";
			this.log.error(error);
			this.screenShot(error);
			throwException("下拉列表操作失败");
		} catch (NoSuchElementException nee) {
			String error = "给定控件不能通过索引" + index + "获取Options的值";
			this.log.error(error);
			this.screenShot(error);
			throwException("下拉列表操作失败");
		}
	}

	/**
	 * 获取yaml文件中key的下拉列表框的文本内容为text的选项
	 * 
	 * @param key
	 *            yaml文件中的key
	 * @param text
	 *            需要选择的文本内容
	 */
	public void selectComboxText(String key, String text) {
		WebElement element = this.getElement(key);
		this.selectComboxText(element, text);
	}

	public void selectComboxText(WebElement element, String text) {
		if (element == null) {
			this.log.error("获取元素失败，无法操作");
			throwException("下拉列表操作失败");
			return;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (Exception e) {
			throwException("下拉列表操作失败");
		}
		Select select = null;
		this.highLight(element);
		String message = "下拉列表操作失败";
		try {
			select = new Select(element);
			select.selectByVisibleText(text);
		} catch (UnexpectedTagNameException utne) {

			this.log.error(message);
			throwException(message);
		} catch (NoSuchElementException nee) {
			this.log.error(message);
			throwException(message);
		}
	}

	public void selectComboxText(String key, String parameter, String text) {
		WebElement element = this.getElement(key, parameter);
		this.selectComboxText(element, text);
	}

	/**
	 * 通过Xpath选取下拉列表框。选择其文本为text的选项
	 * 
	 * @param xpath
	 *            下拉列表的xpath
	 * @param text
	 *            文本选项
	 */
	public void selectXpathComboxText(String xpath, String text) {
		WebElement element = this.getElementByXpath(xpath, "定位列表框");
		if (element == null) {
			this.log.error("获取元素失败，无法操作");
			throwException("下拉列表操作失败");
			return;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (Exception e) {
			String error = "当前元素不可见";
			log.error("当前元素不可见");
			this.screenShot(error);
			throwException("下拉列表操作失败");
		}
		Select select = null;
		this.highLight(element);
		try {
			select = new Select(element);
			select.selectByVisibleText(text);
		} catch (UnexpectedTagNameException utne) {
			String error = "当前元素不是HTML表单的下拉列表";
			this.log.error(error);
			this.screenShot(error);
			throwException("下拉列表操作失败");
		} catch (NoSuchElementException nee) {
			String error = "给定控件不能通过文本内容" + text + "获取选项值";
			this.log.error(error);
			this.screenShot(error);
			throwException("下拉列表操作失败");
		}
	}

	/**
	 * 按照指定选项的实际值（不是可见文本值，而是对象的“value”属性的值）选择下拉列表中的选项。
	 * 
	 * @param key
	 *            yaml文件定位元素的key
	 * 
	 * @param itemValue
	 */
	public void selectItemByValue(String key, String itemValue) {
		WebElement element = this.getElement(key);
		if (element == null) {
			this.log.error("获取元素失败，无法操作");
			throwException("下拉列表操作失败");
			return;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (Exception e) {
			String error = "当前元素不可见";
			log.error("当前元素不可见");
			this.screenShot(error);
			throwException("下拉列表操作失败");
		}
		Select select = null;
		this.highLight(element);
		try {
			select = new Select(element);
			select.selectByValue(itemValue);
		} catch (UnexpectedTagNameException utne) {
			this.log.error("通过" + key + "获取的元素" + this.getElementDes(key)
					+ ",不是HTML表单的下落列表");
			String error = "当前元素不是HTML表单的下拉列表";
			this.screenShot(error);
			throwException("下拉列表操作失败");
		} catch (NoSuchElementException nee) {
			this.log.error("控件" + this.getElementDes(key) + ",不能通过选项值"
					+ itemValue + "选择该控件");
			String error = "给定控件不能通过" + itemValue + "获取Options的值";
			this.screenShot(error);
			throwException("下拉列表操作失败");
		}
	}

	/**
	 * 按照给定的xpath定位到下拉列表，并通过指定选项的实际值（不是可见文本值，而是对象的“value”属性的值）选择下拉列表中的选项。
	 * 
	 * @param xpath
	 *            自定义定位元素的xpath
	 * @param itemValue
	 *            选项值
	 */
	public void selectComboxItemValue(String xpath, String itemValue) {
		WebElement element = this.getElementByXpath(xpath, "下拉列表控件");
		if (element == null) {
			this.log.error("获取元素失败，无法操作");
			throwException("下拉列表操作失败");
			return;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (Exception e) {
			String error = "当前元素不可见";
			log.error("当前元素不可见");
			this.screenShot(error);
			throwException("下拉列表操作失败");
		}
		Select select = null;
		this.highLight(element);
		try {
			select = new Select(element);
			select.selectByValue(itemValue);
		} catch (UnexpectedTagNameException utne) {
			this.log.error("通过" + xpath + "定位的元素不是HTML表单的下落列表");
			String error = "当前元素不是HTML表单的下拉列表";
			this.screenShot(error);
			throwException("下拉列表操作失败");
		} catch (NoSuchElementException nee) {
			this.log.error("下拉列表控件,不能通过选型值" + itemValue + "选择该控件");
			String error = "给定控件不能通过" + itemValue + "获取Options的值";
			this.screenShot(error);
			throwException("下拉列表操作失败");
		}
	}

	/**
	 * 模拟键盘键入相关数据
	 * 
	 * keysToSend,可以是单一，也可以是组合键 eg: 单一参数:xx.sendKeys(K.ENTER); //xx为AutoTest 对象
	 * 多个参数:xx.sendkeys(K.ALT,K.ENTER);//xx为AutoTest 对象
	 * 
	 * @param keysToSend
	 */
	public void sendKeys(CharSequence... keysToSend) {
		try {
			this.getActions().sendKeys(keysToSend).perform();
			R: for (CharSequence cs : keysToSend) {
				for (K k : K.values()) {
					if (cs.equals(k)) {
						this.log.info("模拟键盘键入" + k.name());
						break R;
					}
				}
			}
		} catch (Exception e) {
			throwException("键盘操作失败");
		}
	}

	/**
	 * 
	 * @param key
	 *            yaml 文件中获取元素的key
	 * @param attributeName
	 *            所要获取的属性.
	 * @return
	 */
	public String getAttribute(String key, String attributeName) {
		WebElement element = this.getElement(key);
		if (element == null) {
			throwException("元素获取失败，无法获取数据");
			return null;
		}
		String value = element.getAttribute(attributeName);
		return value;
	}

	/**
	 * 获取元素属性
	 * 
	 * @param js
	 *            指定元素的js
	 * @param attributeName
	 *            获取元素的属性
	 * @return
	 */
	public String getAttributeByJS(String js, String attributeName) {
		String value = null;
		WebElement element = this.getElementByJavaScript(js);
		if (element == null) {
			throwException("元素获取失败,无法获取数据");
			return null;
		}
		value = element.getAttribute(attributeName);
		return value;
	}

	/**
	 * 获取xpath的元素的相关attribute属性值
	 * 
	 * @param xpath
	 * @param attributeName
	 * @return
	 */
	public String getAttributeByXpath(String xpath, String attributeName) {
		String value = null;
		WebElement element = this.getElementByXpath(xpath, "通过xpath获取元素");
		if (element == null) {
			throwException("元素获取失败");
			return null;
		}
		value = element.getAttribute(attributeName);
		return value;
	}

	/**
	 * 获取指定元素的文本内容
	 * 
	 * @param element
	 *            指定元素
	 * @return 文本内容
	 */
	public String getText(WebElement element) {
		if (element == null) {
			throwException("获取内容失败");
			return null;
		}
		return element.getText();
	}

	public boolean addCookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		WebDriver dr = this.getBrowser();
		dr.manage().addCookie(cookie);
		// Set<Cookie> cookies = dr.manage().getCookies();
		//
		return true;
		// System.out.println(String.format("Domain -> name ->
	}
}
