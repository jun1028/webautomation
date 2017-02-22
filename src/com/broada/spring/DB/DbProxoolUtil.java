package com.broada.spring.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.broada.spring.DB.DbConnectionNoContainer;

public class DbProxoolUtil {
	private static Log log = LogFactory.getLog(DbProxoolUtil.class);

	/**
	 * 执行sql更新语句
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            语句 sql
	 * @return int 成功:返回更新的记录数目;失败:-1
	 **/
	public static int update(String sql) {
		Connection connection = null;
		Statement SQLStament = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.createStatement();
			return SQLStament.executeUpdate(sql);
		} catch (SQLException e) {
			log.error("更新数据库表记录时发生异常,SQL语句为：" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("更新数据库表记录时发生异常,SQL语句为：" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * 执行sql删除语句
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            语句
	 * @return int 成功:返回删除的记录数目;失败:-1
	 **/
	public static int delete(String sql) {
		Connection connection = null;
		Statement SQLStament = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.createStatement();
			return SQLStament.executeUpdate(sql);
		} catch (SQLException e) {
			log.error("删除数据库表记录时发生异常,SQL语句为：" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("删除数据库表时记录发生异常,SQL语句为：" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * 执行sql插入语句
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            语句 sql
	 * @return int 成功:返回插入的记录数目;失败:-1
	 **/
	public static int insert(String sql) {
		Connection connection = null;
		Statement SQLStament = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.createStatement();
			return SQLStament.executeUpdate(sql);
		} catch (SQLException e) {
			log.error("插入数据库表记录时发生异常,SQL语句为：" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("插入数据库表时记录发生异常,SQL语句为：" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * 批量执行sql插入语句
	 * 
	 * @since 2013.06.25
	 * @param sqlList
	 *            语句集 sqlList
	 * @return int 成功:返回插入的记录数目;失败:-1。一条插入失败所有都失败
	 **/
	public static int batchInsert(List<String> sqlList) {
		Connection connection = null;
		Statement SQLStament = null;
		try {

			connection = DbConnectionNoContainer.getConnection(false);
			SQLStament = connection.createStatement();
			for (String sql : sqlList) {
				SQLStament.executeUpdate(sql);
			}
			connection.commit();
			return sqlList.size();
		} catch (SQLException e) {
			DbConnectionNoContainer.rollback(connection);
			log.error("批量插入数据库表时发生异常", e);
			return -1;
		} catch (Exception e) {
			DbConnectionNoContainer.rollback(connection);
			log.error("批量插入数据库表时发生异常", e);
			return -1;
		}

		finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * 执行sql查询语句
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            语句
	 * @param returnType
	 *            返回结果集类型,0:返回list结果集;1:返回hashmap结果集;其他同0 returnType
	 * @return List<Object> 查询结果集
	 *         resultList元素为list<value>或HashMap<ColName,value>
	 **/
	public static List<Object> query(String sql, int returnType) {
		Connection connection = null;
		Statement SQLStament = null;
		ResultSet set = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			// log.error("12334");
			SQLStament = connection.createStatement();
			set = SQLStament.executeQuery(sql);

			ResultSetMetaData rsmd = set.getMetaData();
			int colCount = rsmd.getColumnCount();
			List<Object> resultList = new ArrayList<Object>();
			if (returnType == 0) {
				while (set != null && set.next()) {
					List<Object> temp = new ArrayList<Object>();
					for (int i = 0; i < colCount; i++) {
						temp.add(set.getObject(i + 1));
					}
					resultList.add(temp);
				}
			} else {
				while (set != null && set.next()) {
					HashMap<String, Object> temp = new HashMap<String, Object>();
					for (int i = 0; i < colCount; i++) {
						temp.put(rsmd.getColumnName(i + 1).toUpperCase(), set
								.getObject(i + 1));
					}
					resultList.add(temp);
				}
			}
			return resultList;
		} catch (SQLException e) {
			log.error("查询数据库表时发生异常,SQL语句为：" + sql, e);
			return null;
		} catch (Exception e) {
			log.error("查询数据库表时发生异常,SQL语句为：" + sql, e);
			return null;
		} finally {
			// System.out.println("1233ww4");
			DbConnectionNoContainer.closeResultSet(set);
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * 执行sql查询语句
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            语句 sql
	 * @param returnType
	 *            返回结果集类型,0:返回list结果集;1:返回hashmap结果集;其他同0 returnType
	 * @return List<Object> 查询结果集
	 *         resultList元素为list<value>或HashMap<ColName,value>
	 **/
	public static List<Object> query(String sql, int returnType, String parm) {
		Connection connection = null;
		PreparedStatement SQLStament = null;
		ResultSet set = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.prepareStatement(sql);
			SQLStament.setString(1, parm);
			set = SQLStament.executeQuery();

			ResultSetMetaData rsmd = set.getMetaData();
			int colCount = rsmd.getColumnCount();
			List<Object> resultList = new ArrayList<Object>();
			if (returnType == 0) {
				while (set != null && set.next()) {
					List<Object> temp = new ArrayList<Object>();
					for (int i = 0; i < colCount; i++) {
						temp.add(set.getObject(i + 1));
					}
					resultList.add(temp);
				}
			}

			return resultList;
		} catch (SQLException e) {
			log.error("查询数据库表时发生异常,SQL语句为：" + sql, e);
			return null;
		} catch (Exception e) {
			log.error("查询数据库表时发生异常,SQL语句为：" + sql, e);
			return null;
		} finally {
			DbConnectionNoContainer.closeResultSet(set);
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * 执行sql查询语句
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            语句 sql
	 * @param returnType
	 *            返回结果集类型,0:返回list结果集;1:返回hashmap结果集;其他同0 returnType
	 * @return List<Object> 查询结果集
	 *         resultList元素为list<value>或HashMap<ColName,value>
	 **/
	public static List<Object> query(String sql, int returnType, int parm) {
		Connection connection = null;
		PreparedStatement SQLStament = null;
		ResultSet set = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.prepareStatement(sql);
			SQLStament.setInt(1, parm);
			set = SQLStament.executeQuery();

			ResultSetMetaData rsmd = set.getMetaData();
			int colCount = rsmd.getColumnCount();
			List<Object> resultList = new ArrayList<Object>();
			if (returnType == 0) {
				while (set != null && set.next()) {
					List<Object> temp = new ArrayList<Object>();
					for (int i = 0; i < colCount; i++) {
						temp.add(set.getObject(i + 1));
					}
					resultList.add(temp);
				}
			}

			return resultList;
		} catch (SQLException e) {
			log.error("查询数据库表时发生异常,SQL语句为：" + sql, e);
			return null;
		} catch (Exception e) {
			log.error("查询数据库表时发生异常,SQL语句为：" + sql, e);
			return null;
		} finally {
			DbConnectionNoContainer.closeResultSet(set);
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * 执行sql查询语句
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            语句 sql
	 * @param returnType
	 *            返回结果集类型,0:返回list结果集;1:返回hashmap结果集;其他同0 returnType
	 * @return List<Object> 查询结果集
	 *         resultList元素为list<value>或HashMap<ColName,value>
	 **/
	public static List<Object> query(String sql, int returnType, Object x,
			Object y) {
		Connection connection = null;
		PreparedStatement SQLStament = null;
		ResultSet set = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.prepareStatement(sql);
			SQLStament.setObject(1, x);
			SQLStament.setObject(2, y);
			set = SQLStament.executeQuery();

			ResultSetMetaData rsmd = set.getMetaData();
			int colCount = rsmd.getColumnCount();
			List<Object> resultList = new ArrayList<Object>();
			if (returnType == 0) {
				while (set != null && set.next()) {
					List<Object> temp = new ArrayList<Object>();
					for (int i = 0; i < colCount; i++) {
						temp.add(set.getObject(i + 1));
					}
					resultList.add(temp);
				}
			}

			return resultList;
		} catch (SQLException e) {
			log.error("查询数据库表时发生异常,SQL语句为：" + sql, e);
			return null;
		} catch (Exception e) {
			log.error("查询数据库表时发生异常,SQL语句为：" + sql, e);
			return null;
		} finally {
			DbConnectionNoContainer.closeResultSet(set);
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * 执行sql查询语句
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            语句 sql
	 * @param list
	 *            返回结果集类型,0:返回list结果集;1:返回hashmap结果集;其他同0 returnType
	 * @return 查询结果集 resultList元素为list<value>或HashMap<ColName,value>
	 **/
	public static List<Object> query(String sql, int returnType, List list) {
		Connection connection = null;
		PreparedStatement SQLStament = null;
		ResultSet set = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				SQLStament.setString(i + 1, list.get(i) == null ? "" : list
						.get(i).toString());
			}
			set = SQLStament.executeQuery();

			ResultSetMetaData rsmd = set.getMetaData();
			int colCount = rsmd.getColumnCount();
			List<Object> resultList = new ArrayList<Object>();
			if (returnType == 0) {
				while (set != null && set.next()) {
					List<Object> temp = new ArrayList<Object>();
					for (int i = 0; i < colCount; i++) {
						temp.add(set.getObject(i + 1));
					}
					resultList.add(temp);
				}
			}

			return resultList;
		} catch (SQLException e) {
			log.error("查询数据库表时发生异常,SQL语句为：" + sql, e);
			return null;
		} catch (Exception e) {
			log.error("查询数据库表时发生异常,SQL语句为：" + sql, e);
			return null;
		} finally {
			DbConnectionNoContainer.closeResultSet(set);
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * 获取当前可插入表中的记录编号
	 * 
	 * @since 2013.06.25
	 * @param table
	 *            表名
	 * @param idName
	 *            记录主键
	 * @param baseId
	 *            记录基本值
	 * @return 对于原来记录主键为按数字型排序的返回当前最大记录+1，如果为-1表示不能获取最大记录
	 * */
	public static int getId(String table, String idName, int baseId) {
		if (table == null || table.equals("") || idName == null
				|| idName.equals("")) {
			return -1;
		}

		Connection connection = null;
		Statement SQLStament = null;
		ResultSet set = null;
		String sql = null;
		try {
			sql = "SELECT MAX(" + idName + ") tempvalue FROM " + table;
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.createStatement();
			set = SQLStament.executeQuery(sql);
			if (set != null && set.next()) {
				return Integer.parseInt(set.getString("tempvalue")) + 1;
			}
			return baseId + 1;
		} catch (Exception e) {
			log.error("获取当前可插入表中的记录编号发生异常,SQL语句为：" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeResultSet(set);
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * 从数据库查询到的结果集中获取指定列的值
	 * 
	 * @since 2013.06.25
	 * @param resultList
	 *            查询结果list
	 * @param rowNum
	 *            第几条数据
	 * @param colName
	 *            列的名称
	 * @return value 列的值
	 **/
	public static Object getValue(List resultList, int rowNum, String colName) {
		try {
			HashMap<String, Object> tempMap = (HashMap<String, Object>) resultList
					.get(rowNum);
			return tempMap.get(colName.toUpperCase());
		} catch (Exception e) {
			log.error("从数据库查询到的结果集中获取指定列的值发生异常，结果集size[" + resultList.size()
					+ "]请求行[" + rowNum + "]请求列" + colName, e);
			return null;
		}
	}

	/**
	 * 从数据库查询到的结果集中获取指定列的值
	 * 
	 * @since 2013.06.25
	 * @param resultList
	 *            查询结果list
	 * @param rowNum
	 *            第几条数据
	 * @param colNum
	 *            第几列
	 * @return value 列的值
	 **/
	public static Object getValue(List resultList, int rowNum, int colNum) {
		try {
			ArrayList<String> tempList = (ArrayList) resultList.get(rowNum);
			return tempList.get(colNum);
		} catch (Exception e) {
			log.error("从数据库查询到的结果集中获取指定列的值发生异常，结果集size[" + resultList.size()
					+ "]请求行[" + rowNum + "]请求列" + colNum, e);
			return null;
		}
	}

	/**
	 * 从数据库查询到的结果集中获取指定列的值
	 * 
	 * @since 2013.06.25
	 * @param resultList
	 *            查询结果list
	 * @param colNum
	 *            第几列
	 * @return value 列的值
	 **/
	public static String getValue(List resultList, int colNum) {
		try {
			return getValue(resultList, 0, colNum) == null ? "" : getValue(
					resultList, 0, colNum).toString();
		} catch (Exception e) {
			log.error("从数据库查询到的结果集中获取指定列的值发生异常，结果集size[" + resultList.size()
					+ "]请求列" + colNum, e);
			return null;
		}
	}

	public static int preparedInsert(String sql, List list) {
		Connection connection = null;
		PreparedStatement SQLStament = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				SQLStament.setObject(i + 1, list.get(i) == null ? "" : list
						.get(i));
			}
			return SQLStament.executeUpdate();
		} catch (SQLException e) {
			log.error("插入数据库表时发生异常,SQL语句为：" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("插入数据库表时发生异常,SQL语句为：" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	public static int preparedupdate(String sql, List list, String key) {
		Connection connection = null;
		PreparedStatement SQLStament = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				SQLStament.setObject(i + 1, list.get(i) == null ? "" : list
						.get(i));
			}
			SQLStament.setObject(list.size() + 1, key);
			return SQLStament.executeUpdate();
		} catch (SQLException e) {
			log.error("更新数据库表时发生异常,SQL语句为：" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("更新数据库表时发生异常,SQL语句为：" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	// 增加提交是多个的更新操作
	public static int preparedupdate(String sql, List list, List keyValue) {
		Connection connection = null;
		PreparedStatement SQLStament = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				SQLStament.setObject(i + 1, list.get(i) == null ? "" : list
						.get(i));
			}
			for (int j = 0; j < keyValue.size(); j++) {
				SQLStament.setObject(list.size() + j + 1,
						keyValue.get(j) == null ? "" : keyValue.get(j));
			}
			return SQLStament.executeUpdate();
		} catch (SQLException e) {
			log.error("更新数据库表时发生异常,SQL语句为：" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("更新数据库表时发生异常,SQL语句为：" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	public static int preparedupdate(String sql, String key) {
		Connection connection = null;
		PreparedStatement SQLStament = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.prepareStatement(sql);
			SQLStament.setString(1, key);
			return SQLStament.executeUpdate();
		} catch (SQLException e) {
			log.error("更新数据库表时发生异常,SQL语句为：" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("更新数据库表时发生异常,SQL语句为：" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}
}
