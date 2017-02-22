/**
 * 
 */
package com.example.tests;

import com.broada.spring.DB.DbProxoolUtil;

/**
 * @author water
 * 
 */
public class TestDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(DbProxoolUtil.query(
				"select * from page.loginelement  ", 0).get(0));

	}

}
