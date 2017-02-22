package com.test.testcase;

import org.testng.annotations.Test;

import com.test.basetest.ExcelTypeTest;
import com.test.fixture.ActionFixture;

public class TestActionFixtureWithParam extends ExcelTypeTest {

	private ActionFixture fixture = null;

	public TestActionFixtureWithParam(String fName, String shName) {
		this.fName = fName;
		this.shName = shName;
	}

	@Test(dataProvider = "createMapObj")
	public void testActionWithParam(Object obj) {
		fixture = new ActionFixture(fName, shName, page);
		testMethod(obj, "test");
		fixture.close();
	}

	public boolean test(Object obj) {
		boolean bResult = false;
		try {
			if (fixture != null) {
				bResult = fixture.doSheet(obj, this.curRetryies);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bResult;
	}
}
