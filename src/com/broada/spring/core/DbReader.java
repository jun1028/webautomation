package com.broada.spring.core;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.broada.spring.DB.DbProxoolUtilTestDB;

/**
 * 
 * @author wury
 * 
 */

public class DbReader {

	private Log logger = LogFactory.getLog(DbReader.class.getName());
	private String tableName;
	private HashMap<String, HashMap<String, String>> map;
	private HashMap<String, String> hmValue;

	public DbReader(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 读取数据库获取数据
	 * 
	 * @return @ throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public HashMap getDataMap() throws Exception {

		List<Object> resultList = DbProxoolUtilTestDB.query("select * from "
				+ tableName, 1);
		// System.out.println("---------------------"+resultList.size()+"------------------");
		map = new HashMap<String, HashMap<String, String>>();
		for (Object hm : resultList) {
			String key = (String) ((HashMap) hm).get("KEYVALUE");
			String id = (String) ((HashMap) hm).get("IDVALUE");
			String css = (String) ((HashMap) hm).get("CSSVALUE");
			String xpath = (String) ((HashMap) hm).get("XPATHVALUE");
			String name = (String) ((HashMap) hm).get("NAMEVALUE");
			String desc = (String) ((HashMap) hm).get("DESCVALUE");

			// System.out.println(key+"     "+id+"   "+css+"   "+xpath+"    "+name+"   ");

			hmValue = new HashMap<String, String>();
			// 当且只有id,css,xpath,name有且只有一个的时候才正确
			int idflag = 0;
			int cssflag = 0;
			int xpathflag = 0;
			int nameflag = 0;
			if (id != null && !id.trim().equals("")) {
				idflag++;
			}
			if (css != null && !css.trim().equals("")) {
				cssflag++;
			}
			if (xpath != null && !xpath.trim().equals("")) {
				xpathflag++;
			}
			if (name != null && !name.trim().equals("")) {
				nameflag++;
			}
			// 当且只有id,css,xpath,name有且只有一个的时候才正确
			if (key != null) {

				if (idflag + cssflag + xpathflag + nameflag == 1) {
					if (idflag == 1) {
						hmValue.put("id", id);
					} else if (cssflag == 1) {
						hmValue.put("css", css);
					} else if (xpathflag == 1) {
						hmValue.put("xpath", xpath);
					} else if (nameflag == 1) {
						hmValue.put("name", name);
					}

				} else {
					logger.error("请检查元素" + key
							+ "数据表中的id,css,xpath,name。要求有且只有一个不为空");
					throw new Exception("元素定位文件错误，请检查key为" + key + "的记录");
				}
				if (desc != null) {
					hmValue.put("des", desc);
				} else {
					hmValue.put("des", "");
				}

				map.put(key, hmValue);

			} else {
				logger.error("数据表存在key为null的记录");
				throw new Exception("数据表存在key为null的记录");
			}

		}

		// System.out.println(map.get("用户密码"));
		return map;

	}

}
