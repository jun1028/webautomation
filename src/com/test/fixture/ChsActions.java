package com.test.fixture;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;

import com.test.common.LogHelper;
import com.utilhelper.ProperUtil;

public class ChsActions {

	private static Log log = LogHelper.getLog(ChsActions.class);
	private static String actionPropFileName = "conf" + File.separator
			+ "actions.properties";
	private static ProperUtil proper = null;
	private static Map ChsActionMap = null;

	public static String getProperty(String key) {
		String chsValue = null;
		String skey = key.toString();
		if (proper == null) {
			proper = new ProperUtil(actionPropFileName);
		}
		chsValue = proper.getProperty(skey);
		try {
			chsValue = chsValue.trim();
			chsValue = new String(chsValue.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return chsValue;
	}
	
//	public static Map initChsMap() {
//		return ChsActionMap;
//	}
	
	public static Map getChsMap() {
		if (ChsActionMap == null) {
			ChsActionMap = new HashMap() {
				{
					put(ChsActions.getProperty(Actions.CHECK_ITEM),
							Actions.CHECK_ITEM);
					put(ChsActions.getProperty(Actions.OPEN_PAGE),
							Actions.OPEN_PAGE);
					put(ChsActions.getProperty(Actions.CLOSE_PAGE),
							Actions.CLOSE_PAGE);
					put(ChsActions.getProperty(Actions.CLICK_ACTION),
							Actions.CLICK_ACTION);
					put(ChsActions.getProperty(Actions.SELECT_ITEM),
							Actions.SELECT_ITEM);
					put(ChsActions.getProperty(Actions.INPUT_ACTION),
							Actions.INPUT_ACTION);
					put(ChsActions.getProperty(Actions.MOVEON_ELEMENT),
							Actions.MOVEON_ELEMENT);
					put(ChsActions.getProperty(Actions.SWITCH_WINDOWS),
							Actions.SWITCH_WINDOWS);
					put(ChsActions.getProperty(Actions.SCREEN_WINDOWS),
							Actions.SCREEN_WINDOWS);
					put(ChsActions.getProperty(Actions.DOUBLECLICK_ACTION),
							Actions.DOUBLECLICK_ACTION);
					put(ChsActions.getProperty(Actions.UL_LIST),
							Actions.UL_LIST);
					put(ChsActions.getProperty(Actions.TESTCASE_NAME),
							Actions.TESTCASE_NAME);
				}
			};
		}
		return ChsActionMap;
	}

	public static void main(String args[]) {
		System.out.println(ChsActions.getProperty("open"));
		Map map = ChsActions.getChsMap();
		System.out.println(map.get(ChsActions.getProperty("open")));
	}
}
