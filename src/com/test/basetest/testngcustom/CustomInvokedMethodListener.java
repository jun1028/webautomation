package com.test.basetest.testngcustom;

import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;

import com.test.basetest.check.CustomAssert;


public class CustomInvokedMethodListener extends InvokedMethodListener {

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult result) {
		Throwable e = null;
		result.setAttribute("TESTNAME", "water");
		Reporter.setCurrentTestResult(result);
		if (method.isTestMethod()) {
			List<Throwable> verificationFailures = CustomAssert
					.getVerificationFailures();
			// if there are verification failures...
			if (verificationFailures.size() > 0) {
				// set the test to failed
				result.setStatus(ITestResult.FAILURE);
				// if there is an assertion failure add it to
				// verificationFailures
				if (result.getThrowable() != null) {
					// Throwable simpleThrow = new
					verificationFailures.add(result.getThrowable());
				}
				int size = verificationFailures.size();
				// if there's only one failure just set that
				if (size == 1) {
					result.setThrowable(verificationFailures.get(0));
				} else {
					// create a failure message with all failures and stack
					// traces (except last failure)
					StringBuffer failureMessage = new StringBuffer(
							"Multiple failures (").append(size)
							.append("):\n\n");
					for (int i = 0; i < size - 1; i++) {
						failureMessage.append("Failure ").append(i + 1).append(
								" of ").append(size).append(":\n");
						Throwable t = verificationFailures.get(i);
						String fullStackTrace = Utils.stackTrace(t, false)[1];
						failureMessage.append(fullStackTrace).append("\n\n");
					}
					// final failure
					Throwable last = verificationFailures.get(size - 1);
					failureMessage.append("Failure ").append(size).append(
							" of ").append(size).append(":\n");
					failureMessage.append(last.toString());
					// set merged throwable
					Throwable merged = new Throwable(failureMessage.toString());
					merged.setStackTrace(last.getStackTrace());
					result.setThrowable(merged);
				}
			}
		}
	}

	public String format(Throwable e) {
		StringBuffer failureMessage = new StringBuffer();
		// failureMessage.append(e.g);
		failureMessage.append(e.getMessage());
		return failureMessage.toString();

	}

}
