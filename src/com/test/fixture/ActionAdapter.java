package com.test.fixture;

import com.test.basetest.HtmlPage;
import com.test.basetest.check.BaseCheck;

public class ActionAdapter implements IActions {

	protected HtmlPage page = null;

	@Override
	public void click(String element) {
		page.click(element);
	}

	@Override
	public void doubleClick(String element) {
		page.doubleClick(element);
	}

	@Override
	public void close() {
		page.close();
	}

	@Override
	public void input(String elementkey, String elmentvalue) {
		page.input(elementkey, elmentvalue);
	}

	@Override
	public void moveOn(String elment) {
		page.moveOn(elment);
	}

	@Override
	public void open(String url) {
		page.openUrl(url);
	}

	@Override
	public void select(String obj) {
		// page.select
	}

	/***
	 * @param checkType
	 *            check type, (URL, TEXT, ELEMENT)
	 */
	public boolean check(String checkType, String expected) {
		boolean bResult = false;
		bResult = BaseCheck.parseExpected(checkType, expected, page);
		return bResult;
	}

}
