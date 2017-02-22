package com.test.testcase;

import org.testng.annotations.Test;

import com.test.basetest.ExcelTypeTest;
import com.test.fixture.ActionFixture;

public class TestActionFixture extends ExcelTypeTest {

	private ActionFixture fixture = null;

	public TestActionFixture(String fName, String shName) {
		this.fName = fName;
		this.shName = shName;
	}

	@Test
	public void testAction() {
		fixture = new ActionFixture(fName, shName, page);
		testMethod("test");
		fixture.close();
	}

	public boolean test() {
		boolean bResult = false;
		if (fixture != null) {
			bResult = fixture.doSheet(this.curRetryies);
		}
		return bResult;
	}
}
