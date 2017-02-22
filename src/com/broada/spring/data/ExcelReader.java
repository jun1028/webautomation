package com.broada.spring.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * @author chingsir
 * 
 */
public class ExcelReader {

	private String fileName;
	private Log log;
	private String flag;

	public ExcelReader(String f, Log log) {
		this.fileName = f;
		this.log = log;
		flag = "0";
	}

	/**
	 * 获取数据数据map
	 * 
	 * @return
	 */
	public HashMap<String, String> getDataMap() {
		InputStream inp = null;
		HashMap<String, String> inputMap = new HashMap<String, String>();
		try {
			inp = new FileInputStream(this.fileName);
		} catch (FileNotFoundException e) {
			this.log.error(Messages.getString("AutoTest.file") + this.fileName
					+ Messages.getString("noexists"));
			flag = "-1";
			inputMap = null;
		}
		if (inp != null) {
			Workbook wb = null;
			try {
				wb = WorkbookFactory.create(inp);
				// 获取数据数据map
				Sheet sheet = wb.getSheet("input");
				if (sheet == null) {
					this.log.error(Messages.getString("curFile")
							+ this.fileName + Messages.getString("nosheetName")
							+ "input");
					flag = "-11";
					inputMap = null;
				} else {
					int rowNum = sheet.getLastRowNum() + 1;
					for (int i = 1; i < rowNum; i++) {
						Row row = sheet.getRow(i);
						Cell keyCell = row.getCell(1);
						Cell valueCell = row.getCell(2);
						int type = valueCell.getCellType();
						String key = keyCell.getStringCellValue();
						// CELL_TYPE_NUMERIC 数值型 0
						// CELL_TYPE_STRING 字符串型 1
						// CELL_TYPE_FORMULA 公式型 2
						// CELL_TYPE_BLANK 空值 3
						// CELL_TYPE_BOOLEAN 布尔型 4
						// CELL_TYPE_ERROR 错误 5
						String value = "";
						switch (type) {
						case 0:
							DecimalFormat df = new DecimalFormat("0");

							value = df.format(valueCell.getNumericCellValue());
							break;
						case 1:
							value = valueCell.getStringCellValue();
							break;
						case 2:
							value = valueCell.getCellFormula();
							break;
						case 3:
							break;
						case 4:
							// value = valueCell.get
							break;
						case 5:
							value = String.valueOf(valueCell
									.getErrorCellValue());
							break;
						}
						inputMap.put(key, value);
					}
				}
			} catch (InvalidFormatException e) {
				flag = "-111";
				this.log.error(Messages.getString("readFile") + this.fileName
						+ Messages.getString("AutoTest.fail")
						+ Messages.getString("checkDom4j"));

				inputMap = null;
			} catch (IOException e) {
				//
				flag = "-1111";
				this.log.error(Messages.getString("readFile") + this.fileName
						+ Messages.getString("AutoTest.fail")
						+ Messages.getString("checkExcel"));
				inputMap = null;
			}
			try {
				inp.close();
			} catch (IOException e) {
				flag = "-1111";
				this.log.error(Messages.getString("closeFileStream")
						+ this.fileName + Messages.getString("AutoTest.fail")
						+ Messages.getString("checkEv"));
			}
		}
		return inputMap;
	}

	/**
	 * 获取期望数据的map
	 * 
	 * @return
	 */
	public HashMap<String, String> getExpectMap() {
		InputStream inp = null;
		HashMap<String, String> expectMap = new HashMap<String, String>();
		try {
			inp = new FileInputStream(this.fileName);
		} catch (FileNotFoundException e) {
			this.log.error(Messages.getString("file") + this.fileName
					+ Messages.getString("noexists"));
			flag = "-1";
			expectMap = null;
		}
		if (inp != null) {
			try {
				Workbook wb = WorkbookFactory.create(inp);
				// 获取期望数据
				Sheet expData = wb.getSheet("expect");
				if (expData == null) {
					this.log.error(Messages.getString("curFile")
							+ this.fileName + Messages.getString("nosheetName")
							+ "expect");
					flag = "-11";
					expectMap = null;
				} else {
					int rowExNum = expData.getLastRowNum() + 1;
					for (int i = 1; i < rowExNum; i++) {
						Row row = expData.getRow(i);
						String key = row.getCell(1).getStringCellValue();
						String value = row.getCell(2).getStringCellValue();
						expectMap.put(key, value);
					}
				}
			} catch (InvalidFormatException e) {
				flag = "-111";
				this.log.error(Messages.getString("readFile") + this.fileName
						+ Messages.getString("fail")
						+ Messages.getString("checkDom4j"));

				expectMap = null;
			} catch (IOException e) {
				flag = "-1111";
				this.log.error(Messages.getString("readFile") + this.fileName
						+ Messages.getString("fail")
						+ Messages.getString("checkExcel"));
				expectMap = null;
			}
			try {
				inp.close();
			} catch (IOException e) {
				flag = "-1111";
				this.log.error(Messages.getString("closeFileStream")
						+ this.fileName + Messages.getString("fail")
						+ Messages.getString("checkEv"));
			}
		}
		return expectMap;
	}

	public String getFlag() {
		return this.flag;
	}
}
