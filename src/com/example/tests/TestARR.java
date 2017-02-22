package com.example.tests;

import java.util.ArrayList;
import java.util.List;

public class TestARR {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List temp = new ArrayList();
		temp.add("jun");
		temp.add("is");
		if (temp.contains("jun")) {
			System.out.println("pass");
		}
		if (!temp.contains("is")) {
			System.out.println("is");
		}

	}

}
