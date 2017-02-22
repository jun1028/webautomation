package com.example.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import com.thoughtworks.selenium.*;

public class TestNG extends SeleneseTestNgHelper {
	@Test
	public void testNG() throws Exception {
		selenium.open("http://192.168.1.248/web/");
		selenium.click("id=input_submit");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=入库");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=supplier");
		selenium
				.click("//div[@id='id-searchcompleteMultiselect']/div/div/ul/li[3]");
		selenium.click("id=itemDTOs0.commodityCode");
		selenium.click("id=itemDTOs0.commodityCode");
		selenium.click("id=itemDTOs0.commodityCode");
		selenium.click("id=itemDTOs0.commodityCode");
		selenium.click("id=itemDTOs0.commodityCode");
		selenium.click("id=itemDTOs0.commodityCode");
		selenium.click("id=itemDTOs0.commodityCode");
		selenium.click("css=div.titleWords");
		selenium.click("id=itemDTOs0.commodityCode");
		selenium.click("id=itemDTOs0.commodityCode");
		selenium.click("//div[@id='id-searchcomplete']/div/div/ul/li[2]");
		selenium.click("id=itemDTOs1.commodityCode");
		selenium.click("id=itemDTOs0.commodityCode");
		selenium.click("id=itemDTOs0.productName");
		selenium.click("id=itemDTOs0.productName");
		selenium.click("id=itemDTOs0.productName");
		selenium.click("css=body.bodyMain");
		selenium.click("id=itemDTOs1.commodityCode");
		selenium.click("//div[@id='id-searchcomplete']/div/div/ul/li[2]");
		selenium.click("id=inventorySaveBtn");
		selenium.click("xpath=(//button[@type='button'])[7]");
		selenium.click("id=inventorySaveBtn");
		selenium.click("xpath=(//button[@type='button'])[7]");
		selenium.click("id=inventorySaveBtn");
		selenium.click("xpath=(//button[@type='button'])[7]");
		selenium.select("id=storehouseId", "label=默认仓库");
		selenium.click("id=inventorySaveBtn");
		selenium.click("id=surePayStroage");
		AssertJUnit
				.assertEquals(selenium.getConfirmation(),
						"本次结算应付：550 元，优惠：0 元，挂账0 元，实付：550 元\n（现金：550 元，银行卡：0 元，支票：0 元，取用预付款：0 元）");
		selenium.click("id=surePayStroage");
	}
}
