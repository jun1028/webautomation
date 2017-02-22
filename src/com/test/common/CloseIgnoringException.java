package com.test.common;

import java.io.Closeable;
import java.io.IOException;

public class CloseIgnoringException {

	public static void CloseIgnorException(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException ex) {
				;
			}
		}
	}

}
