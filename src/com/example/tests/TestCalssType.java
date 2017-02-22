/**
 * 
 */
package com.example.tests;

/**
 * @author water
 * 
 */
public class TestCalssType {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Object test = new Object();
		if (test instanceof TestCalssType) {
			((TestCalssType) test).close();
		}
	}

	public void close() {
		System.out.println("hello");
	}

}
