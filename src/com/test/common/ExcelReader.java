package com.test.common;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.logging.Log;

/**
 * the ExcelReader class will read data function from excel file
 * 
 * @author water
 * @version 1.0
 */
public class ExcelReader implements Closeable, IExcelReader {

	private static Log log = LogHelper.getLog(ExcelReader.class);
	private Workbook workBook = null;
	private Sheet curSheet = null;
	/** -1 means error **/
	private int errorFlag = 0;

	public ExcelReader(String fName) {
		init(fName);
	}

	public void init(String fName) {
		File file = new File(fName);
		if (file.exists()) {
			try {
				this.workBook = Workbook.getWorkbook(file);
				this.curSheet = this.workBook.getSheet(0);
			} catch (IOException e) {
				log.error(e);
				errorFlag = -1;
			} catch (BiffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			log.info("can't find the find " + file.getAbsolutePath());
			log.info("please check file name! system will be exit!");
			errorFlag = -1;
		}
	}

	public Sheet getCurSheet() {
		return curSheet;
	}

	public void setCurSheet(Sheet curSheet) {
		this.curSheet = curSheet;
	}

	public Sheet getShByShName(String sheetName) {
		Sheet sheet = null;
		if (workBook != null) {
			sheet = workBook.getSheet(sheetName);
			this.curSheet = sheet;
		}
		return sheet;
	}

	public String getSheetName(int index) {
		return workBook.getSheet(index).getName();
	}

	public List<String> getAllSheetNames() {
		List<String> resultList = new ArrayList<String>();
		int sheetNum = workBook.getNumberOfSheets();
		for (int i = 0; i < sheetNum; i++) {
			resultList.add(workBook.getSheet(i).getName());
		}
		return resultList;
	}

	public int getNumberOfSheets() {
		return workBook.getNumberOfSheets();
	}

	public String getCellStrValue(Cell valueCell) {
		return valueCell.getContents();
	}

	public int getErrorFlag() {
		return errorFlag;
	}

	public String getCellStrValue(int col, int row) {
		String value = "";
		if (this.curSheet != null) {
			try {
				value = this.curSheet.getCell(col, row).getContents();
			} catch (Exception e) {
				log.error(e);
			}
		}
		return value;
	}

	public void close() {
		if (this.workBook != null) {
			this.workBook.close();
		}
	}

	public static void main(String args[]) {
		ExcelReader excel = new ExcelReader(
				"testcases\\inquerystat-testcase.xls");
		Sheet sheet = excel.getShByShName("productThrough_params");
		for (int i = 0; i < sheet.getRows(); i++) {
			for (int j = 0; j < sheet.getColumns(); j++) {
				Cell cell = sheet.getCell(j, i);
				System.out.print(cell.getContents() + " ");
			}
			System.out.print("\n");
		}
	}

}
