package com.test.basetest;

import org.testng.reporters.XMLReporterConfig;

/**
 */
public class CustomXMLReporterConfig extends XMLReporterConfig {

	@Override
	public int getStackTraceOutputMethod() {
		return STACKTRACE_SHORT;
	}
}
