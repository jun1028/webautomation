/**
 * 
 */
package com.test.basetest.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;

import org.apache.commons.logging.Log;

import com.example.tests.ReflectHelper;
import com.test.common.DataConsts;
import com.test.common.ExcelReader;
import com.test.common.FormatUtil;
import com.test.common.IExcelReader;
import com.test.common.LogHelper;

/**
 * the parameter class is responsible for obtain parameter from Excel file
 * 
 * @author water
 * @version 1.0
 */
public class ExcelParamData extends DefaultParamDataAdpter {
	private static Log log = LogHelper.getLog(ExcelParamData.class);
	// public List lParams = new ArrayList();
	private String fName = "";
	private Map mParamslist = null; // need comment
	private String errMessage = "";
	private IExcelReader excel = null;

	/**
	 * initial parameter from excel file into mParamslist!
	 * 
	 * @param fName
	 *            excel file name
	 */
	public ExcelParamData(String fName) {
		this.fName = fName;
		initParamData();
	}

	/**
	 * get parameter data by sheet name
	 * 
	 * @param shName
	 *            sheet name of parameter
	 */
	@Override
	public Object[][] getParamDataByShName(String fName, String shName) {
		Object[][] objs = null;
		List lParams = new ArrayList();
		if (fName != null && shName != null) {
			lParams = genParamDataByShName(fName, shName);
			objs = new Object[lParams.size()][];
			for (int i = 0; i < objs.length; i++) {
				objs[i] = new Object[] { lParams.get(i) };
			}
		}
		return objs;
	}

	/**
	 * get parameter data by key
	 * 
	 * @param key
	 * 
	 */
	@Override
	public Object[][] getParamDataByKey(String key) {
		Object[][] objs = null;
		List lParams = null;
		log.debug("the file name is:" + this.fName);
		if (mParamslist != null) {
			if (mParamslist.containsKey(key)) {
				lParams = (List) mParamslist.get(key);
			} else {
				key = key + DataConsts.TAG_SHEETPARAMSUF;
				if (mParamslist.containsKey(key)) {
					lParams = (List) mParamslist.get(key);
				} else {
					log.info("the key doesn't exist!" + key);
				}
			}
			objs = getParamDataByList(lParams);
		} else {
			log.info("mParamslist iWhether or nots null!");
		}
		return objs;
	}

	public Object[][] getParamDataByList(List lParams) {
		Object[][] objs = null;
		if (lParams != null) {
			objs = new Object[lParams.size()][];
			for (int i = 0; i < objs.length; i++) {
				objs[i] = new Object[] { lParams.get(i) };
			}
		}
		return objs;
	}

	/**
	 * initial parameter data , put the parameter from excel to map
	 */
	public void initParamData() {
		if (fName != null && !"".equals(fName)) {
			excel = new ExcelReader(fName);
			if (excel.getErrorFlag() == -1) {
				errMessage = "create excel file fail! please check file name or path "
						+ fName + " in test*.xml, system will exit!";
				log.info(errMessage);
				System.out.println(errMessage);
				System.exit(0);
			}
			log.debug("initial parameter data");
			if (mParamslist == null) {
				mParamslist = new HashMap();
			} else {
				mParamslist.clear();
			}
			int nums = excel.getNumberOfSheets();
			for (int i = 0; i < nums; i++) {
				String shName = excel.getSheetName(i);
				/**
				 * if sheet name include keyword "_params" indicate it is
				 * parameter sheet
				 */
				if (shName.contains(DataConsts.TAG_SHEETPARAMSUF)) {
					String key = shName;
					Sheet sheet = excel.getShByShName(shName);
					List temp = genLParamsBySh(sheet);
					if (temp != null) {
						key = FormatUtil.formatShName(key);
						mParamslist.put(key, temp);
					}
				}
			}
			excel.close();
		}
	}

	public Map getMParamslist() {
		return mParamslist;
	}

	public void setMParamslist(Map paramslist) {
		mParamslist = paramslist;
	}

	private List genParamDataByShName(String fName, String shName) {
		log.debug("the file name is : " + fName);
		log.debug("the sheet name is : " + shName);
		List lParams = new ArrayList();
		excel = new ExcelReader(fName);
		Sheet sheet = excel.getShByShName(shName);
		lParams = genLParamsBySh(sheet);
		excel.close();
		return lParams;
	}

	private List genLParamsBySh(Sheet sheet) {
		List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
		List<String> lParamNames = new ArrayList<String>();
		try {
			int rowi = 0;
			int coli = 0;
			int colNum = 1;
			int rowNum = sheet.getRows();
			String cellStr = "";
			Map paramMap = new HashMap();
			/** ignore the first row, because it is headline */
			cellStr = excel.getCellStrValue(coli + 1, rowi);
			while (null == cellStr || "".equals(cellStr)) {
				rowi++;
				cellStr = excel.getCellStrValue(coli + 1, rowi);
			} // if it is header, note, checkpoint row, the the second column
			// will be null
			// if ("".equals(cellStr)) { // if the second column is null, means
			// it is header
			// rowi++;
			// }
			//			
			// cellStr = excel.getCellStrValue(coli, rowi);
			//			
			// /** if include "CHECKPOINT", will ignore the row */
			// if (cellStr.contains(DataConsts.CHECKPOINTFLAG)) {
			// rowi++;
			// }
			// cellStr = excel.getCellStrValue(coli, rowi);
			// /** if include "node", will ignore the row */
			// if (cellStr.contains(DataConsts.NODELLAG)) {
			// rowi++;
			// }
			int maxColumCount = sheet.getColumns();
			// generate parameter name by column name
			do {
				cellStr = excel.getCellStrValue(coli, rowi);
				if (cellStr != null && !"".equals(cellStr)) {
					/** if column name contains "expected " means result column */
					if (cellStr.contains(DataConsts.TAG_EXPECTED)) {
						lParamNames.add(DataConsts.TAG_EXPECTED);
						colNum = coli;
						break;
					} else {
						lParamNames.add(cellStr);
						coli++;
					}
				} else {
					coli++;
				}
			} while (coli < maxColumCount);
			rowi++;
			for (int i = rowi; i < rowNum; i++) {
				Map map = new HashMap();
				cellStr = excel.getCellStrValue(0, i);
				// need comment
				// if (cellStr == null || "".equals(cellStr))
				// break;
				for (int col = 0; col < colNum + 1; col++) {
					cellStr = excel.getCellStrValue(col, i);
					map.put(lParamNames.get(col), cellStr);
				}
				// Object obj = createObj();
				tempList.add(map);
				// lParams.add(map);
				// addObject(map, obj);
				// vParamsObj.add(obj);
				// paramMap.clear();
			}
		} catch (NullPointerException e) {
			log.debug("create parameter object fail");
			log.error(e);
		}
		return tempList;
	}

	private void addObject(Map map, Object obj) {
		Set<String> keys = map.keySet();
		String methodName, value;
		String[] args = { "" };
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			value = map.get(key).toString();
			args[0] = value;
			ReflectHelper.setPropery(obj, key, args);
		}
	}
}
