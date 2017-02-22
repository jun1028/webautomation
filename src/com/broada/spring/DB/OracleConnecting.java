package com.broada.spring.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OracleConnecting {
	String ip = "192.168.18.175";
	String port = "1521";
	String sid = "orcl";
	String thin = "jdbc:oracle:thin:";
	String user = "system";
	String pwd = "system";
	Connection conn = null;
	Statement statement;
	ResultSet rs = null;
	private Log log;

	/**
	 * �û��Զ�������
	 * 
	 * @param Ip
	 * @param port
	 * @param sid
	 * @param user
	 * @param pwd
	 */
	public OracleConnecting(String ip, String port, String sid, String user,
			String pwd, Log log) {
		this.ip = ip;
		this.port = pwd;
		this.sid = sid;
		this.user = user;
		this.pwd = pwd;
		this.log = log;

		String dbUrl = thin + "@" + ip + ":" + port + ":" + sid;
		conn = this.getConnection(dbUrl, user, pwd);
		try {
			this.statement = conn.createStatement();
		} catch (SQLException e) {
			this.log.error("����SQL Statement����ʧ��");
			this.log.error(e);
		}
	}

	public OracleConnecting() {
		String dbUrl = thin + "@" + ip + ":" + port + ":" + sid;
		conn = this.getConnection(dbUrl, user, pwd);
		this.log = LogFactory.getLog(OracleConnecting.class.getName());
		try {
			this.statement = conn.createStatement();
		} catch (SQLException e) {
			this.log.error("����SQL Statement����ʧ��");
			this.log.error(e);
		}
	}

	public Connection getConnection(String url, String user, String password) {
		Connection c = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		} catch (InstantiationException e) {
			this.log.error("���ݿ�ʵ����ʧ��");
			this.log.error(e);
		} catch (IllegalAccessException e) {
			this.log.error("�������ݿ�ʧ��");
			this.log.error(e);
		} catch (ClassNotFoundException e) {
			this.log.error("�����Ϊ�ҵ�����ȷ����ذ��Ƿ����");
			this.log.error(e);
		}
		try {
			c = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			this.log.error("���ݿ�����ʧ��");
			this.log.error(e);
		}
		return c;
	}

	// ִ�в�ѯ
	public ResultSet executeQuery(String sql) {
		rs = null;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void close() {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
