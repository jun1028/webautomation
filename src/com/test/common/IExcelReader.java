package com.test.common;

import java.util.List;

import jxl.Sheet;

public interface IExcelReader {

	public int getErrorFlag();

	public int getNumberOfSheets();

	public String getSheetName(int i);

	public String getCellStrValue(int col, int row);

	public Sheet getCurSheet();

	public Sheet getShByShName(String shName);

	public List getAllSheetNames();

	public void setCurSheet(Sheet sheet);

	public void close();

}
