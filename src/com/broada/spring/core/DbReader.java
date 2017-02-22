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
	 * ��ȡ���ݿ��ȡ����
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
			// ����ֻ��id,css,xpath,name����ֻ��һ����ʱ�����ȷ
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
			// ����ֻ��id,css,xpath,name����ֻ��һ����ʱ�����ȷ
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
					logger.error("����Ԫ��" + key
							+ "���ݱ��е�id,css,xpath,name��Ҫ������ֻ��һ����Ϊ��");
					throw new Exception("Ԫ�ض�λ�ļ���������keyΪ" + key + "�ļ�¼");
				}
				if (desc != null) {
					hmValue.put("des", desc);
				} else {
					hmValue.put("des", "");
				}

				map.put(key, hmValue);

			} else {
				logger.error("���ݱ����keyΪnull�ļ�¼");
				throw new Exception("���ݱ����keyΪnull�ļ�¼");
			}

		}

		// System.out.println(map.get("�û�����"));
		return map;

	}

}
