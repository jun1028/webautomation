package com.example.tests;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestStrSUB {

	/**
	 * @param argswwwww
	 */
	@Test
	public String testMethod() {
		return "myname";
	}

	@DataProvider
	public void testMethod2222(String sss) {
	}

	public static void main(String[] args) {
		String expected = "check:dfhjdhf";
		String temp = "cehck";
		if (expected.contains("check:")) {
			temp = "check:";
		}
		System.out.println( temp);
		System.out.println( expected.indexOf(temp) + expected.length());
		System.out.println( expected.substring(expected.indexOf(temp) + temp.length()));
		// Random r = new Random(System.currentTimeMillis());
		// int rd = Math.abs(r.nextInt()) % 60+1;
		// System.out.println(rd);
//		TestStrSUB t = new TestStrSUB();
//		for (Method m : t.getClass().getMethods())
//			if (m.getAnnotation(Test.class) != null)
//				System.out.println("test");
//			else if (m.getAnnotation(DataProvider.class) != null) {
//				System.out.println("DataProvider");
//			} else {
//				System.out.println("i is null");
			}
	}
