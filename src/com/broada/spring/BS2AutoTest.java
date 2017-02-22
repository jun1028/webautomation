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
	 * �ù��췽��������Ĭ��ѡ��IEΪ����
	 */
	public BS2AutoTest() {
		super();
	}

	/**
	 * ������������� {BrowserType {IE, FIREFOX, CHROME,HTTPUNIT }} <br>
	 * <br>
	 * Using eg: BrowserType.IE,BrowserType.FIREFOX
	 */
	public BS2AutoTest(BrowserType bt, Object... args) {
		super(bt, args);
	}

	/**
	 * ���ý�����Ԫ����
	 * 
	 * @param key
	 */
	public void focusOnWithXpath(String xpath) {
		WebElement element = this.getElementByXpath(xpath, "���ý���");
		this.focusOn(element);
	}

	/**
	 * ���ý�����Ԫ����
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
		String message = "Ԫ�����ý���ʧ��";
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
	 * ͨ��javaScript��ȡ���������ı���Ȼ����������������������
	 * 
	 * @param jscode
	 *            ��λ���������ı����javascript�ű�
	 * @param value
	 *            ��Ҫ���������
	 */
	public void inputByJavaScript(String jscode, String value) {
		WebElement jelement = this.getElementByJavaScript(jscode);
		if (jelement == null) {
			this.log.error("��ȡԪ��ʧ�ܣ��޷�����");
			return;
		}
		this.highLight(jelement);
		jelement.sendKeys(value);
	}

	/**
	 * �ڵȵ�ָ������ɼ�֮���ڸö����������������һ������������ѡ���
	 * 
	 * @param key
	 *            ��yaml�ļ��е�Ԫ��
	 */
	public void clear(String key) {
		WebElement element = this.getElement(key);
		this.clear(element);
	}

	public void clear(WebElement element) {
		if (element == null) {
			String message = "����������ݻ���ȡ��ѡ��ʧ��";
			this.log.error(message);
			throwException(message);
			return;
		}
		try {
			waitUntilElementVisible(element);
			element.clear();
		} catch (ElementNotVisibleException e) {
			String message = "Ԫ���ڵ�ǰҳ���ϲ��ɼ�";
			this.log.error(message);
			throwException(message);
		} catch (Exception e) {
			String message = "clear ����ʧ��";
			this.log.error(message);
			throwException(message);
		}
	}

	public void clear(String key, String parameter) {
		WebElement element = this.getElement(key, parameter);
		this.clear(element);
	}

	/**
	 * �ڵȵ�ָ������ɼ�֮���ڸö����������������һ������������ѡ���
	 * 
	 * @param xpath
	 *            ��Ҫ��λԪ�ص�xpath
	 */
	public void clearByXpath(String xpath) {
		WebElement element = this.getElementByXpath(xpath, "��ջ���ȡ��ѡ��");
		this.clear(element);
	}

	/**
	 * ��λ��key���������ֱ����������
	 * 
	 * @param key
	 *            ��yaml�ļ��е�Ԫ��
	 * @param value
	 *            ���������
	 */
	public void input(String key, String value) {
		WebElement element = getElement(key);
		if (element == null) {
			String message = "��ȡԪ��ʧ�ܣ��޷�����";
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
			String message = "��ȡԪ��ʧ�ܣ��޷�����";
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
	 * ѡ��frame����Iframe
	 * 
	 * @param key
	 *            ��yaml�ļ��д洢��frame
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
			String message = "yaml�ļ��� Fram " + key + "�ṩ��������";
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
				String title = "��ȡFrame " + key + "ʧ��";
				this.screenShot(title);
				throwException(title);
			}
		} else {
			try {
				ExceptFrame ef = new ExceptFrame(ov);
				innerFrame = wait.until(ef);
			} catch (TimeoutException nfe) {
				String title = "��ȡFrame " + key + "ʧ��";
				this.screenShot(title);
				throwException(title);
			}
		}
		return innerFrame;
	}

	/**
	 * ����ǰִ�е�driver��ѡ�񵽰���ָ��Ԫ�ص�frame�ϡ�
	 * 
	 * @param driver
	 * @param element
	 */
	public WebDriver selectFrame(WebDriver driver, WebElement element) {
		WebDriver innerFrame = null;
		if (driver == null || element == null) {
			this.log.error("��ȡԪ��ʧ�ܣ��޷�����");
			throwException("�л�frame����Iframeʧ��");
			return null;
		}
		try {
			innerFrame = driver.switchTo().frame(element);
		} catch (NoSuchFrameException e) {
			String message = "��Ҫ�л���frame����Iframe������";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
		} catch (StaleElementReferenceException e) {
			String message = "Ŀ��frame�в�������Ԫ�ض�λ";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
		}
		return innerFrame;
	}

	/**
	 * ѡ�����Ԫ�ص�frame
	 * 
	 * @param element
	 *            frame�е�Ԫ��
	 * @return
	 */
	public WebDriver selectFrame(WebElement element) {
		return this.selectFrame(this.browser, element);
	}

	/**
	 * ѡ�����Ԫ�ص�frame��
	 * 
	 * @param elementKey
	 *            ��Ҫ��λԪ�ص�key yaml�ļ��е�key
	 */
	public void selectFrameByElement(String elementKey) {
		WebElement element = this.getElement(elementKey);
		this.selectFrame(this.browser, element);
	}

	/**
	 * �л���frame
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
				this.log.info("�л�����" + it + "frame");
			} catch (TimeoutException nfe) {
				isOk = false;
			}
		} else {
			try {
				ExceptFrame ef = new ExceptFrame(par);
				innerFrame = wait.until(ef);
				this.log.info("�л��� frame '" + par + "'");
			} catch (TimeoutException nfe) {
				isOk = false;
			}
		}
		if (!isOk) {
			String error = "�л��� frame'" + par + "'ʧ��";
			this.log.error(error);
			this.screenShot(error);
			throwException(error);
		}
		return innerFrame;
	}

	/**
	 * ����ָ�����ѡ��yaml�ļ��е�keyԪ�������б��е�ѡ�
	 * 
	 * @param key
	 *            yaml�ļ���λԪ�ص�key
	 * @param index
	 *            ��Ҫѡ���ѡ�������ֵ
	 */
	public void selectItemByIndex(String key, int index) {
		WebElement element = this.getElement(key);
		if (element == null) {
			this.log.error("��ȡԪ��ʧ�ܣ��޷�����");
			throwException("����ʧ��");
			return;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (Exception e) {
			throwException("����ʧ��");
		}
		Select select = null;
		this.highLight(element);
		try {
			select = new Select(element);
			select.selectByIndex(index);
		} catch (UnexpectedTagNameException utne) {
			this.log.error("ͨ��" + key + "��ȡ��Ԫ��" + this.getElementDes(key)
					+ ",����HTML���������б�");
			throwException("�����б����ʧ��");
		} catch (NoSuchElementException nee) {
			this.log.error("�ؼ�" + this.getElementDes(key) + ",����ͨ������" + index
					+ "��ȡOptions��ֵ");
			throwException("�����б����ʧ��");
		}
	}

	/**
	 * ����ָ�����ѡ�����xpath�������б��е�ѡ�
	 * 
	 * @param xpath
	 *            String ��λ�����б��xpath
	 * @param index
	 *            int ��Ҫ����ѡ��ֵ������
	 */
	public void selectXpathItemByIndex(String xpath, int index) {
		WebElement element = this.getElementByXpath(xpath, "��λ�б��");
		if (element == null) {
			this.log.error("��ȡԪ��ʧ�ܣ��޷�����");
			return;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (Exception e) {
			String error = "��ǰԪ�ز��ɼ�";
			log.error("��ǰԪ�ز��ɼ�");
			this.screenShot(error);
			throwException("�����б����ʧ��");
		}
		Select select = null;
		this.highLight(element);
		try {
			select = new Select(element);
			select.selectByIndex(index);
		} catch (UnexpectedTagNameException utne) {
			String error = "��ǰԪ�ز���HTML���������б�";
			this.log.error(error);
			this.screenShot(error);
			throwException("�����б����ʧ��");
		} catch (NoSuchElementException nee) {
			String error = "�����ؼ�����ͨ������" + index + "��ȡOptions��ֵ";
			this.log.error(error);
			this.screenShot(error);
			throwException("�����б����ʧ��");
		}
	}

	/**
	 * ��ȡyaml�ļ���key�������б����ı�����Ϊtext��ѡ��
	 * 
	 * @param key
	 *            yaml�ļ��е�key
	 * @param text
	 *            ��Ҫѡ����ı�����
	 */
	public void selectComboxText(String key, String text) {
		WebElement element = this.getElement(key);
		this.selectComboxText(element, text);
	}

	public void selectComboxText(WebElement element, String text) {
		if (element == null) {
			this.log.error("��ȡԪ��ʧ�ܣ��޷�����");
			throwException("�����б����ʧ��");
			return;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (Exception e) {
			throwException("�����б����ʧ��");
		}
		Select select = null;
		this.highLight(element);
		String message = "�����б����ʧ��";
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
	 * ͨ��Xpathѡȡ�����б��ѡ�����ı�Ϊtext��ѡ��
	 * 
	 * @param xpath
	 *            �����б��xpath
	 * @param text
	 *            �ı�ѡ��
	 */
	public void selectXpathComboxText(String xpath, String text) {
		WebElement element = this.getElementByXpath(xpath, "��λ�б��");
		if (element == null) {
			this.log.error("��ȡԪ��ʧ�ܣ��޷�����");
			throwException("�����б����ʧ��");
			return;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (Exception e) {
			String error = "��ǰԪ�ز��ɼ�";
			log.error("��ǰԪ�ز��ɼ�");
			this.screenShot(error);
			throwException("�����б����ʧ��");
		}
		Select select = null;
		this.highLight(element);
		try {
			select = new Select(element);
			select.selectByVisibleText(text);
		} catch (UnexpectedTagNameException utne) {
			String error = "��ǰԪ�ز���HTML���������б�";
			this.log.error(error);
			this.screenShot(error);
			throwException("�����б����ʧ��");
		} catch (NoSuchElementException nee) {
			String error = "�����ؼ�����ͨ���ı�����" + text + "��ȡѡ��ֵ";
			this.log.error(error);
			this.screenShot(error);
			throwException("�����б����ʧ��");
		}
	}

	/**
	 * ����ָ��ѡ���ʵ��ֵ�����ǿɼ��ı�ֵ�����Ƕ���ġ�value�����Ե�ֵ��ѡ�������б��е�ѡ�
	 * 
	 * @param key
	 *            yaml�ļ���λԪ�ص�key
	 * 
	 * @param itemValue
	 */
	public void selectItemByValue(String key, String itemValue) {
		WebElement element = this.getElement(key);
		if (element == null) {
			this.log.error("��ȡԪ��ʧ�ܣ��޷�����");
			throwException("�����б����ʧ��");
			return;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (Exception e) {
			String error = "��ǰԪ�ز��ɼ�";
			log.error("��ǰԪ�ز��ɼ�");
			this.screenShot(error);
			throwException("�����б����ʧ��");
		}
		Select select = null;
		this.highLight(element);
		try {
			select = new Select(element);
			select.selectByValue(itemValue);
		} catch (UnexpectedTagNameException utne) {
			this.log.error("ͨ��" + key + "��ȡ��Ԫ��" + this.getElementDes(key)
					+ ",����HTML���������б�");
			String error = "��ǰԪ�ز���HTML���������б�";
			this.screenShot(error);
			throwException("�����б����ʧ��");
		} catch (NoSuchElementException nee) {
			this.log.error("�ؼ�" + this.getElementDes(key) + ",����ͨ��ѡ��ֵ"
					+ itemValue + "ѡ��ÿؼ�");
			String error = "�����ؼ�����ͨ��" + itemValue + "��ȡOptions��ֵ";
			this.screenShot(error);
			throwException("�����б����ʧ��");
		}
	}

	/**
	 * ���ո�����xpath��λ�������б���ͨ��ָ��ѡ���ʵ��ֵ�����ǿɼ��ı�ֵ�����Ƕ���ġ�value�����Ե�ֵ��ѡ�������б��е�ѡ�
	 * 
	 * @param xpath
	 *            �Զ��嶨λԪ�ص�xpath
	 * @param itemValue
	 *            ѡ��ֵ
	 */
	public void selectComboxItemValue(String xpath, String itemValue) {
		WebElement element = this.getElementByXpath(xpath, "�����б�ؼ�");
		if (element == null) {
			this.log.error("��ȡԪ��ʧ�ܣ��޷�����");
			throwException("�����б����ʧ��");
			return;
		}
		try {
			this.waitUntilElementVisible(element);
		} catch (Exception e) {
			String error = "��ǰԪ�ز��ɼ�";
			log.error("��ǰԪ�ز��ɼ�");
			this.screenShot(error);
			throwException("�����б����ʧ��");
		}
		Select select = null;
		this.highLight(element);
		try {
			select = new Select(element);
			select.selectByValue(itemValue);
		} catch (UnexpectedTagNameException utne) {
			this.log.error("ͨ��" + xpath + "��λ��Ԫ�ز���HTML���������б�");
			String error = "��ǰԪ�ز���HTML���������б�";
			this.screenShot(error);
			throwException("�����б����ʧ��");
		} catch (NoSuchElementException nee) {
			this.log.error("�����б�ؼ�,����ͨ��ѡ��ֵ" + itemValue + "ѡ��ÿؼ�");
			String error = "�����ؼ�����ͨ��" + itemValue + "��ȡOptions��ֵ";
			this.screenShot(error);
			throwException("�����б����ʧ��");
		}
	}

	/**
	 * ģ����̼����������
	 * 
	 * keysToSend,�����ǵ�һ��Ҳ��������ϼ� eg: ��һ����:xx.sendKeys(K.ENTER); //xxΪAutoTest ����
	 * �������:xx.sendkeys(K.ALT,K.ENTER);//xxΪAutoTest ����
	 * 
	 * @param keysToSend
	 */
	public void sendKeys(CharSequence... keysToSend) {
		try {
			this.getActions().sendKeys(keysToSend).perform();
			R: for (CharSequence cs : keysToSend) {
				for (K k : K.values()) {
					if (cs.equals(k)) {
						this.log.info("ģ����̼���" + k.name());
						break R;
					}
				}
			}
		} catch (Exception e) {
			throwException("���̲���ʧ��");
		}
	}

	/**
	 * 
	 * @param key
	 *            yaml �ļ��л�ȡԪ�ص�key
	 * @param attributeName
	 *            ��Ҫ��ȡ������.
	 * @return
	 */
	public String getAttribute(String key, String attributeName) {
		WebElement element = this.getElement(key);
		if (element == null) {
			throwException("Ԫ�ػ�ȡʧ�ܣ��޷���ȡ����");
			return null;
		}
		String value = element.getAttribute(attributeName);
		return value;
	}

	/**
	 * ��ȡԪ������
	 * 
	 * @param js
	 *            ָ��Ԫ�ص�js
	 * @param attributeName
	 *            ��ȡԪ�ص�����
	 * @return
	 */
	public String getAttributeByJS(String js, String attributeName) {
		String value = null;
		WebElement element = this.getElementByJavaScript(js);
		if (element == null) {
			throwException("Ԫ�ػ�ȡʧ��,�޷���ȡ����");
			return null;
		}
		value = element.getAttribute(attributeName);
		return value;
	}

	/**
	 * ��ȡxpath��Ԫ�ص����attribute����ֵ
	 * 
	 * @param xpath
	 * @param attributeName
	 * @return
	 */
	public String getAttributeByXpath(String xpath, String attributeName) {
		String value = null;
		WebElement element = this.getElementByXpath(xpath, "ͨ��xpath��ȡԪ��");
		if (element == null) {
			throwException("Ԫ�ػ�ȡʧ��");
			return null;
		}
		value = element.getAttribute(attributeName);
		return value;
	}

	/**
	 * ��ȡָ��Ԫ�ص��ı�����
	 * 
	 * @param element
	 *            ָ��Ԫ��
	 * @return �ı�����
	 */
	public String getText(WebElement element) {
		if (element == null) {
			throwException("��ȡ����ʧ��");
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
