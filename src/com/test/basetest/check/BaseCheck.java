/**
 * 
 */
package com.test.basetest.check;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;

import com.broada.spring.DB.DbProxoolUtilTestDB;
import com.test.basetest.HtmlPage;
import com.test.common.LogHelper;
import com.test.db.DbCheck;

/**
 * the class is responsible for verify expected data in excel file by check page
 * element or database record
 * 
 * @version 1.0
 */
public class BaseCheck extends CustomAssert {

	private static Log log = LogHelper.getLog(BaseCheck.class);

	public static boolean parseExpected(String expected, HtmlPage page) {
		return parseExpected("text", expected, page);
	}
	
	/**
	 * parse the special expected result by keyword. if include "select"
	 * keyword, will execute query from business database if include
	 * "check link " or "check text " etc. will check page otherwise , it is
	 * only expected value
	 * 
	 * @param expected
	 *            expected result
	 * @param page
	 *            page object
	 * @param checkType
	 *            check type, URL, ELEMENT, TEXT type
	 */
	public static boolean parseExpected(String checkType, String expected,
			HtmlPage page) {
		boolean bResult = true; // default value, just go through, test pass
		String checkContent = "";
		if (!"".equals(expected) && expected != null) {
			if (expected.contains("select")) { // select statement , query data
				// from database
				if (expected.contains("select:")) {
					checkContent = expected.substring(expected
							.indexOf("select:")
							+ "select:".length());
				}
				bResult = selectData(checkContent);
			} else {
				if (expected.contains("check")) {
					String temp = "cehck";
					if (expected.contains("check:")) {
						temp = "check:";
					}
					checkContent = expected.substring(expected.indexOf(temp)
							+ temp.length());
					if (!checkByType(checkType, expected, page)) {
						fail("text don't exists " + expected);
						bResult = false;
					}
				} else if (expected.contains("pass")) {
					log.info("ignore check result just make sure the flow!");
				} else {
					if (!checkByType(checkType, expected, page)) {
						if ("".equals(checkType)){
							checkType = "text";
						}
						fail(checkType + "text don't exists " + expected);
						bResult = false;
					}
				}
			}

		} else {
			fail("expected result column is null!");
			log.info("expected result column is null!");
		}
		return bResult;
	}

	public static boolean checkByType(String checkType, String expected,
			HtmlPage page) {
		boolean bResult = false;
		if ("element".equalsIgnoreCase(checkType)) {
			bResult = elementContains(expected, page);
		} else if ("url".equalsIgnoreCase(checkType)) {
			bResult = URLContains(expected, page);
		} else {
			bResult = textsContains(expected, page);
		}
		return bResult;
	}

	public static boolean selectData(String querysql) {
		boolean bResult = false;
		DbCheck dbcheck = DbCheck.getInstance();
		List resultList = dbcheck.selectDataFromTestDB(querysql);
		if (resultList.size() > 1)
			bResult = true;
		return bResult;
	}

	private static boolean elementContains(String elements, HtmlPage page) {
		boolean elementExits = false;
		String[] elementArray = elements.split("\\|");
		for (String e : elementArray) {
			List resultlist1 = DbProxoolUtilTestDB
					.query("select *  from test.firstpage a where a.checkkey ="
							+ e, 1);

			for (Object hm : resultlist1) {
				String key = (String) ((HashMap) hm).get("KEY");
				elementExits = page.elementExists(key);
				if (elementExits == false) {
					log.error("[" + key + "]元素存在:" + elementExits);
					return elementExits;
				}
			}
		}
		return elementExits;
	}

	private static boolean textsContains(String texts, HtmlPage page) {
		boolean result = false;
		String[] textsArray = texts.split("\\|");
		for (String text : textsArray) {
			try {
				boolean textExits = page.isTextPresent2(text);
				if (textExits == false) {
					log.error("文本不存在[" + text + "]:" + textExits);
					result = false;
					break;
				} else
					result = true;
			} catch (NullPointerException e) {
				log.error(e);
			}
		}
		return result;
	}

	private static boolean URLContains(String URL, HtmlPage page) {
		boolean bResult = false;
		if (URL == null || URL.equals("")) {
			log.info("URL为空");
		} else {
			if (page.getBrowser().getCurrentUrl().contains(URL)) {
				bResult = true;
			}
		}
		return bResult;
	}
}
