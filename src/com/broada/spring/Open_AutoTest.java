package com.broada.spring;

import java.io.File;

import org.openqa.selenium.WebElement;

import com.broada.spring.data.InputData;
import com.conf.Messages;

/**
 * �������Զ������Լ򵥷�װ�� �������ķ������ݣ���������Selenium-WebDriver
 * 
 * @author Administrator
 * 
 */
public class Open_AutoTest extends BS2AutoTest {
	/**
	 * ��������
	 */
	private InputData inputData;

	/**
	 * �ù��췽��������Ĭ��ѡ��IEΪ����
	 */
	public Open_AutoTest() {
		// super(0);
	}

	/**
	 * ������������� Chrome -1�� IE 0�� ff 1
	 */
	public Open_AutoTest(BrowserType bt, Object... args) {
		super(bt, args);
	}

	/**
	 * �����ļ�
	 * 
	 * @param pageFile
	 *            ҳ��Ԫ���ļ�
	 * @param inputFile
	 *            ���������ļ�
	 * @param expectFile
	 *            ����ֵ�ļ�
	 */
	public void loadData(File pageFile, String inputFile) {
		// this.pageFile = pageFile;
		this.loadData(pageFile);
		this.inputData = new InputData(inputFile, this.log);
		if (inputData.getFlag() != "0") { //$NON-NLS-1$
			this.log
					.error(Messages.getString("AutoTest.loadDatafile") + inputFile //$NON-NLS-1$
							+ Messages.getString("AutoTest.fail")); //$NON-NLS-1$
			// this.close();
		} else {
			this.log
					.info(Messages.getString("AutoTest.loadDatafile") + " " + inputFile //$NON-NLS-1$ //$NON-NLS-2$
							+ Messages.getString("AutoTest.finish")); //$NON-NLS-1$
		}
	}

	/**
	 * ��λ��key�������������excel�ṩ����
	 * 
	 * @param key
	 *            ��yaml�ļ��е�Ԫ��
	 * @param inputKey
	 *            ͨ����ȡexcel��ȡ����
	 */
	@Override
	public void input(String key, String inputKey) {
		String value = this.getInputData(inputKey);
		WebElement element = this.getElement(key);
		if (element != null) {
			this.highLight(element);
			element.sendKeys(value);
			this.log.info(this.getElementDes(key) + Messages.getString("input") //$NON-NLS-1$
					+ "\"" + value + "\"");
		}

	}

	/**
	 * ��ȡExcel��ͨ��inputkey��ʾ����������
	 * 
	 * @param inputKey
	 * @return
	 */
	public String getInputData(String inputKey) {
		String value = this.inputData.getInputData(inputKey);
		return value;
	}

	/**
	 * ��λ��key���������ֱ����������
	 * 
	 * @param key
	 *            ��yaml�ļ��е�Ԫ��
	 * @param value
	 *            ���������
	 */
	public void input2(String key, String value) {
		this.getElement(key).sendKeys(value);
		this.log.info(this.getElementDes(key) + Messages.getString("input") //$NON-NLS-1$
				+ "\"" + value + "\"");
	}

}
