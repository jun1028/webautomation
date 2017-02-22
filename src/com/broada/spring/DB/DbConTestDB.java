package com.broada.spring.DB;

/**
 *ͨ��proxool���ݿ����ӳط������ݿ�
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
	 * ����poxool���ӳ�
	 * 
	 * @author wury
	 * @since 2013.06.25
	 **/
	static {
		if (!connectionAuth()) {
			log.error("�������ݿ�ʧ��!");
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
			log.error("��ʼ��proxool���ӳ�ʧ��:", e);
			return false;
		}
		return true;
	}

	/**
	 * �����ݿ����ӳ��л�ȡһ������
	 * 
	 * @since 2013.06.25
	 * @return �ɹ�:���ݿ�����;ʧ��:null
	 **/
	public static Connection getConnection() {
		Connection conn = null;

		try {
			conn = pool.getConnection();
		} catch (SQLException e) {
			/* �쳣�������� */
			if (connectionAuth()) {
				try {
					conn = pool.getConnection();
				} catch (SQLException ex) {
					log.error("��������ʱ�����쳣", e);
				}
			}
			log.error("�����ݿ����ӳ��л�ȡһ������ʱ�����쳣:", e);
		}
		return conn;
	}

	/**
	 * �����ݿ����ӳ��л�ȡһ������
	 * 
	 * @since 2013.06.25
	 * @param autoCommit
	 *            �Ƿ��Զ�commit autoConmmit
	 * @return �ɹ�:���ݿ�����;ʧ��:null
	 **/
	public static Connection getConnection(boolean autoCommit) {
		Connection conn = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			log.error("�����ݿ����ӳ��л�ȡһ������ʱ�����쳣:", e);
		}
		return conn;
	}

	/**
	 * ���ݿ�����rollback
	 * 
	 * @since 2013.06.25
	 * @param p_connection
	 *            ���ݿ�����
	 **/
	public static void rollback(Connection p_connection) {
		try {
			p_connection.rollback();
		} catch (SQLException e) {
			log.error("�ع�SQL����쳣:", e);
		}
	}

	/**
	 * ���ݿ�����commit
	 * 
	 * @since 2013.06.25
	 * @param p_connection
	 *            ���ݿ�����
	 **/
	public static void commit(Connection p_connection) {
		try {
			p_connection.commit();
		} catch (SQLException e) {
			log.error("�ύ���ݿ�SQL����쳣:", e);
		}
	}

	/**
	 * ���ݿ�����release
	 * 
	 * @since 2013.06.25
	 * @param p_connection
	 *            ���ݿ�����
	 **/
	public static void releaseConnection(Connection p_connection) {
		try {
			if (p_connection != null) {
				p_connection.close();
			}
		} catch (SQLException e) {
			log.error("�ͷ����ݿ������쳣:", e);
		}
	}

	/**
	 * �ر�resultset
	 * 
	 * @since 2013.06.25
	 * @param a_resultSet
	 *            �����
	 **/
	public static void closeResultSet(ResultSet a_resultSet) {
		try {
			if (a_resultSet != null) {
				a_resultSet.close();
			}
		} catch (SQLException e) {
			log.error("�ر����ݿ�����ݼ��쳣:", e);
		}
	}

	/**
	 * �ر����ݿ��������(Statement)
	 * 
	 * @since 2013.06.25
	 * @param a_pstmSQL
	 *            ���ݿ��������
	 **/
	public static void closeStatement(Statement a_pstmSQL) {
		try {
			if (a_pstmSQL != null) {
				a_pstmSQL.close();
			}
		} catch (SQLException e) {
			log.error("�ر����ݿ���������쳣:", e);
		}
	}

	/**
	 * ��ȡproxool���ӳ����Բ��ں�̨��ӡ(�˺��������ο�)
	 * 
	 * @since 2013.06.25
	 **/
	public static boolean showSnapshotInfo() {
		try {
			SnapshotIF snapshot = ProxoolFacade.getSnapshot(dbAlias, true);
			int curActiveCount = snapshot.getActiveConnectionCount();// ��û������
			int availableCount = snapshot.getAvailableConnectionCount();// ��ÿɵõ���������
			int maxCount = snapshot.getMaximumConnectionCount();// �����������
			if (curActiveCount != activeCount)// ����������仯ʱ�������Ϣ
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
