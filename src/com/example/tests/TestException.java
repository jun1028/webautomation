package com.example.tests;

public class TestException {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] a = null;
		TestException tt = new TestException();
		try {
			// tt.testException();
			a[1] = "";
		} catch (NullPointerException e) {
			Throwable t = e;
			while (true) {
				if (t == null) {
					break;
				}
				System.out.println("test Throwable");
				System.out.println(e.toString());
				t = t.getCause();
			}
			System.out.println("test getMessage");
			System.out.println(e.getMessage());
			System.out.println("test toString");
			System.out.println(e.toString());
		}
	}

	public void testException() throws NewException {
		throw new NewException("new exception");
	}

	class NewException extends Exception {
		public NewException(String message) {
			super(message);
		}

	}
}
