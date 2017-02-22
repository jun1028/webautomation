package com.example.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ll l2 = new ll();
		// List lParams = new ArrayList();
		// lParams.add("d");
		// System.out.println(lParams.get(0));
		// System.out.println((lParams.getClass()).getName());
		// System.out.println((l2.getClass()).getName());
		// Map map = new HashMap();
		// map.put("key", "value");
		//		
		// Object o = map;
		// String v = (String) ((Map) o).get("key");
		// map.put("dd", "oo");
		//		
		// v = (String) ((Map) o).get("dd");
		// System.out.println(v);
		// lParams.add(map);
		// v = (String) ((Map) lParams.get(1)).get("dd");
		// System.out.println(v);
		// v = "jun is here";
		// int pos = v.indexOf("jun");
		// System.out.println(pos);
		// if ( pos>0 ) {
		// System.out.println("dfdf");
		// }
		//		
		LL l = new LL();
		System.out.println(l.getName());
		String ss = "TestHDJFSHSKJFSample";
		ss = ss.replaceAll("Test", "");
		ss = ss.replaceAll("Sample", "");
		System.out.println(ss);
		Map mm = new HashMap();
		mm.put("ttt_params", "jun");
		String key = "ttt";
		if (mm.containsKey(key)) {
			System.out.println(mm.get(key));
		} else {
			key = key + "_params";
			if (mm.containsKey(key)) {
				System.out.println("append param to key " + mm.get(key));
			}
		}
		List lk = new ArrayList();
		lk.add("jun");
		List jun = null;
		jun = lk;
		System.out.println(jun.get(0));
		Map m = null;
		m = new HashMap();
		m.put("g", "d");
		System.out.println(m.get("g"));
		boolean b = false;
		Object o = b;
		if (!Boolean.parseBoolean(o.toString()))
			System.out.println("s");
		System.out.println(Boolean.parseBoolean(o.toString()));

	}

	public String getTest() {
		return this.getClass().toString();
	}

	public String getName() {
		StringBuffer sb = new StringBuffer();
		String name = "";
		name = this.getClass().getSimpleName();
		sb.append("simplename: ");
		sb.append(name);
		sb.append("\n the name: ");
		sb.append(this.getClass().getName());
		return sb.toString();

	}

	public void print() {

	}

}
