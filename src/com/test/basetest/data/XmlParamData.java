package com.test.basetest.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.test.common.LogHelper;

public class XmlParamData extends DefaultParamDataAdpter {

	private static Log log = LogHelper.getLog(XmlParamData.class);
	private String fName = null;
	private List paramsList = null; // need comment
	private String errMessage = "";
	public int errorFlag = 0;

	public XmlParamData(String fName) {
		this.fName = fName;
		init();
	}

	private void init() {
		File inputXml = null;
		try {
			inputXml = new File(this.fName);
		} catch (NullPointerException e) {
			log.info("xml file doesn't exist please check the name \n");
			log.error(e);
			errorFlag = -1;
			return;
		}
		List list = new ArrayList();
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(inputXml);
			Element root = document.getRootElement();
			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element methods = (Element) i.next();
				Map map = new HashMap();
				Map tempMap = new HashMap();
				for (Iterator j = methods.elementIterator(); j.hasNext();) {
					Element node = (Element) j.next();
					String test = node.getText();
					if (test == null) {
						test = "";
					}
					tempMap.put(node.getName(), test);
				}
				map.put(methods.getName(), tempMap);
				list.add(map);
			}
		} catch (DocumentException e) {
			System.out.println(e);
		}
		this.paramsList = list;
	}

	@Override
	public Object[][] getParamDataByKey(String name) {
		List<Map<String, String>> mPramedata = new ArrayList<Map<String, String>>();
		Object[][] result = null;
		if (this.paramsList != null) {
			for (int i = 0; i < this.paramsList.size(); i++) {
				Map m = (Map) this.paramsList.get(i);
				if (m.containsKey(name)) {
					Map<String, String> dm = (Map<String, String>) m.get(name);
					mPramedata.add(dm);
				}
			}
			result = new Object[mPramedata.size()][];
			for (int i = 0; i < mPramedata.size(); i++) {
				result[i] = new Object[] { mPramedata.get(i) };
			}
		}
		return result;
	}
}
