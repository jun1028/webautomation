package com.broada.spring.DB;

/**
 *通过proxool数据库连接池访问数据库
 *
 *@author wury
 *@version  1.0.0
 *@satement
 * 
 * */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;
import org.openqa.jetty.log.LogFactory;

import com.broada.spring.utils.SilencerProperties;
import com.test.db.DbCheck;

public class DbConTestDB {

	private static SilencerProperties silencerProperties = null;
	private static ProxoolDataSource pool = null;
	private static String activeCountstr = null;
	private static String dbAlias = null;
	private static String dbDriver = null;
	private static String dbUrl = null;
	private static String dbUser = null;
	private static String dbPwd = null;
	private static String minConnstr = null;
	private static String maxConnstr = null;

	private static Log log = null;

	private static int activeCount;
	private static int minConn;
	private static int maxConn;
	/**
	 * 创建poxool连接池
	 * 
	 * @author wury
	 * @since 2013.06.25
	 **/
	static {
		if (!connectionAuth()) {
			log.error("连接数据库失败!");
		}
	}

	private static void init() {
		silencerProperties = SilencerProperties.getInstance();
		activeCountstr = silencerProperties.getString("test.activeCount");
		System.out.print(activeCountstr);
		dbAlias = silencerProperties.getString("test.dbAlias");
		dbDriver = silencerProperties.getString("test.dbDriver");
		dbUrl = silencerProperties.getString("test.dbUrl");
		dbUser = silencerProperties.getString("test.dbUser");
		dbPwd = silencerProperties.getString("test.dbPwd");
		minConnstr = silencerProperties.getString("test.minConn");
		maxConnstr = silencerProperties.getString("test.maxConn");
		log = LogFactory.getLog(DbConTestDB.class);
		activeCount = Integer.parseInt(activeCountstr);
		minConn = Integer.parseInt(minConnstr);
		maxConn = Integer.parseInt(maxConnstr);
	}

	public static boolean connectionAuth() {

		init();
		try {
			pool = new ProxoolDataSource();
			pool.setAlias(dbAlias);
			pool.setDriver(dbDriver);
			pool.setDriverUrl(dbUrl);
			pool.setUser(dbUser);
			pool.setPassword(dbPwd);
			pool.setDelegateProperties("user=" + dbUser + ",password=" + dbPwd);
			pool.setMinimumConnectionCount(minConn);
			pool.setMaximumConnectionCount(maxConn);
			pool.setHouseKeepingSleepTime(900000);
			// pool.setHouseKeepingTestSql("select 1 from dual");
		} catch (Exception e) {
			log.error("初始化proxool连接池失败:", e);
			return false;
		}
		return true;
	}

	/**
	 * 从数据库连接池中获取一个连接
	 * 
	 * @since 2013.06.25
	 * @return 成功:数据库连接;失败:null
	 **/
	public static Connection getConnection() {
		Connection conn = null;

		try {
			conn = pool.getConnection();
		} catch (SQLException e) {
			/* 异常重新链接 */
			if (connectionAuth()) {
				try {
					conn = pool.getConnection();
				} catch (SQLException ex) {
					log.error("重新连接时发生异常", e);
				}
			}
			log.error("从数据库连接池中获取一个连接时发生异常:", e);
		}
		return conn;
	}

	/**
	 * 从数据库连接池中获取一个连接
	 * 
	 * @since 2013.06.25
	 * @param autoCommit
	 *            是否自动commit autoConmmit
	 * @return 成功:数据库连接;失败:null
	 **/
	public static Connection getConnection(boolean autoCommit) {
		Connection conn = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			log.error("从数据库连接池中获取一个连接时发生异常:", e);
		}
		return conn;
	}

	/**
	 * 数据库连接rollback
	 * 
	 * @since 2013.06.25
	 * @param p_connection
	 *            数据库连接
	 **/
	public static void rollback(Connection p_connection) {
		try {
			p_connection.rollback();
		} catch (SQLException e) {
			log.error("回滚SQL语句异常:", e);
		}
	}

	/**
	 * 数据库连接commit
	 * 
	 * @since 2013.06.25
	 * @param p_connection
	 *            数据库连接
	 **/
	public static void commit(Connection p_connection) {
		try {
			p_connection.commit();
		} catch (SQLException e) {
			log.error("提交数据库SQL语句异常:", e);
		}
	}

	/**
	 * 数据库连接release
	 * 
	 * @since 2013.06.25
	 * @param p_connection
	 *            数据库连接
	 **/
	public static void releaseConnection(Connection p_connection) {
		try {
			if (p_connection != null) {
				p_connection.close();
			}
		} catch (SQLException e) {
			log.error("释放数据库连接异常:", e);
		}
	}

	/**
	 * 关闭resultset
	 * 
	 * @since 2013.06.25
	 * @param a_resultSet
	 *            结果集
	 **/
	public static void closeResultSet(ResultSet a_resultSet) {
		try {
			if (a_resultSet != null) {
				a_resultSet.close();
			}
		} catch (SQLException e) {
			log.error("关闭数据库查数据集异常:", e);
		}
	}

	/**
	 * 关闭数据库操作声明(Statement)
	 * 
	 * @since 2013.06.25
	 * @param a_pstmSQL
	 *            数据库操作声明
	 **/
	public static void closeStatement(Statement a_pstmSQL) {
		try {
			if (a_pstmSQL != null) {
				a_pstmSQL.close();
			}
		} catch (SQLException e) {
			log.error("关闭数据库操作声明异常:", e);
		}
	}

	/**
	 * 获取proxool连接池属性并在后台打印(此函数仅供参考)
	 * 
	 * @since 2013.06.25
	 **/
	public static boolean showSnapshotInfo() {
		try {
			SnapshotIF snapshot = ProxoolFacade.getSnapshot(dbAlias, true);
			int curActiveCount = snapshot.getActiveConnectionCount();// 获得活动连接数
			int availableCount = snapshot.getAvailableConnectionCount();// 获得可得到的连接数
			int maxCount = snapshot.getMaximumConnectionCount();// 获得总连接数
			if (curActiveCount != activeCount)// 当活动连接数变化时输出的信息
			{
				log.error("active DB connect account:" + curActiveCount
						+ "(active)");
				log.error("available connect account:" + availableCount
						+ "(available)");
				log.error("max connect account:" + maxCount + "(max)");

				activeCount = curActiveCount;
			} else {
				return false;
			}
			return true;
		} catch (ProxoolException e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	public static void main(String args[]) {
		DbConTestDB.getConnection();
	}
}
