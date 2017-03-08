package com.broada.spring;

import java.io.File;

import org.openqa.selenium.WebElement;

import com.broada.spring.data.InputData;
import com.conf.Messages;

/**
 * 轻量级自动化测试简单封装。 本方法的方法数据，均来自于Selenium-WebDriver
 * 
 * @author Administrator
 * 
 */
public class Open_AutoTest extends BS2AutoTest {
	/**
	 * 输入数据
	 */
	private InputData inputData;

	/**
	 * 该构造方法是用以默认选择IE为启动
	 */
	public Open_AutoTest() {
		// super(0);
	}

	/**
	 * 设置浏览器类型 Chrome -1， IE 0， ff 1
	 */
	public Open_AutoTest(BrowserType bt, Object... args) {
		super(bt, args);
	}

	/**
	 * 加载文件
	 * 
	 * @param pageFile
	 *            页面元素文件
	 * @param inputFile
	 *            输入数据文件
	 * @param expectFile
	 *            期望值文件
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
	 * 向定位到key的输入框中输入excel提供数据
	 * 
	 * @param key
	 *            在yaml文件中的元素
	 * @param inputKey
	 *            通过读取excel获取数据
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
	 * 获取Excel中通过inputkey标示的输入数据
	 * 
	 * @param inputKey
	 * @return
	 */
	public String getInputData(String inputKey) {
		String value = this.inputData.getInputData(inputKey);
		return value;
	}

	/**
	 * 向定位到key的输入框中直接输入数据
	 * 
	 * @param key
	 *            在yaml文件中的元素
	 * @param value
	 *            输入的数据
	 */
	public void input2(String key, String value) {
		this.getElement(key).sendKeys(value);
		this.log.info(this.getElementDes(key) + Messages.getString("input") //$NON-NLS-1$
				+ "\"" + value + "\"");
	}

}
