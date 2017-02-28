package com.test.common;

import java.io.UnsupportedEncodingException;

public class Utilts {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Utilts.getEncoding("中国"));
		try {
			String k = new String("中国".getBytes("GB2312"), "iso-8859-1");
			System.out.println(Utilts.getEncoding(k));
			System.out.println(k);
			System.out.println(new String(k.getBytes("iso-8859-1"), "gb2312"));
			k = new String("中国".getBytes("iso-8859-1"), "utf-8");
			System.out.println(Utilts.getEncoding(k));
			System.out.println(k);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getEncoding(String str) {        
	       String encode = "GB2312";        
	      try {        
	          if (str.equals(new String(str.getBytes(encode), encode))) {        
	               String s = encode;        
	              return s;        
	           }        
	       } catch (Exception exception) {        
	       }        
	       encode = "ISO-8859-1";        
	      try {        
	          if (str.equals(new String(str.getBytes(encode), encode))) {        
	               String s1 = encode;        
	              return s1;        
	           }        
	       } catch (Exception exception1) {        
	       }        
	       encode = "UTF-8";        
	      try {        
	          if (str.equals(new String(str.getBytes(encode), encode))) {        
	               String s2 = encode;        
	              return s2;        
	           }        
	       } catch (Exception exception2) {        
	       }        
	       encode = "GBK";        
	      try {        
	          if (str.equals(new String(str.getBytes(encode), encode))) {        
	               String s3 = encode;        
	              return s3;        
	           }        
	       } catch (Exception exception3) {        
	       }        
	      return "";        
	   } 

}
