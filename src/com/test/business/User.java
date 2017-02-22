/**
 * 
 */
package com.test.business;

public class User extends BaseObject {

	private String username;
	private String pwd;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
