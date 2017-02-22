package com.test.common;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * the ExcelReader class will read data function from excel file
 * 
 * @author water
 * @version 1.0
 */
public class OldExcelReader implements Closeable {

	private static Log log = LogHelper.getLog(OldExcelReader.class);
	private InputStream inp = null;
	private Workbook workBook = null;
	private Sheet curSheet = null;
	/** -1 means error **/
	public int errorFlag = 0;

	public OldExcelReader(String fName) {

		init(fName);
	}

	public void init(String fName) {
		File file = new File(fName);
		if (file.exists()) {
			try {
				inp = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				log.debug("the file " + file.getAbsolutePath() + " exists!");
				log.debug("new FileInputStream fail!");
				log.error(e);
				errorFlag = -1;
			}
		} else {
			log.info("can't find the find " + file.getAbsolutePath());
			log.info("please check file name! system will be exit!");
			errorFlag = -1;
		}
		if (inp != null) {
			try {
				workBook = WorkbookFactory.create(inp);
			} catch (InvalidFormatException e) {
				log.debug("the file is invalid EXCEL file");
				log.error(e);
				errorFlag = -1;
			} catch (IOException e) {
				log.error(e);
				errorFlag = -1;
			}
		}
	}

	public void close() {
		try {
			inp.close();
		} catch (IOException e) {
			log.error(e);
		}
	}

	public Sheet getCurSheet() {
		return curSheet;
	}

	public Sheet getShByShName(String sheetName) {
		Sheet sheet = null;
		if (workBook != null)
			sheet = workBook.getSheet(sheetName);
		return sheet;
	}

	public String getSheetName(int index) {
		return workBook.getSheetName(index);
	}

	public List<String> getAllSheetNames() {
		List<String> resultList = new ArrayList<String>();
		int sheetNum = workBook.getNumberOfSheets();
		for (int i = 0; i < sheetNum; i++) {
			resultList.add(workBook.getSheetName(i));
		}
		return resultList;
	}

	public int getNumberOfSheets() {
		return workBook.getNumberOfSheets();
	}

	public String getCellStrValue(Cell valueCell) {
		int type = valueCell.getCellType();
		// CELL_TYPE_NUMERIC 数值型 0
		// CELL_TYPE_STRING 字符串型 1
		// CELL_TYPE_FORMULA 公式型 2
		// CELL_TYPE_BLANK 空值 3
		// CELL_TYPE_BOOLEAN 布尔型 4
		// CELL_TYPE_ERROR 错误 5
		String value = "";
		switch (type) {
		case 0:
			String temp = valueCell.toString();
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
			value = String.valueOf(valueCell.getBooleanCellValue());
			break;
		case 5:
			value = String.valueOf(valueCell.getErrorCellValue());
			break;
		}
		return value;
	}

	public void setCurShByShName(String sheetName) {
		curSheet = getShByShName(sheetName);
	}
}
