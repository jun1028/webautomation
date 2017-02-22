package com.example.tests;

public class TestStatic {

	protected Test jun = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestStatic t = new TestStatic();
		t.jun = Test.getInstance();
		System.out.println(t.jun.name);
	}

}

class Test {
	private static Test test = null;
	String name;

	public Test(String name) {
		this.name = name;
	}

	public static Test getInstance() {
		if (test == null) {
			test = new Test("me");
		}
		return test;
	}
}