package com.broada.spring.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 系统运行参数配置,及加载
 * */

public class SystemCons {

	private static Log log = LogFactory.getLog(SystemCons.class);
	private static int activeCount = 0;
	private static String dbAlias = null;
	private static String dbDriver = null;
	private static String dbUrl = null;
	private static String dbUser = null;
	private static String dbPwd = null;
	private static int minConn = 0;
	private static int maxConn = 0;

	private static String encrypt = null;

	// ------------------------------------------------<

	public static boolean loadSysParam() {

		try {
			ProperFile proper = new ProperFile(System.getProperty("user.dir")
					+ "/conf/silencer.properties");

			setActiveCount(Integer.parseInt(proper.getProper("activeCount")));
			dbAlias = proper.getProper("dbAlias");
			dbDriver = proper.getProper("dbDriver");
			dbUrl = proper.getProper("dbUrl");
			dbUser = proper.getProper("dbUser");
			dbPwd = proper.getProper("dbPwd");
			minConn = Integer.parseInt(proper.getProper("minConn"));
			maxConn = Integer.parseInt(proper.getProper("maxConn"));

			return true;

		} catch (Exception e) {
			log.error("load shepherd.properties error:", e);
			return false;
		}
	}

	public static String getDbAlias() {
		return dbAlias;
	}

	public static void setDbAlias(String dbAlias) {
		SystemCons.dbAlias = dbAlias;
	}

	public static String getDbDriver() {
		return dbDriver;
	}

	public static void setDbDriver(String dbDriver) {
		SystemCons.dbDriver = dbDriver;
	}

	public static String getDbUrl() {
		return dbUrl;
	}

	public static void setDbUrl(String dbUrl) {
		SystemCons.dbUrl = dbUrl;
	}

	public static String getDbUser() {
		return dbUser;
	}

	public static void setDbUser(String dbUser) {
		SystemCons.dbUser = dbUser;
	}

	public static String getDbPwd() {
		return dbPwd;
	}

	public static void setDbPwd(String dbPwd) {
		SystemCons.dbPwd = dbPwd;
	}

	public static int getMinConn() {
		return minConn;
	}

	public static void setMinConn(int minConn) {
		SystemCons.minConn = minConn;
	}

	public static int getMaxConn() {
		return maxConn;
	}

	public static void setMaxConn(int maxConn) {
		SystemCons.maxConn = maxConn;
	}

	public static String getEncrypt() {
		return encrypt;
	}

	public static void setEncrypt(String encrypt) {
		SystemCons.encrypt = encrypt;
	}

	public static void setActiveCount(int activeCount) {
		SystemCons.activeCount = activeCount;
	}

	public static int getActiveCount() {
		return activeCount;
	}

}
