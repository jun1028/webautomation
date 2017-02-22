package com.example.tests;

//import org.openqa.selenium.webdriver.common.action_chains.*;
//import selenium.webdriver.common.action_chains.ActionChains;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestOfjava {
	private WebDriver driver;
	private WebElement toElement;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeMethod
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.1.248/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testOfjava() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.1.248/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("start test");
		driver.get(baseUrl + "/web/");
		driver.findElement(By.id("j_username")).click();
		driver.findElement(By.id("j_username")).sendKeys("15995453940");
		driver.findElement(By.id("j_password")).sendKeys("111111");
		driver.findElement(By.id("input_rememberPassword")).click();
		driver.findElement(By.id("input_submit")).click();
		Thread.sleep(1000);
		driver.findElement(By.className("m_label1")).click();
		Actions actions = new Actions(driver);
		toElement = driver.findElement(By.xpath("//a[text()='进销存']"));
		actions.moveToElement(toElement);
		actions.perform();
		// org.openqa.selenium.interactions.Mouse m= (Mouse) ((HasInputDevices
		// )driver).getMouse();
		// MoveMouseAction action2 = new MoveMouseAction( m,
		// (Locatable)toElement);
		// action2.perform();
		// Thread.sleep(5000);
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
		driver.findElement(
				By.xpath("//div[@id='id-searchcomplete']/div/div/ul/li[2]"))
				.click();
		driver.findElement(By.id("itemDTOs1.commodityCode")).click();
		driver.findElement(By.id("itemDTOs0.commodityCode")).click();
		driver.findElement(By.id("itemDTOs0.productName")).click();
		driver.findElement(By.id("itemDTOs0.productName")).click();
		driver.findElement(By.id("itemDTOs0.productName")).click();
		driver.findElement(By.cssSelector("body.bodyMain")).click();
		driver.findElement(By.id("itemDTOs1.commodityCode")).click();
		driver.findElement(
				By.xpath("//div[@id='id-searchcomplete']/div/div/ul/li[2]"))
				.click();
		driver.findElement(By.id("inventorySaveBtn")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//button[@type='button'])[7]")).click();
		driver.findElement(By.id("inventorySaveBtn")).click();
		driver.findElement(By.xpath("(//button[@type='button'])[7]")).click();
		driver.findElement(By.id("inventorySaveBtn")).click();
		driver.findElement(By.xpath("(//button[@type='button'])[7]")).click();
		new Select(driver.findElement(By.id("storehouseId")))
				.selectByVisibleText("默认仓库");
		driver.findElement(By.id("inventorySaveBtn")).click();
		driver.findElement(By.id("surePayStroage")).click();
		AssertJUnit
				.assertEquals(
						"本次结算应付：550 元，优惠：0 元，挂账0 元，实付：550 元\n（现金：550 元，银行卡：0 元，支票：0 元，取用预付款：0 元）",
						closeAlertAndGetItsText());
		driver.findElement(By.id("surePayStroage")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
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
}
