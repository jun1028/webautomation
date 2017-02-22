package com.example.tests;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.test.basetest.BaseTest;
import com.test.basetest.data.PageElementData;
import com.test.common.LogHelper;

public class TestSample extends BaseTest {

	private static Log log = LogHelper.getLog(TestSample.class);
	private StringBuffer verificationErrors = new StringBuffer();
	protected String baseUrl = "";
	protected WebDriver driver = null;
	protected WebElement toElement = null;
	protected boolean acceptNextAlert = true;
	protected PageElementData elementMap = null;

	public boolean test(Object obj) {
		boolean bResult = false;
		Map map = (Map) obj;
		driver.get(baseUrl + "/web/");
		findElementById("登录输入框").click();
		findElementById("登录输入框").sendKeys((String) map.get("userName"));
		findElementById("密码输入框").sendKeys((String) map.get("pwd"));
		findElementById("提交按钮").click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// driver.g
		// Assert.assertEquals((String)driver.get("css=p"), "密码不能为空！");
		try {
			findElementByCss("m_label1").click();
		} catch (Exception e) {
			log.error(e);
			return false;
		}
		Actions actions = new Actions(driver);
		toElement = driver.findElement(By.xpath("//a[text()='进销存']"));
		actions.moveToElement(toElement);
		actions.perform();
		try {
			driver
					.findElement(
							By
									.xpath("//a[@href='storage.do?method=getProducts&type=txn']"))
					.click();
			driver.findElement(By.id("supplier")).click();
			driver
					.findElement(
							By
									.xpath("//div[@id='id-searchcompleteMultiselect']/div/div/ul/li[3]"))
					.click();
			driver.findElement(By.id("itemDTOs0.commodityCode")).click();
			driver.findElement(By.id("itemDTOs0.commodityCode")).click();
			driver.findElement(By.id("itemDTOs0.commodityCode")).click();
			driver.findElement(By.id("itemDTOs0.commodityCode")).click();
			driver.findElement(By.id("itemDTOs0.commodityCode")).click();
			driver.findElement(By.id("itemDTOs0.commodityCode")).click();
			driver.findElement(By.id("itemDTOs0.commodityCode")).click();
			driver.findElement(By.cssSelector("div.titleWords")).click();
			driver.findElement(By.id("itemDTOs0.commodityCode")).click();
			driver.findElement(By.id("itemDTOs0.commodityCode")).click();
			driver
					.findElement(
							By
									.xpath("//div[@id='id-searchcomplete']/div/div/ul/li[2]"))
					.click();
			driver.findElement(By.id("itemDTOs1.commodityCode")).click();
			driver.findElement(By.id("itemDTOs0.commodityCode")).click();
			driver.findElement(By.id("itemDTOs0.productName")).click();
			driver.findElement(By.id("itemDTOs0.productName")).click();
			driver.findElement(By.id("itemDTOs0.productName")).click();
			driver.findElement(By.cssSelector("body.bodyMain")).click();
			driver.findElement(By.id("itemDTOs1.commodityCode")).click();
			driver
					.findElement(
							By
									.xpath("//div[@id='id-searchcomplete']/div/div/ul/li[2]"))
					.click();
			driver.findElement(By.id("inventorySaveBtn")).click();
			Thread.sleep(1000);
			bResult = true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bResult;
	}

	@BeforeMethod
	public void beforeMethod() {
		elementMap = new PageElementData("totalelement");
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.1.248/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			AssertJUnit.fail(verificationErrorString);
		}
	}

	@Override
	public String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	public WebElement findElementById(String keyValue) {
		return driver.findElement(By.id(elementMap.qureyByIdValue(keyValue,
				"login")));
	}

	public WebElement findElementByCss(String keyValue) {
		return driver.findElement(By.cssSelector(elementMap.qureyByCss(
				keyValue, "index")));
	}

	public WebElement findElementByXpath(String keyValue) {
		return driver.findElement(By.className(elementMap.qureyByXpath(
				keyValue, "index")));
	}
}
