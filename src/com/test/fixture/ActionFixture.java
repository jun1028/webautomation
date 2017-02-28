/**
 * 
 */
package com.test.fixture;

import java.util.Map;

import org.apache.commons.logging.Log;

import com.test.basetest.HtmlPage;
import com.test.basetest.check.CustomAssert;
import com.test.basetest.data.SuiteData;
import com.test.common.ExcelReader;
import com.test.common.IExcelReader;
import com.test.common.LogHelper;

import jxl.Sheet;

/**
 * the function of the class action fixture class include as follows 1) read
 * test case from excel file 2) parse action from test case
 * 
 * @author water
 * @version 1.0
 ***/
public class ActionFixture {
	
	private static Log log = LogHelper.getLog(ActionFixture.class);
	
	private  HtmlPage page = null;
	private StringBuffer errMessage = new StringBuffer();
	/** default value, just make sure every step can go through */
	private boolean bTestResult = true;
	private boolean bExpdException = false;
	/** parameter data object **/
	private Map mParam = null;
	private Map ChsActionMap = null;
	private IExcelReader excel = null;
	private String shName = "";
	private Sheet sheet = null;
	private boolean hasChecked = false;

	// private String toChs;

	/***
	 * 
	 * @param fName
	 *            test case file name (excel type)
	 * @param shName
	 *            sheet name of test case
	 * @param page
	 *            page object
	 * @param obj
	 *            parameter data map
	 * @param bExpectedException
	 *            expect throw exception, default= false
	 */
	public ActionFixture(String fName, String shName, HtmlPage page,
			boolean bExpectedException) {
		this.excel = new ExcelReader(fName);
		init(shName, page, bExpectedException);
	}

	public ActionFixture(String fName, String shName, HtmlPage page) {
		this(fName, shName, page, false);
	}

	public ActionFixture(IExcelReader excel, String shName, HtmlPage page,
			boolean bExpectedException) {
		this.excel = excel;
		init(shName, page, bExpectedException);
	}

	public ActionFixture(IExcelReader excel, String shName, HtmlPage page) {
		this(excel, shName, page, false);
	}

	private void init(String shName, HtmlPage page, boolean bExpectedException) {
		this.shName = shName;
		this.page = page;
		this.sheet = excel.getShByShName(shName);
		this.bExpdException = bExpectedException;
		ChsActionMap = ChsActions.getChsMap();
	}

	public boolean doSheet(int curRetryies) {
		return doSheet(null, curRetryies);
	}

	public boolean doSheet(Object obj, int curRetryies) {
		String cellStr = "";
		if (obj != null) {
			this.mParam = (Map) obj;
		}
		log.debug("start do actionfixture test, the sheet name is :"
				+ this.shName);
		int rowi = 0;
		cellStr = excel.getCellStrValue(1, rowi);
		while (null == cellStr || "".equals(cellStr)) {// if the second column
			rowi++;
			cellStr = excel.getCellStrValue(1, rowi);
		}
		dowRows(rowi);
		return bTestResult;
	}

	public void dowRows(int rowi) {
		int rowNum = sheet.getRows();
		for (int i = rowi; i < rowNum; i++) {
			try {
				doCells(i);
			} catch (RuntimeException e) {
				log.error(e);
				log.error("please check the element key name in DB!");
				if (!hasChecked || (hasChecked && !bTestResult)) {
					CustomAssert
							.exception(new RuntimeException(
									"can't find the element of the key or can't open URL "));
					bTestResult = false;
				}
				break;
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	/******
	 * Execute action in test case of excel, like as click, double click, open,
	 * move on ;
	 * 
	 * @param row
	 */
	public void doCells(int rowi) {
		String cellStr = excel.getCellStrValue(0, rowi);
		String action = "";
		if (cellStr != null && !"".equals(cellStr)) {
			cellStr = cellStr.trim();
			if (ChsActionMap.containsKey(cellStr)) { // Chinese string
				action = (String) ChsActionMap.get(cellStr);
			} else {
				action = cellStr;
			}
			String element = excel.getCellStrValue(1, rowi).trim(); // element
			String value = excel.getCellStrValue(2, rowi); // value column
			if (action.equalsIgnoreCase(Actions.OPEN_PAGE)) {
				if ("".equals(element)) {
					log.error("URL IS NULL");
					page.close();
				}
				log.debug("open element");
				page.open(element);
			} else if (action.equalsIgnoreCase(Actions.INPUT_ACTION)) {
				log.debug("input element");
				page.input(element, getVarValue(value));
			} else if (action.equalsIgnoreCase(Actions.CLICK_ACTION)) {
				String temp = getVarValue(element);
				if ("".equals(temp)) {
					log
							.error("THE CLICK OBJECT CAN NOT BE NULL!, PLEASE CHECK VARIBLE NAME OR PARAMMETER SHEET!");
					log.error("the sheet name is " + this.shName);
					log.error("test terminate!");
					page.close();
					System.exit(-1);
				}
				log.debug("click element");
				try{
					page.click(temp);
					log.debug("after click element");
				}catch(Exception e){
					log.error(e);
				}
			} else if (action.equalsIgnoreCase(Actions.DOUBLECLICK_ACTION)) {
				log.debug("doubleClick element");
				page.doubleClick(element);
				page.sleep(SuiteData.sleepTime);
			} else if (action.equalsIgnoreCase(Actions.MOVEON_ELEMENT)) {
				page.moveOn(element);
			} else if (action.equalsIgnoreCase(Actions.SCREEN_WINDOWS)) {
				page.screenShot(element);
			} else if (action.equalsIgnoreCase(Actions.SWITCH_WINDOWS)) {
				if (element.equalsIgnoreCase("windows")) {
					page.selectNewWindow();
				} else if (element.equalsIgnoreCase("alert")) {
					if (page.alertExists(1))
						page.acceptAlert();
				} else {
					page.selectFrame(element);
					page.sleep(SuiteData.sleepTime);
				}
				page.sleep(2);
			} else if (action.equalsIgnoreCase(Actions.SELECT_ITEM)) {
				int index = 1;
				if (value != null && !"".equals(value)) {
					index = Integer.parseInt(value);
				}
				page.selectItemByIndex(element, index);
			} else if (action.equalsIgnoreCase(Actions.CHECK_ITEM)
					|| action.equalsIgnoreCase(Actions.VERIFY_ITEM)) {
				page.sleep(SuiteData.sleepTime);
				String temp = getVarValue(value);
				log.debug("the expected is :" + temp);
				page.screenShot("check " + value);
				log.debug("check element");
				bTestResult = page.check(element, temp);
				log.debug("test result is: " + bTestResult);
				hasChecked = true;
			} else if (action.equalsIgnoreCase(Actions.SLEEP_ACTION)) {
				int time = 2;
				if (value != null && !"".equals(element)) {
					time = Integer.parseInt(element);
				}
				page.sleep(time);
			} else if (action.equalsIgnoreCase(Actions.UL_LIST)) { // 
				page.selectLi(element, value);
			} else {
				// IGNORE IT
				// errMessage.append("can not know action or invald action! \n");
				// errMessage.append("please contact to automaction tester! \n");
				// log.error(errMessage);
				// page.close();

			}
		}
	}

	/**
	 * if the value start with '#', mean it is variable. remove '#' and get key
	 * value from map
	 */
	public String getVarValue(final String value) {
		String resultVar = "";
		if (value != null && !"".equals(value)) {
			if (value.startsWith("#")) { // if it is variable, start with "#"!
				String var = value.substring(1);
				if (mParam != null && mParam.containsKey(var))
					resultVar = (String) (mParam).get(var);
			} else if (value.startsWith("\\#")) { // value include "#" char
				resultVar = value.substring(1);
			} else {
				resultVar = value;
			}
		}
		return resultVar;
	}

	public void close() {
		if (this.excel != null)
			this.excel.close();
	}

}