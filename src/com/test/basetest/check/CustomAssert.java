package com.test.basetest.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

public class CustomAssert {

	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();

	public static void assertTrue(boolean condition) {
		Assert.assertTrue(condition);
	}

	public static void assertTrue(boolean condition, String message) {
		Assert.assertTrue(condition, message);
	}

	public static void assertFalse(boolean condition) {
		Assert.assertFalse(condition);
	}

	public static void assertFalse(boolean condition, String message) {
		Assert.assertFalse(condition, message);
	}

	public static void assertEquals(boolean actual, boolean expected) {
		Assert.assertEquals(actual, expected);
	}

	public static void assertEquals(Object actual, Object expected) {
		Assert.assertEquals(actual, expected);
	}

	public static void assertEquals(Object[] actual, Object[] expected) {
		Assert.assertEquals(actual, expected);
	}

	public static void assertEquals(Object actual, Object expected,
			String message) {
		Assert.assertEquals(actual, expected, message);
	}

	public static boolean verifyTrue(boolean condition) {
		boolean bResult = false;
		try {
			assertTrue(condition);
			bResult = true;
		} catch (Throwable e) {
			addVerificationFailure(e);
		}
		return bResult;
	}

	public static boolean verifyTrue(boolean condition, String message) {
		boolean bResult = false;
		try {
			assertTrue(condition, message);
			bResult = true;
		} catch (Throwable e) {
			addVerificationFailure(e);
		}
		return bResult;
	}

	public static boolean verifyFalse(boolean condition) {
		boolean bResult = false;
		try {
			assertFalse(condition);
			bResult = true;
		} catch (Throwable e) {
			addVerificationFailure(e);
		}
		return bResult;
	}

	public static boolean verifyFalse(boolean condition, String message) {
		boolean bResult = false;
		try {
			assertFalse(condition, message);
			bResult = true;
		} catch (Throwable e) {
			addVerificationFailure(e);
		}
		return bResult;
	}

	public static boolean verifyEquals(boolean actual, boolean expected) {
		boolean bResult = false;
		try {
			assertEquals(actual, expected);
			bResult = true;
		} catch (Throwable e) {
			addVerificationFailure(e);
		}
		return bResult;
	}

	public static boolean verifyEquals(Object actual, Object expected) {
		boolean bResult = false;
		try {
			assertEquals(actual, expected);
			bResult = true;
		} catch (Throwable e) {
			addVerificationFailure(e);
		}
		return bResult;
	}

	public static boolean verifyEquals(Object[] actual, Object[] expected) {
		boolean bResult = false;
		try {
			assertEquals(actual, expected);
			bResult = true;
		} catch (Throwable e) {
			addVerificationFailure(e);
		}
		return bResult;
	}

	public static boolean verifyEquals(Object actual, Object expected,
			String message) {
		boolean bResult = false;
		try {
			assertEquals(actual, expected, message);
			bResult = true;
		} catch (Throwable e) {
			addVerificationFailure(e);
		}
		return bResult;
	}

	public static void fail(String message) {
		try {
			Assert.fail(message);
		} catch (Throwable e) {
			addVerificationFailure(e);
		}
	}

	public static void exception(Throwable e) {
		addVerificationFailure(e);
	}

	public static void exception(String errMessage) {
		addVerificationFailure(new Exception(errMessage));
	}

	public static List<Throwable> getVerificationFailures() {
		List<Throwable> verificationFailures = verificationFailuresMap
				.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>()
				: verificationFailures;
	}

	private static void addVerificationFailure(Throwable e) {
		List<Throwable> verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(),
				verificationFailures);
		verificationFailures.add(e);
	}

}
