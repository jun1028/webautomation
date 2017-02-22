package com.test.basetest;

import org.testng.reporters.XMLReporterConfig;

/**
 * @author Hani Suleiman Date: Mar 27, 2007 Time: 9:16:28 AM
 */
public class CustomXMLReporterConfig extends XMLReporterConfig {

	@Override
	public int getStackTraceOutputMethod() {
		return STACKTRACE_SHORT;
	}
}
