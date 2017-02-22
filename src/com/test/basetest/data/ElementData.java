package com.test.basetest.data;

import com.test.basetest.HtmlPage;
import com.test.common.DataConsts;

public class ElementData {

	public void loadPageDataDb(String tbNameOfPageEl, HtmlPage page) {
		if (tbNameOfPageEl != null) {
			String[] tables = tbNameOfPageEl.split(";");
			for (String table : tables) {
				try {
					page.loadPageDataDb(DataConsts.TAG_PAGEDBNAME + "."
							+ formatTableName(table));
				} catch (Exception e) { // need rewrite!
					page.close();
					System.exit(0);
				}
			}
		}
	}

	private String formatTableName(String tableName) {
		return tableName.toLowerCase();
	}

}
