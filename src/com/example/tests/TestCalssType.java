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
		Object test = new TestCalssType();
		if (test instanceof TestCalssType) {
			((TestCalssType) test).close();
		}else{
			System.out.println("fgfg");
		}
	}

	public void close() {
		System.out.println("hello");
	}

}
