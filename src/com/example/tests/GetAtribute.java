package com.example.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.test.basetest.BaseTest;

public class GetAtribute {

	/**
	 * @param args
	 */
	String name, id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetAtribute n = new GetAtribute();
		n.setName("dfdf");
		String[] args1 = { "jun" };
		Object o = n.setFieldValue(n, "name", args1);
		System.out.println(n.getName());
		String value = n.getValue(n, "name");
		System.out.println(value);
//		TestLoginSample tt = new TestLoginSample();
//		HashMap<String, Object> m = new HashMap<String, Object>();
//		m.put("jun", "123");
//		try {
//			Object[] args11 = { m };
//			Method[] me = tt.getClass().getDeclaredMethods();
//			for (Method xk : me) {
//				System.out.println(xk.getName());
//				if ("testJ".equalsIgnoreCase(xk.getName())) {
//					Object temp = xk.invoke(tt, args11);
//				}
//			}
//			Object temp = BaseTest.invokeMethod(tt, "testJ", args11);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	/**
	 * 根据属性名获取属性值
	 * */
	public Object setFieldValue(Object o, String fieldName, Object[] args) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String methodName = "set" + firstLetter + fieldName.substring(1);
			Class[] argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {
				argsClass[i] = args[i].getClass();
			}
			Method method = this.getClass().getMethod(methodName, argsClass);
			Object value = method.invoke(o, args);
			return value;

		} catch (Exception e) {
			return null;
		}
	}

	public String getValue(Object obj, String key) {
		System.out.println("d");
		String value = null;
		String firstLetter = key.substring(0, 1).toUpperCase();
		String methodName = "get" + firstLetter + key.substring(1); // invoke
																	// The owner
																	// class set
																	// +
																	// PropertyName
																	// method

		Class ownerClass = obj.getClass();
		Method method = null;
		try {
			method = ownerClass.getMethod(methodName);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			;
			value = (String) method.invoke(obj);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

}
