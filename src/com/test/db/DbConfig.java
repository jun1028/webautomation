package com.test.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;

import com.test.common.LogHelper;

public class DbConfig {

	private static Log log = LogHelper.getLog(DbConfig.class);

	public static final String dbConfName = "conf" + File.separator
			+ "db.properties";

	private String dbPwd = "";
	private String dbUrl = "";
	private String dbUser = "";
	private String dbAlias = "";
	private String dbDriver = "";
	private java.sql.Connection conn = null;

	public void init() {
		init(dbConfName);
	}

	public void init(String dbConfName) {
		Properties properties = new Properties();//
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(dbConfName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			log.error(e1);
		}
		try {
			properties.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		dbAlias = properties.getProperty("dbAlias").trim();
		dbDriver = properties.getProperty("dbDriver").trim();
		dbUrl = properties.getProperty("dbUrl").trim();
		dbUser = properties.getProperty("dbUser").trim();
		dbPwd = properties.getProperty("dbPwd").trim();
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			log.error(e);
		}
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public void close() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public String getDbPwd() {
		return dbPwd;
	}

	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbAlias() {
		return dbAlias;
	}

	public void setDbAlias(String dbAlias) {
		this.dbAlias = dbAlias;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public java.sql.Connection getConn() {
		if (conn == null) {
			init();
		}
		return conn;
	}

	public void setConn(java.sql.Connection conn) {
		this.conn = conn;
	}

}
