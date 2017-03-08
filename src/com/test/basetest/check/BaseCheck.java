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
		if ("".equals(checkType)){//default text check type
			checkType = "text";
		}
		if (expected != null && !"".equals(expected)) {
			if (!checkByType(checkType, expected, page)) {
				fail(checkType + " check fail, the  expected is " + expected);
				bResult = false;
			} else{
				log.info("check successfull");
			}
		} else {
			log.info("expected result column is null!");
		}
		return bResult;
	}
	
	public static boolean checkByType(String checkType, String expected,
			HtmlPage page) {
		boolean bResult = true;
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
		boolean exist = page.elementExists(elements);
		if (!exist) {
			log.error("[" + elements + "]元素不存在:");
		}
		return exist;
	}

	private static boolean textsContains(String texts, HtmlPage page) {
		boolean result = false;
		String[] textArray = texts.split("\\|");
		for (String text : textArray) {
			try {
				boolean exist = page.isTextPresent2(text);
				if (exist == false) {
					log.error("文本不存在[" + text + "]:");
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
