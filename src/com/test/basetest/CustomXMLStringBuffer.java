package com.test.basetest;

import org.testng.reporters.XMLStringBuffer;

public class CustomXMLStringBuffer extends XMLStringBuffer {
	public void setXmlDetails(String v, String enc) {
		enc = "GBK";
		super.setXmlDetails(v, enc);
	}

}
