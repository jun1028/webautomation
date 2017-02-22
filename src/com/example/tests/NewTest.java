package com.example.tests;

import org.testng.annotations.Test;

import com.test.business.User;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class NewTest extends TestSample {

	@Test(dataProvider = "createObj")
	public void test(User obj) {
		driver.get(baseUrl + "/web/");
		findElementById("µÇÂ¼ÊäÈë¿ò").click();
		findElementById("µÇÂ¼ÊäÈë¿ò").sendKeys(obj.getUserName());
		findElementById("ÃÜÂëÊäÈë¿ò").sendKeys(obj.getPwd());
		findElementById("Ìá½»°´Å¥").click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		findElementByCss("m_label1").click();
		Actions actions = new Actions(driver);
		toElement = driver.findElement(By.xpath("//a[text()='½øÏú´æ']"));
		actions.moveToElement(toElement);
		actions.perform();
		// org.openqa.selenium.interactions.Mouse m= (Mouse) ((HasInputDevices
		// )driver).getMouse();
		// MoveMouseAction action2 = new MoveMouseAction( m,
		// (Locatable)toElement);
		// action2.perform();
		// Thread.sleep(5000);
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
