package com.broada.spring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.broada.spring.cmd.CWinTasklist;
import com.broada.spring.condition.ExceptWindow;
import com.broada.spring.data.ElementData;
import com.broada.spring.utils.SilencerProperties;
import com.broada.spring.utils.Utils;
import com.conf.Messages;

/**
 * Browser
 * 
 * @author chingsir
 * 
 */
public class Browser extends Utils {
	/**
	 * ��ҳ���ݴ���
	 */
	protected ArrayList<ElementData> traceList;
	/**
	 * ���������
	 */
	protected WebDriver browser;
	/**
	 * ��־����
	 */
	protected Log log;
	/**
	 * ����ִ�й����д��ڵ�ID��¼�б�
	 */
	protected ArrayList<String> windowsList;
	/**
	 * ��ԭʼ���ھ��
	 */
	protected String winHandle;
	/**
	 * ҳ��Ԫ���ļ��б�
	 */
	protected ArrayList<File> fileList;
	/**
	 * ҳ��Ԫ���ļ��б�
	 */
	protected ArrayList<String> tableList;
	/**
	 * ��ǰ�����
	 */
	protected WebDriver currentBrowser;
	/**
	 * �ȴ�ʱ��
	 */
	protected int maxWaitfor = Utils.TIME_OUT;
	protected BrowserType browserType;

	public Browser() {
		this(BrowserType.IE);
	}

	public Browser(BrowserType bt, Object... args) {
		PropertyConfigurator.configure(System.getProperty("user.dir")
				+ "/conf/log4j.properties");
		PropertyConfigurator.configure(System.getProperty("user.dir")
				+ "/conf/silencer.properties");
		this.browserType = bt;
		this.log = LogFactory.getLog(Browser.class.getName());
		this.windowsList = new ArrayList<String>();
		this.fileList = new ArrayList<File>();
		this.tableList = new ArrayList<String>();
		this.traceList = new ArrayList<ElementData>();
		if ((args != null && args.length != 0)) {
			if (args[0] instanceof Boolean) {
				if ((Boolean) args[0] == false) {
					return;
				}
			}
		}
		initBrowser();
	}

	public String getMainHandle() {
		return this.winHandle;
	}

	/**
	 * ��ȡ�������Ϣ
	 * 
	 * @return ��������� �Ͱ汾
	 */
	public String getBrowserInfo() {
		String s = Utils.STR_NAV_JS;
		String value = (String) this.executeJavaScript(s);
		String browser = "��ǰִ��������� ";
		String version = ",�汾��";
		if (value.contains("firefox")) {
			browser += "Firefox ";
			version += value.split("firefox/")[1];
		} else if (value.contains("msie")) {
			browser += "IE ";
			version += value.split("msie")[1].split(";")[0];
		} else if (value.contains("chrome")) {
			browser += "Chrome ";
			version += value.split("chrome/")[1].split(" ")[0];
		} else if (value.contains("safari") && !(value.contains("chrome"))) {
			browser += "Safari ";
			version += value.split("version/")[1].split(" ")[0];
		} else {

			return "��ǰ�������δ����";
		}
		return browser + version;
	}

	/**
	 * ����µľ��
	 * 
	 * @param windowHandle
	 */
	public void addHandle(String windowHandle) {
		if (windowHandle == null || windowHandle.length() == 0) {
			String message = "��ǰ�ṩ��window�ľ��Ϊ��";
			this.log.error(message);
			throwException(message);
		}
		if (this.windowsList.contains(windowHandle)) {
			String message = "���ھ���Ѿ����";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.windowsList.add(windowHandle);
	}

	/**
	 * ������һ��ʱ����л���alert
	 * 
	 * @param seconds
	 * @return
	 */
	public boolean alertExists(int seconds) {
		long start = System.currentTimeMillis();
		while ((System.currentTimeMillis() - start) < seconds * 1000) {
			try {
				this.browser.switchTo().alert();
				return true;
			} catch (NoAlertPresentException ne) {
				throwException("��ǰû�п����л��ľ�����ʾ�� ");
			} catch (Exception e) {
				this.log.error("��ǰû�п����л��ľ�����ʾ�� ");
				throwException("��ǰû�п����л��ľ�����ʾ��");
			}
		}
		return false;
	}

	protected void throwException(String message) {
		throw new RuntimeException(message);
	}

	/**
	 * �������ʷ��¼ǰ��
	 */
	public void forward() {
		this.getBrowser().navigate().forward();
	}

	/**
	 * �������ʷ��¼����
	 */
	public void back() {
		this.getBrowser().navigate().back();
	}

	/**
	 * �ͷ�IE��Դ.<br>
	 * ���쳣ִ������£������޷��ر�IE�����������������������д���<br>
	 * IEDriverServer�Ľ���,ִ����Ϻ󣬱�ִ֤�л����ĸɾ����رմ��ڵ�<br>
	 * IE��IEDriverServer����
	 */
	public void destroyIE() {
		ArrayList<String> list = this.getTaskList();
		if (list != null && !(list.isEmpty())) {
			boolean hasIedriver = false;
			for (String name : list) {
				if (name.equalsIgnoreCase("IEDriverServer.exe")
						|| name.equalsIgnoreCase("IEDriverServer")) {
					hasIedriver = true;
					break;
				}
			}
			if (hasIedriver) {
				killIeDriverSever();
			}
			boolean hasIe = false;
			for (String name : list) {
				if (name.equalsIgnoreCase("iexplore.exe")
						|| name.equalsIgnoreCase("iexplore")) {
					hasIe = true;
					break;
				}
			}
			if (hasIe) {
				Utils.killIE();
			}
		}
	}

	/**
	 * �ͷ�Firefox��Դ.<br>
	 * ���쳣ִ������£������޷��ر�Firefox�������<br>
	 * ��ִ֤�л����ĸɾ����رմ��ڵ�Firefox����
	 */
	public void destroyFirefox() {
		ArrayList<String> list = this.getTaskList();
		if (list != null && !(list.isEmpty())) {
			boolean hasChrome = false;
			for (String name : list) {
				if (name.equalsIgnoreCase("firefox.exe")
						|| name.equalsIgnoreCase("firefox")) {
					hasChrome = true;
					break;
				}
			}
			if (hasChrome) {
				Utils.killFirefox();
			}
		}
	}

	/**
	 * �ͷ�Chrome��Դ.<br>
	 * ���쳣ִ������£������޷��ر�Chrome�����������������������д���<br>
	 * ChromeDriver�Ľ���,ִ����Ϻ󣬱�ִ֤�л����ĸɾ����رմ��ڵ�Chrome<br>
	 * ��ChromeDriver����
	 */
	public void destroyChrome() {
		ArrayList<String> list = this.getTaskList();
		if (list != null && !(list.isEmpty())) {
			boolean hasChrome = false;
			for (String name : list) {
				if (name.equalsIgnoreCase("Chrome.exe")
						|| name.equalsIgnoreCase("Chrome")) {
					hasChrome = true;
					break;
				}
			}
			if (hasChrome) {
				Utils.killChrome();
			}
			boolean hasChromeDriver = false;
			for (String name : list) {
				if (name.equalsIgnoreCase("ChromeDriver.exe")
						|| name.equalsIgnoreCase("ChromeDriver")) {
					hasChromeDriver = true;
					break;
				}
			}
			if (hasChromeDriver) {
				Utils.killChromeDriver();
			}
		}
	}

	/**
	 * ���ڹر�
	 */
	public void close() {
		if (this.browser != null) {
			this.browser.quit();
			this.log.info(Messages.getString("AutoTest.endtip")); //$NON-NLS-1$
		}
		if (this.browserType.equals(BrowserType.IE)) {
			this.destroyIE();
		}
		if (this.browserType.equals(BrowserType.CHROME)) {
			this.destroyChrome();
		}
		if (this.browserType.equals(BrowserType.FIREFOX)) {
			this.destroyFirefox();
		}
		// deleteTmpFiles(); //�����ã�����ִ�й�����testng�������޷���ֹ
	}

	/**
	 * ������������
	 * 
	 * @param browser
	 *            ��Ҫ���Ƶ������
	 */
	public void setBrowser(WebDriver browser) {
		this.browser = browser;
	}

	/**
	 * ��ȡ��ǰ����������
	 * 
	 * @return
	 */
	public WebDriver getBrowser() {
		return this.browser;
	}

	/**
	 * ��Yaml�е�URL ʹ�ø÷�����������yaml�ļ�������URL
	 */
	public void openUrl() {
		SilencerProperties sp = SilencerProperties.getInstance();
		String url = sp.getString("URL");
		String port = sp.getString("webPort");
		if (url == null || url.isEmpty()) {
			String message = "�����ļ���conf��silencer.properties�ļ��У�URL��վ��ַ����";
			this.throwException(message);
		}
		String Url = null;
		if (port == null || port.isEmpty()) {
			Url = url;
		} else {
			Url = url + ":" + port;
		}
		this.openUrl(Url);
	}

	/**
	 * ���û��Զ���URL
	 * 
	 * @param url
	 */
	public void openUrl(String url) {
		if (url == null || url.isEmpty()) {
			String message = "URL��վ��ַ����";
			this.throwException(message);
		}
		this.browser.get(url);
		this.log.info("����ҳ��[" + url + "]");
		this.winHandle = this.browser.getWindowHandle();
		this.windowsList.add(winHandle);
	}

	protected void loadData(File file) {
		ElementData yamlData = null;
		try {
			yamlData = new ElementData(file, this.browser);
		} catch (UnsupportedEncodingException e) {
			this.log.error("Ԫ��ҳ���ļ� " + file.getAbsolutePath()
					+ "�ı��뷽ʽ�����ϣ�����GBKͳһ���롣");
			throwException("��������");
		} catch (FileNotFoundException e) {
			this.log.error("Ԫ��ҳ���ļ�" + file.getAbsolutePath() + "�����ڡ�");
			throwException("yaml�ļ������ڣ�����ʧ��");
		} catch (IOException e) {
			this.log.error("Ԫ��ҳ���ļ�" + file.getAbsolutePath() + "�򿪻�ر��쳣");
			throwException("�ļ�����ʧ��");
		}
		if (yamlData.getDataHash() == null) {
			this.log.error(Messages.getString("AutoTest.loadYamlfile") //$NON-NLS-1$
					+ file.getAbsolutePath()
					+ Messages.getString("AutoTest.fail")); //$NON-NLS-1$
			throwException("�����ļ�ʧ��");
		} else {
			if (traceList.contains(yamlData)) {
				throwException("�ظ������ļ�");
			} else {
				traceList.add(yamlData);
				this.log.info(Messages.getString("AutoTest.loadYamlfile") //$NON-NLS-1$
						+ file.getAbsolutePath()
						+ Messages.getString("AutoTest.finish")); //$NON-NLS-1$
			}
		}
	}

	// �������ݱ��ȡԪ�صķ�ʽ
	protected void loadData(String tableName) {
		ElementData DbData = null;
		try {
			DbData = new ElementData(tableName, this.browser);

		} catch (Exception e) {
			this.log.error("Ԫ��ҳ�����ݿ��" + tableName + "�����ڡ�");
			throwException("���ݿ�����ڣ�����ʧ��" + e.getMessage());
		}
		if (DbData.getDataHash() == null) {
			this.log.error(Messages.getString("AutoTest.loadDbTable") //$NON-NLS-1$
					+ tableName + Messages.getString("AutoTest.fail")); //$NON-NLS-1$
			throwException("�������ݿ��ʧ��");
		} else {
			if (traceList.contains(DbData)) {
				throwException("�ظ��������ݿ��");
			} else {
				traceList.add(DbData);
				this.log.info(Messages.getString("AutoTest.loadDbTable") //$NON-NLS-1$
						+ tableName + Messages.getString("AutoTest.finish")); //$NON-NLS-1$
			}
		}
	}

	public void setTestTitleMessage(String title, Class<?> t) {
		if (title == null || title.length() == 0) {
			title = "���Կ�ʼ";
		}
		this.log
				.info("==========================================================");
		this.log.info("=");
		this.log.info("=           " + title);
		this.log.info("=           " + t.getName());
		this.log.info("=");
		this.log
				.info("==========================================================");
	}

	/**
	 * ��ʼ�������
	 */
	public void initBrowser() {
		this.log.info("��ǰ����ϵͳ��" + this.getSystemInfo("os.name"));
		BrowserType bt = this.browserType;
		switch (bt) {
		case IE:
			this.browser = BrowserInit.getIe();
			this.log.info(Messages.getString("AutoTest.ieStart")); //$NON-NLS-1$
			break;
		case FIREFOX:
			this.browser = BrowserInit.getFirefox();
			this.log.info(Messages.getString("AutoTest.ffstart")); //$NON-NLS-1$
			break;
		case CHROME:
			this.browser = BrowserInit.getChrome();
			this.log.info(Messages.getString("AutoTest.ChromeStart"));
			break;
		default:
			this.log
					.error(Messages.getString("AutoTest.browserModel") + this.browserType //$NON-NLS-1$
							+ Messages.getString("AutoTest.errorBroweser")); //$NON-NLS-1$
			this.log.error(Messages.getString("AutoTest.run") //$NON-NLS-1$
					+ Messages.getString("AutoTest.fail")); //$NON-NLS-1$
			System.exit(0);
			break;
		}
		this.log.info(this.getBrowserInfo());
		this.currentBrowser = browser;
		this.browser.manage().window().maximize();
	}

	/**
	 * ��ȡ��־����
	 * 
	 * @return
	 */
	public Log getLog() {
		return this.log;
	}

	/**
	 * ��¼��־-info
	 * 
	 * @param message
	 */
	public void log(String message) {
		this.log.info(message);
	}

	/**
	 * ˢ��ҳ��
	 */
	public void browserRefresh() {
		try {
			this.browser.navigate().refresh();
		} catch (Exception e) {
			String error = "ˢ��ҳ������!";
			this.log.error(error);
			this.screenShot(error);
			throwException("ˢ��ҳ������");
		}
	}

	/**
	 * ��ͼ
	 * 
	 * @param message
	 */
	protected void screenShot(String message) {
		String title = getDate() + message;
		String mess = title + "." + "png";
		this.log.error("��鿴ץͼ�ļ�: " + Messages.getString("pngs")
				+ File.separator + mess);
		try {
			SpringCapture.screenShot(browser, title);
		} catch (IOException e) {
			this.log.error("ץͼʧ��");
			throwException("ץͼʧ��");
		}
	}

	/**
	 * �������ҳ��ִ��js�ű�
	 * 
	 * @param driver
	 *            ��Ҫִ�еĶ���
	 * @param js
	 *            ��Ҫִ�нű�
	 * @param args
	 *            ��Ӧ��Ԫ�ز���
	 * @return
	 */
	public Object executeJavaScript(WebDriver driver, String js, Object... args) {
		try {
			return ((JavascriptExecutor) driver).executeScript(js, args);
		} catch (Exception e) {
			String error = "ҳ��ִ��Java Script" + js + "����!";
			this.log.error(error);
			this.screenShot("ҳ��ִ��Java Script����");
			throwException("jsִ��ʧ��");
		}
		return null;
	}

	/**
	 * �ڵ�ǰ�������ִ��javascript�ű�
	 * 
	 * @param js
	 * @param args
	 * @return
	 */
	public Object executeJavaScript(String js, Object... args) {
		WebDriver wd = this.getBrowser();
		return this.executeJavaScript(wd, js, args);
	}

	/**
	 * ���alert������ȷ����ť
	 */
	public void acceptAlert() {
		try {
			Alert alert = this.browser.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException e) {
			String message = "���洰������߲�����";
			this.screenShot(message);
			throwException(message);
		}
	}

	/**
	 * ���alert�����е�ȡ����ť
	 */
	public void cancelAlert() {
		try {
			Alert alert = this.browser.switchTo().alert();
			alert.dismiss();
			this.log.info("������洰��ȡ����ť");
		} catch (NoAlertPresentException e) {
			String message = "���洰������߲�����";
			this.screenShot(message);
			throwException(message);
		}
	}

	/**
	 * ��ȡalert�����еľ�����Ϣ
	 * 
	 * @return
	 */
	public String getTextOfAlert() {
		String text = null;
		try {
			Alert alert = this.browser.switchTo().alert();
			text = alert.getText();
		} catch (NoAlertPresentException e) {
			String message = "���洰������߲�����";
			this.screenShot(message);
			throwException(message);
		}
		return text;
	}

	/**
	 * ����Ԫ�ظ�����ʾ
	 * 
	 * @param element
	 */
	protected void highLight(WebElement element) {
		if (element == null) {
			return;
		}
		String arg = "arguments[0].style.border = \"" + Utils.HIGHLIGHT_BORDER
				+ "\"";// HIGHLIGHT_BORDER
		this.executeJavaScript(arg, element);
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
	public void loadPageData(String pageFilePath) {
		File f = new File(pageFilePath);
		if (!fileList.contains(f)) {
			this.fileList.add(f);
			this.loadData(f);
		}

	}

	/**
	 * �������ݿ��
	 * 
	 * @param tableName
	 *            ҳ��Ԫ���ļ�
	 * @param inputFile
	 *            ���������ļ�
	 * @param expectFile
	 *            ����ֵ�ļ�
	 */
	public void loadPageDataDb(String tableName) {

		if (!tableList.contains(tableName)) {
			this.tableList.add(tableName);
			this.loadData(tableName);
		}

	}

	/**
	 * ��ǰҳ���Ƿ����ı�
	 * 
	 * @param content
	 *            �ı�����
	 * @return true / false
	 */
	public boolean isTextPresent(String content) {
		boolean status = false;
		try {
			status = this.browser.getPageSource().contains(content);
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	/**
	 * ��ǰҳ�����Ƿ�����ı�����
	 * 
	 * @param pattern
	 * @return
	 */
	public boolean isTextPresent2(String pattern) {
		String Xpath = "//*[contains(text(),\'" + pattern + "\')]";
		try {
			WebElement element = this.browser.findElement(By.xpath(Xpath));
			return element == null ? false : true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * �л������µ����Ĵ���
	 */
	public void selectNewWindow() {
		this.sleep(3);
		browser.getWindowHandles();
		Set<String> aWins = browser.getWindowHandles();
		for (String id : aWins) {
			if (this.windowsList.indexOf(id) == -1) {
				currentBrowser = this.selectWindow(browser, id);
				this.log.info("�л������´���");
				this.windowsList.add(id);
				break;
			}
		}
	}

	/**
	 * �л���ָ���Ĵ���win
	 * 
	 * @param win
	 */
	public void selectWindow(String win) {
		this.selectWindow(this.browser, win);
	}

	/**
	 * �����ڴ�driver �л���NewID�Ĵ����ϡ�
	 * 
	 * @param driver
	 * @param newId
	 * @return
	 */
	public WebDriver selectWindow(WebDriver driver, String newId) {
		WebDriver d = null;
		WebDriverWait wait = new WebDriverWait(driver, TIME_OUT);
		ExceptWindow ew = new ExceptWindow(newId);
		this.sleep(3);
		try {
			d = wait.until(ew);
		} catch (TimeoutException te) {
			String message = "�л�����ʧ��";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
			return null;
		}
		this.log.info("�л����ڳɹ�");
		return d;
	}

	/**
	 * �л���ԭ������Ҫ����
	 */
	public void selectMainWindow() {
		this.selectWindow(currentBrowser, winHandle);
		this.log.info("�л���������");
	}

	public WebDriver getCurrentWindow() {
		return this.currentBrowser;
	}

	/**
	 * ҳ��ȴ�
	 * 
	 * @param second
	 *            ��Ҫ�ȴ���ʱ�䣬����Ϊ��λ
	 */
	public void sleep(int second) {
		try {
			long millis = second * 1000;
			Thread.sleep(millis);
			this.log.info(Messages.getString("AutoTest.pageWait") + second //$NON-NLS-1$
					+ Messages.getString("AutoTest.millis")); //$NON-NLS-1$
		} catch (InterruptedException e) {
			throwException("�ȴ�ʧ��");
		}
	}

	public ArrayList<String> getTaskList() {
		CWinTasklist list = CWinTasklist.getInstance();
		return list.getTaskList();
	}

	public String getSystemInfo(String cmd) {
		return System.getProperty(cmd);
	}
}
