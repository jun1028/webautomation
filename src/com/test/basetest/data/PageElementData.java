package com.test.basetest.data;

import java.util.HashMap;
import java.util.List;

import com.broada.spring.DB.DbProxoolUtil;
import com.test.common.DataConsts;

public class PageElementData extends DbProxoolUtil {

	String dbName;
	String tableName;
	String pageName;
	List<Object> results;

	public PageElementData(String tableName) {
		this("page", tableName);
	}

	public PageElementData(String dbName, String tableName) {
		super();
		this.dbName = dbName;
		this.tableName = tableName;
	}

	public String qureyByID(String keyvalue) {
		String result = null;
		return result;
	}

	public String qureyByXpath(String keyvalue, String pageName) {
		return qureyByKey(keyvalue, DataConsts.XPATHVALUE, pageName);
	}

	public String qureyByCss(String keyvalue, String pageName) {
		return qureyByKey(keyvalue, DataConsts.CSSVALUE, pageName);
	}

	public String qureyByIdValue(String keyvalue, String pageName) {
		return qureyByKey(keyvalue, DataConsts.IDVALUE, pageName);
	}

	public String qureyByKey(String keyvalue, String fieldName, String pageName) {
		String result = null;
		String sqlStament = "select " + fieldName + " from " + dbName + "."
				+ tableName;
		String conditionStament = " where keyvalue" + "='" + keyvalue
				+ "' and " + " pagename ='" + pageName + "'";
		sqlStament = sqlStament + conditionStament;
		System.out.println(sqlStament);
		results = query(sqlStament, 1);
		if (results != null && results.size() > 0) {
			result = (String) ((HashMap) results.get(0)).get(fieldName);
		}
		return result;
	}

	// from java.text.messageformat
	// public String format(Object[] obj) {
	// for(int x=0; x<obj.length; x++) {
	// if (obj[x] != null && obj[x].toString().indexOf("'")!= -1){
	// String sValue = obj[x].toString().trim();
	// if(!(sValue.startsWith("'") && sValue.endsWith("'") ||
	// sValue.startsWith("WHERE")))
	// obj[x] = UtilCommon.searchAndReplace("'", "''", sValue);
	// }
	// // Start: Fix for bug 6151
	// // Fix for MSSQL - MSSQL will not accept NULL as a value for int. Must be
	// empty string.
	// // This works for both MYSQL and MSSQL.
	// else if ( obj[x] == null )
	// obj[x]="";
	// // End: Fix for bug 6151
	// }
	//
	// return format(obj);
	// }

}
