package com.example.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectHelper {

	public static Object getPropery(Object obj, String key) {
		Object result = null;
		String firstLetter = key.substring(0, 1).toUpperCase();
		/**
		 * invoke The owner class get +Property Name method
		 */
		String methodName = "get" + firstLetter + key.substring(1);
		Class<? extends Object> ownerClass = obj.getClass();
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
			result = method.invoke(obj);
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
		return result;
	}

	public static void setPropery(Object obj, String key, Object[] args) {
		String firstLetter = key.substring(0, 1).toUpperCase();
		String methodName = "set" + firstLetter + key.substring(1);
		try {
			invokeMethod(obj, methodName, args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getStrOfObject(Object obj) {
		String result = null;
		result = obj.toString();
		return result;
	}

	public static Object invokeMethod(Object obj, String methodName,
			Object[] args) throws Exception {
		Class<? extends Object> ownerClass = obj.getClass();
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(obj, args);
	}
}
