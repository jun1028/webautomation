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
	 * 多页数据处理
	 */
	protected ArrayList<ElementData> traceList;
	/**
	 * 驱动浏览器
	 */
	protected WebDriver browser;
	/**
	 * 日志控制
	 */
	protected Log log;
	/**
	 * 本次执行过程中窗口的ID记录列表
	 */
	protected ArrayList<String> windowsList;
	/**
	 * 最原始窗口句柄
	 */
	protected String winHandle;
	/**
	 * 页面元素文件列表
	 */
	protected ArrayList<File> fileList;
	/**
	 * 页面元素文件列表
	 */
	protected ArrayList<String> tableList;
	/**
	 * 当前浏览器
	 */
	protected WebDriver currentBrowser;
	/**
	 * 等待时间
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
	 * 获取浏览器信息
	 * 
	 * @return 返回浏览器 和版本
	 */
	public String getBrowserInfo() {
		String s = Utils.STR_NAV_JS;
		String value = (String) this.executeJavaScript(s);
		String browser = "当前执行浏览器： ";
		String version = ",版本：";
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

			return "当前浏览器尚未处理";
		}
		return browser + version;
	}

	/**
	 * 添加新的句柄
	 * 
	 * @param windowHandle
	 */
	public void addHandle(String windowHandle) {
		if (windowHandle == null || windowHandle.length() == 0) {
			String message = "当前提供的window的句柄为空";
			this.log.error(message);
			throwException(message);
		}
		if (this.windowsList.contains(windowHandle)) {
			String message = "窗口句柄已经添加";
			this.log.error(message);
			throwException(message);
			return;
		}
		this.windowsList.add(windowHandle);
	}

	/**
	 * 设置在一定时间后，切换到alert
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
				throwException("当前没有可以切换的警告提示框 ");
			} catch (Exception e) {
				this.log.error("当前没有可以切换的警告提示框 ");
				throwException("当前没有可以切换的警告提示框");
			}
		}
		return false;
	}

	protected void throwException(String message) {
		throw new RuntimeException(message);
	}

	/**
	 * 浏览器历史记录前进
	 */
	public void forward() {
		this.getBrowser().navigate().forward();
	}

	/**
	 * 浏览器历史记录后退
	 */
	public void back() {
		this.getBrowser().navigate().back();
	}

	/**
	 * 释放IE资源.<br>
	 * 在异常执行情况下，存在无法关闭IE浏览器，并且在任务管理器中存在<br>
	 * IEDriverServer的进程,执行完毕后，保证执行机器的干净，关闭存在的<br>
	 * IE和IEDriverServer进程
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
	 * 释放Firefox资源.<br>
	 * 在异常执行情况下，存在无法关闭Firefox浏览器，<br>
	 * 保证执行机器的干净，关闭存在的Firefox进程
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
	 * 释放Chrome资源.<br>
	 * 在异常执行情况下，存在无法关闭Chrome浏览器，并且在任务管理器中存在<br>
	 * ChromeDriver的进程,执行完毕后，保证执行机器的干净，关闭存在的Chrome<br>
	 * 和ChromeDriver进程
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
	 * 窗口关闭
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
		// deleteTmpFiles(); //不能用，否则执行过程中testng被挂起，无法终止
	}

	/**
	 * 设置浏览器句柄
	 * 
	 * @param browser
	 *            需要定制的浏览器
	 */
	public void setBrowser(WebDriver browser) {
		this.browser = browser;
	}

	/**
	 * 获取当前的浏览器句柄
	 * 
	 * @return
	 */
	public WebDriver getBrowser() {
		return this.browser;
	}

	/**
	 * 打开Yaml中的URL 使用该方法，必须在yaml文件中配置URL
	 */
	public void openUrl() {
		SilencerProperties sp = SilencerProperties.getInstance();
		String url = sp.getString("URL");
		String port = sp.getString("webPort");
		if (url == null || url.isEmpty()) {
			String message = "配置文件包conf的silencer.properties文件中，URL基站地址有误";
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
	 * 打开用户自定义URL
	 * 
	 * @param url
	 */
	public void openUrl(String url) {
		if (url == null || url.isEmpty()) {
			String message = "URL基站地址有误";
			this.throwException(message);
		}
		this.browser.get(url);
		this.log.info("加载页面[" + url + "]");
		this.winHandle = this.browser.getWindowHandle();
		this.windowsList.add(winHandle);
	}

	protected void loadData(File file) {
		ElementData yamlData = null;
		try {
			yamlData = new ElementData(file, this.browser);
		} catch (UnsupportedEncodingException e) {
			this.log.error("元素页面文件 " + file.getAbsolutePath()
					+ "的编码方式不符合，请用GBK统一编码。");
			throwException("编码有误");
		} catch (FileNotFoundException e) {
			this.log.error("元素页面文件" + file.getAbsolutePath() + "不存在。");
			throwException("yaml文件不存在，加载失败");
		} catch (IOException e) {
			this.log.error("元素页面文件" + file.getAbsolutePath() + "打开或关闭异常");
			throwException("文件操作失败");
		}
		if (yamlData.getDataHash() == null) {
			this.log.error(Messages.getString("AutoTest.loadYamlfile") //$NON-NLS-1$
					+ file.getAbsolutePath()
					+ Messages.getString("AutoTest.fail")); //$NON-NLS-1$
			throwException("加载文件失败");
		} else {
			if (traceList.contains(yamlData)) {
				throwException("重复加载文件");
			} else {
				traceList.add(yamlData);
				this.log.info(Messages.getString("AutoTest.loadYamlfile") //$NON-NLS-1$
						+ file.getAbsolutePath()
						+ Messages.getString("AutoTest.finish")); //$NON-NLS-1$
			}
		}
	}

	// 增加数据表读取元素的方式
	protected void loadData(String tableName) {
		ElementData DbData = null;
		try {
			DbData = new ElementData(tableName, this.browser);

		} catch (Exception e) {
			this.log.error("元素页面数据库表" + tableName + "不存在。");
			throwException("数据库表不存在，加载失败" + e.getMessage());
		}
		if (DbData.getDataHash() == null) {
			this.log.error(Messages.getString("AutoTest.loadDbTable") //$NON-NLS-1$
					+ tableName + Messages.getString("AutoTest.fail")); //$NON-NLS-1$
			throwException("加载数据库表失败");
		} else {
			if (traceList.contains(DbData)) {
				throwException("重复加载数据库表");
			} else {
				traceList.add(DbData);
				this.log.info(Messages.getString("AutoTest.loadDbTable") //$NON-NLS-1$
						+ tableName + Messages.getString("AutoTest.finish")); //$NON-NLS-1$
			}
		}
	}

	public void setTestTitleMessage(String title, Class<?> t) {
		if (title == null || title.length() == 0) {
			title = "测试开始";
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
	 * 初始化浏览器
	 */
	public void initBrowser() {
		this.log.info("当前操作系统：" + this.getSystemInfo("os.name"));
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
	 * 获取日志控制
	 * 
	 * @return
	 */
	public Log getLog() {
		return this.log;
	}

	/**
	 * 记录日志-info
	 * 
	 * @param message
	 */
	public void log(String message) {
		this.log.info(message);
	}

	/**
	 * 刷新页面
	 */
	public void browserRefresh() {
		try {
			this.browser.navigate().refresh();
		} catch (Exception e) {
			String error = "刷新页面有误!";
			this.log.error(error);
			this.screenShot(error);
			throwException("刷新页面有误");
		}
	}

	/**
	 * 截图
	 * 
	 * @param message
	 */
	protected void screenShot(String message) {
		String title = getDate() + message;
		String mess = title + "." + "png";
		this.log.error("请查看抓图文件: " + Messages.getString("pngs")
				+ File.separator + mess);
		try {
			SpringCapture.screenShot(browser, title);
		} catch (IOException e) {
			this.log.error("抓图失败");
			throwException("抓图失败");
		}
	}

	/**
	 * 在相关网页上执行js脚本
	 * 
	 * @param driver
	 *            需要执行的对象
	 * @param js
	 *            需要执行脚本
	 * @param args
	 *            对应的元素参数
	 * @return
	 */
	public Object executeJavaScript(WebDriver driver, String js, Object... args) {
		try {
			return ((JavascriptExecutor) driver).executeScript(js, args);
		} catch (Exception e) {
			String error = "页面执行Java Script" + js + "有误!";
			this.log.error(error);
			this.screenShot("页面执行Java Script有误");
			throwException("js执行失败");
		}
		return null;
	}

	/**
	 * 在当前浏览器上执行javascript脚本
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
	 * 点击alert弹窗的确定按钮
	 */
	public void acceptAlert() {
		try {
			Alert alert = this.browser.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException e) {
			String message = "警告窗有误或者不存在";
			this.screenShot(message);
			throwException(message);
		}
	}

	/**
	 * 点击alert窗口中的取消按钮
	 */
	public void cancelAlert() {
		try {
			Alert alert = this.browser.switchTo().alert();
			alert.dismiss();
			this.log.info("点击警告窗的取消按钮");
		} catch (NoAlertPresentException e) {
			String message = "警告窗有误或者不存在";
			this.screenShot(message);
			throwException(message);
		}
	}

	/**
	 * 获取alert弹框中的警告信息
	 * 
	 * @return
	 */
	public String getTextOfAlert() {
		String text = null;
		try {
			Alert alert = this.browser.switchTo().alert();
			text = alert.getText();
		} catch (NoAlertPresentException e) {
			String message = "警告窗有误或者不存在";
			this.screenShot(message);
			throwException(message);
		}
		return text;
	}

	/**
	 * 设置元素高亮显示
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
	 * 加载文件
	 * 
	 * @param pageFile
	 *            页面元素文件
	 * @param inputFile
	 *            输入数据文件
	 * @param expectFile
	 *            期望值文件
	 */
	public void loadPageData(String pageFilePath) {
		File f = new File(pageFilePath);
		if (!fileList.contains(f)) {
			this.fileList.add(f);
			this.loadData(f);
		}

	}

	/**
	 * 加载数据库表
	 * 
	 * @param tableName
	 *            页面元素文件
	 * @param inputFile
	 *            输入数据文件
	 * @param expectFile
	 *            期望值文件
	 */
	public void loadPageDataDb(String tableName) {

		if (!tableList.contains(tableName)) {
			this.tableList.add(tableName);
			this.loadData(tableName);
		}

	}

	/**
	 * 当前页面是否含有文本
	 * 
	 * @param content
	 *            文本内容
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
	 * 当前页面中是否出现文本内容
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
	 * 切换至最新弹出的窗口
	 */
	public void selectNewWindow() {
		this.sleep(3);
		browser.getWindowHandles();
		Set<String> aWins = browser.getWindowHandles();
		for (String id : aWins) {
			if (this.windowsList.indexOf(id) == -1) {
				currentBrowser = this.selectWindow(browser, id);
				this.log.info("切换至最新窗口");
				this.windowsList.add(id);
				break;
			}
		}
	}

	/**
	 * 切换至指定的窗口win
	 * 
	 * @param win
	 */
	public void selectWindow(String win) {
		this.selectWindow(this.browser, win);
	}

	/**
	 * 将窗口从driver 切换到NewID的窗口上。
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
			String message = "切换窗口失败";
			this.log.error(message);
			this.screenShot(message);
			throwException(message);
			return null;
		}
		this.log.info("切换窗口成功");
		return d;
	}

	/**
	 * 切换至原来的主要窗口
	 */
	public void selectMainWindow() {
		this.selectWindow(currentBrowser, winHandle);
		this.log.info("切换至主窗口");
	}

	public WebDriver getCurrentWindow() {
		return this.currentBrowser;
	}

	/**
	 * 页面等待
	 * 
	 * @param second
	 *            需要等待的时间，以秒为单位
	 */
	public void sleep(int second) {
		try {
			long millis = second * 1000;
			Thread.sleep(millis);
			this.log.info(Messages.getString("AutoTest.pageWait") + second //$NON-NLS-1$
					+ Messages.getString("AutoTest.millis")); //$NON-NLS-1$
		} catch (InterruptedException e) {
			throwException("等待失败");
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
