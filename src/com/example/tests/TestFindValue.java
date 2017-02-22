package com.example.tests;

public class TestFindValue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		for (int i = 1000; i < 9999 / 4; i++) {
			int result = i * 4;
			int iqian = i / 1000;
			int ibai = i / 100 - iqian * 10;
			int ishi = i / 10 - ibai * 10 - iqian * 100;
			int ige = i - ishi * 10 - ibai * 100 - iqian * 1000;
			int rqian = result / 1000;
			int rbai = result / 100 - rqian * 10;
			int rshi = result / 10 - rbai * 10 - rqian * 100;
			int rge = result - rshi * 10 - rbai * 100 - rqian * 1000;
			if (iqian == rge && ibai == rshi && ishi == rbai && ige == rqian) {
				System.out.println("the result is " + i);
				System.out.println("the result is " + result);
			}
		}

	}

}
