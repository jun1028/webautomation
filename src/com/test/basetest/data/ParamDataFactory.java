package com.test.basetest.data;

public class ParamDataFactory {

	public static IParamData createParamData(String fName) {
		IParamData paramData = null;
		if(fName.endsWith(".xml")){ //xml file
			paramData = new XmlParamData(fName);
		} else{//else if(fName.endsWith(".xls") || fName.endsWith(".xlsx")) {
			paramData = new ExcelParamData(fName);
		}
		return paramData;
	}
	
	public static IParamData createExcelParamData(String fName) {
		return new ExcelParamData(fName);
	}

	public static IParamData createXmlParamData(String fName) {
		return new XmlParamData(fName);
	}

	public static IParamData createDbParamData(String fName) {
		return null;
	}

}
