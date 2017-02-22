package com.broada.spring.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class LoadXml {

	@SuppressWarnings( { "rawtypes", "unchecked" })
	public List loadXml(String fileName) {
		File inputXml = new File(fileName);
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
		return list;
	}
}
