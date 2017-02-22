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
	 * ����Ԫ��
	 * 
	 * @param element
	 */
	public void click(WebElement element) {
		this.highLight(element);
		element.click();
	}

	/**
	 * ��ȡ��ǰԪ�ص�������Ϣ
	 * 
	 * @param key
	 *            ��yaml�ļ��е�Ԫ��
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
	 * �����λ��Key��Ԫ��
	 * 
	 * @param key
	 *            ��yaml�ļ��е�Ԫ��
	 */
	public void click(String key) {
		try {
			WebElement element = this.getElement(key);
			if (element == null) {
				String message = "���Ԫ��ʧ��";
				this.log.error(message);
				throwException(message);
				return;
			}
			this.highLight(element);
			element.click();
		} catch (StaleElementReferenceException sere) {
			String error = "���Ԫ��" + key + "ʧ��";
			this.log.error(error);
			throwException(error);
			return;
		}
		this.log
				.info(Messages.getString("AutoTest.click") + "[" + this.getElementDes(key) + "]"); //$NON-NLS-1$
	}

	/**
	 * �����������������Ķ�λ�к��в���
	 * 
	 * @param key
	 * @param parameter
	 */
	public void click(String key, String parameter) {
		try {
			WebElement element = this.getElement(key, parameter);
			if (element == null) {
				String message = "���Ԫ��ʧ��";
				this.log.error(message);
				throwException(message);
				return;
			}
			this.highLight(element);
			element.click();
		} catch (StaleElementReferenceException sere) {
			String error = "���Ԫ��" + key + "ʧ��";
			this.log.error(error);
			throwException(error);
			return;
		}
		this.log
				.info(Messages.getString("AutoTest.click") + "[" + this.getElementDes(key) + "]"); //$NON-NLS-1$

	}

	/**
	 * ���Ԫ��
	 * 
	 * @param xpath
	 *            ͨ��xpath��λԪ��
	 * @param des
	 *            ������Ϣ
	 */
	public void clickByXpath(String xpath, String des) {
		WebElement element = this.getElementByXpath(xpath, des);
		if (element == null) {
			String message = "���Ԫ��ʧ��";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		element.click();
		this.log.info(des + " ok");

	}

	/**
	 * ��ȡ���� ��Ҫ��Ԫ�صĵ�����˫����
	 * 
	 * @see {org.openqa.selenium.interactions.Actions.Actions(WebDriver driver)}
	 * 
	 * @return
	 */
	protected Actions getActions() {
		return new Actions(this.browser);
	}

	/**
	 * ͨ�������ļ��ṩ�����ݣ�������Ԫ��sourcekey��Ԫ����ק��Ŀ��targetKey��λ�� �ͷš�
	 * 
	 * @param soureceKey
	 *            ���϶�����Ԫ��
	 * @param targetKey
	 *            Ŀ��Ԫ��
	 */
	public void dragAndDrop(String soureceKey, String targetKey) {
		WebElement source = this.getElement(soureceKey);
		WebElement target = this.getElement(targetKey);
		this.dragAndDrop(source, target);
	}

	/**
	 * ��Ԫ��source��ק��target��Ԫ������ͷ�
	 * 
	 * @param source
	 * @param target
	 */
	public void dragAndDrop(WebElement source, WebElement target) {
		if (source == null || target == null) {
			String message = "��קʧ��";
			this.log.error(message);
			throwException(message);
			return;
		}
		Actions acts = this.getActions();
		acts.dragAndDrop(source, target).perform();
	}

	/**
	 * ˫����λ��key��Ԫ��
	 * 
	 * @param key
	 *            ��yaml�ļ��е�Ԫ��
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
	 * ˫��Ԫ��
	 * 
	 * @param xpath
	 *            ��Ҫ��λԪ�ص�xpath
	 * @param des
	 *            ������Ϣ
	 */
	public void doubleClickByXpath(String xpath, String des) {
		WebElement element = this.getElementByXpath(xpath, des);
		this.doubleClick(element);
	}

	/**
	 * ˫��Ԫ��
	 * 
	 * @param element
	 *            ��Ҫ�����Ԫ��
	 */
	public void doubleClick(WebElement element) {
		if (element == null) {
			String message = "˫��ʧ��";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		Actions act = this.getActions();
		this.log.info("ִ�����˫��");
		act.doubleClick(element).perform();
	}

	/**
	 * ���Ԫ�أ���Ԫ����ͨ��javascript���
	 * 
	 * ����������Ҫ���������һЩԪ�ز��ڿɼ����������һЩ����Ҫ���Ԫ�� eg: ����ٶ��ύ��ť String jscode=
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
	 * �Ҽ����Ԫ��
	 * 
	 * @param key
	 *            ��yaml�ļ��е�Ԫ��
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
	 * �Ҽ����Ԫ��
	 * 
	 * @param key
	 *            ��yaml�ļ��е�Ԫ��
	 */
	public void rightClick(WebElement element) {
		Actions act = new Actions(this.browser);
		WebElement we = element;
		if (we == null) {
			String message = "�Ҽ����ʧ��";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(we);
		act.contextClick(we).perform();
	}

	/**
	 * �Ҽ����ͨ��xpath��λ��Ԫ��
	 * 
	 * @param xpath
	 *            ��λԪ�ص�xpath
	 */
	public void rightClickByXpath(String xpath) {
		WebElement we = this.getElementByXpath(xpath, "�Ҽ����");
		this.rightClick(we);
	}

	/**
	 * ����ƶ���ָ��Ԫ����
	 * 
	 * @param element
	 *            �����Ҫ��ͣ��Ԫ��
	 */
	public void moveOn(WebElement element) {
		if (element == null) {
			String message = "�����ͣʧ��";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		Actions act = new Actions(this.browser);
		act.moveToElement(element).perform();
	}

	/**
	 * ����ƶ���ָ��Ԫ����
	 * 
	 * @param key
	 *            ��Ҫ��ͣ��Ԫ����yaml�ļ�����
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
	 * ����ƶ���ָ��Ԫ����
	 * 
	 * @param xpath
	 *            ��λԪ�ص�xpath
	 */
	public void moveOnByXpath(String xpath) {
		WebElement element = this.getElementByXpath(xpath, "�����ͣ");
		this.moveOn(element);
	}

	/**
	 * ��������ͷ�
	 */
	public void clickAndHold() {
		Actions act = new Actions(this.browser);
		act.clickAndHold().perform();
	}

	/**
	 * �����Ԫ�ز��ͷ�
	 */
	public void clickAndHold(WebElement element) {
		if (element == null) {
			String message = "���������²��ͷ�ʧ��";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		Actions act = new Actions(this.browser);
		act.clickAndHold(element).perform();
	}

	/**
	 * �����Ԫ�ز��ͷ�
	 */
	public void clickAndHold(String key) {
		WebElement element = this.getElement(key);
		this.clickAndHold(element);
	}

	/**
	 * �����Ԫ�ز��ͷ�
	 */
	public void clickAndHold(String key, String parameter) {
		WebElement element = this.getElement(key, parameter);
		this.clickAndHold(element);
	}

	/**
	 * �����Ԫ�ز��ͷ�
	 */
	public void clickAndHoldByXpath(String xpath) {
		WebElement element = this.getElementByXpath(xpath, "���������²��ͷ�");
		this.clickAndHold(element);
	}

	/**
	 * ����ͷ�
	 */
	public void release() {
		Actions act = new Actions(this.browser);
		act.release().perform();
	}

	/**
	 * ����ͷ�
	 */
	public void release(WebElement element) {
		if (element == null) {
			String message = "����ͷ�ʧ��";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.highLight(element);
		Actions act = new Actions(this.browser);
		act.release(element).perform();
	}

	/**
	 * ����ͷ�
	 */
	public void release(String key) {
		WebElement element = this.getElement(key);
		this.release(element);
	}

	/**
	 * ����ͷ�
	 */
	public void release(String key, String parameter) {
		WebElement element = this.getElement(key, parameter);
		this.release(element);
	}

	/**
	 * ����ͷ�
	 */
	public void releaseByXpath(String xpath) {
		WebElement element = this.getElementByXpath(xpath, "����ͷ�");
		this.release(element);
	}
}
