package com.broada.spring;

import java.util.Arrays;

import org.apache.log4j.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.test.basetest.Global;

/**
 * Browser
 * 
 * @author chingsir
 * 
 */
final class BrowserInit {
	
	private static ChromeOptions g_chromeoptions;
	
	/**
	 * 使用IE浏览器
	 * 
	 * @return
	 */
	static WebDriver getIe() {
		String dir_name = ".\\plugin\\driver\\IEDriverServer.exe";
		System.setProperty("webdriver.ie.driver", dir_name);
		DesiredCapabilities ieCapabilities = DesiredCapabilities
				.internetExplorer();
		ieCapabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
		WebDriver ie = new InternetExplorerDriver(ieCapabilities);
		return ie;
	}

	/**
	 * 使用Firefox浏览器
	 * 
	 * @return
	 */
	static WebDriver getFirefox() {
		WebDriver ff = new FirefoxDriver();
		return ff;
	}

	static WebDriver getChrome1() {
		//".\\plugin\\driver\\chromedriver.exe");
		System.out.println();
		System.setProperty("webdriver.chrome.driver",
				"plugin\\driver\\chromedriver.exe");
		WebDriver chrome = new ChromeDriver();
		return chrome;
	}
	
	static WebDriver getChrome()
	{
		WebDriver chrome = null;
		try
		{
			org.apache.log4j.Logger mylog = org.apache.log4j.Logger.getLogger("");
			mylog.setLevel(Level.OFF);
			g_chromeoptions = new ChromeOptions();
			g_chromeoptions.addArguments("--test-type");
			g_chromeoptions.addArguments("--disable-extensions");
			System.setProperty("webdriver.chrome.driver",
					"plugin\\driver\\chromedriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.switches", Arrays.asList("--load-extension=/path/to/extension/directory"));
			chrome = new ChromeDriver(g_chromeoptions);
			
		}
		catch(Exception e)
		{
			System.out.println("Error while initing driver.");
			e.printStackTrace();
		}
		return chrome;
	}
}
