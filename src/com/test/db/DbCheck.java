package com.test.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;

import com.test.common.LogHelper;

public class DbCheck {

	private static Log log = LogHelper.getLog(DbCheck.class);
	private static DbConfig dbConfig = new DbConfig();
	private static DbCheck dbCheck = null;

	private DbCheck() {
	}

	public static DbCheck getInstance() {
		if (dbCheck == null) {
			dbCheck = new DbCheck();
		}
		return dbCheck;
	}

	public Connection getConnection() {
		return dbConfig.getConn();
	}

	public void close() {
		dbConfig.close();
	}

	public List<Object> selectDataFromTestDB(String sql) {
		return query(sql, 1);
	}

	public String format(String str) {
		// add code
		return str.trim();
	}

	/**
	 * @param sql
	 * @param returnType
	 *            0 means list, 1 means map
	 * @param parm
	 * @return
	 */
	public List<Object> query(String sql, int returnType, String parm) {
		List<Object> results = new ArrayList<Object>();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		try {
			connection = getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, Integer.getInteger(parm));
			set = stmt.executeQuery();
			ResultSetMetaData rsmd = set.getMetaData();
			int colCount = rsmd.getColumnCount();
			if (returnType == 0) {
				while (set != null && set.next()) {
					List<Object> temp = new ArrayList<Object>();
					for (int i = 0; i < colCount; i++) {
						temp.add(set.getObject(i + 1));
					}
					results.add(temp);
				}
			} else {
				while (set != null && set.next()) {
					HashMap<String, Object> temp = new HashMap<String, Object>();
					for (int i = 0; i < colCount; i++) {
						temp.put(rsmd.getColumnName(i + 1).toUpperCase(), set
								.getObject(i + 1));
					}
					results.add(temp);
				}
			}

		} catch (SQLException e) {
			results = null;
			log.error(e);
		} finally {
			closeStatOrSet(set);
			closeStatOrSet(stmt);
		}
		return results;
	}

	public List<Object> query(String sql, int returnType) {
		List<Object> results = new ArrayList<Object>();
		Statement stmt = null;
		ResultSet set = null;
		try {
			stmt = getConnection().createStatement();
			set = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = set.getMetaData();
			int colCount = rsmd.getColumnCount();
			if (returnType == 0) {
				while (set != null && set.next()) {
					List<Object> temp = new ArrayList<Object>();
					for (int i = 0; i < colCount; i++) {
						temp.add(set.getObject(i + 1));
					}
					results.add(temp);
				}
			} else {
				while (set != null && set.next()) {
					HashMap<String, Object> temp = new HashMap<String, Object>();
					for (int i = 0; i < colCount; i++) {
						temp.put(rsmd.getColumnName(i + 1).toUpperCase(), set
								.getObject(i + 1));
					}
					results.add(temp);
				}
			}
			set.close();
			stmt.close();
		} catch (SQLException e) {
			results = null;
			log.error(e);
		} finally {
			closeStatOrSet(set);
			closeStatOrSet(stmt);
		}
		return results;
	}

	public static void closeStatOrSet(Object c) {
		if (c != null) {
			try {
				if (c instanceof Statement) {
					((Statement) c).close();
				}
				if (c instanceof ResultSet) {
					((ResultSet) c).close();
				}
			} catch (SQLException ex) {
				log.error(ex);
			}
		}
	}

	public static void main(String args[]) {
		DbCheck db = new DbCheck();
		System.out.println("test bus!");
		List<?> list = db.query(
				"select  name from `bcuser`.`role` where id = 510000002 ;", 1);
		for (Object l : list) {
			Map<?, ?> map = (Map<?, ?>) l;
			System.out.println(map.get("name".toUpperCase()));
		}
		db.close();
	}
}
