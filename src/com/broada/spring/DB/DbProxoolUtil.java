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
	 * ִ��sql�������
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            ��� sql
	 * @return int �ɹ�:���ظ��µļ�¼��Ŀ;ʧ��:-1
	 **/
	public static int update(String sql) {
		Connection connection = null;
		Statement SQLStament = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.createStatement();
			return SQLStament.executeUpdate(sql);
		} catch (SQLException e) {
			log.error("�������ݿ���¼ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("�������ݿ���¼ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * ִ��sqlɾ�����
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            ���
	 * @return int �ɹ�:����ɾ���ļ�¼��Ŀ;ʧ��:-1
	 **/
	public static int delete(String sql) {
		Connection connection = null;
		Statement SQLStament = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.createStatement();
			return SQLStament.executeUpdate(sql);
		} catch (SQLException e) {
			log.error("ɾ�����ݿ���¼ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("ɾ�����ݿ��ʱ��¼�����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * ִ��sql�������
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            ��� sql
	 * @return int �ɹ�:���ز���ļ�¼��Ŀ;ʧ��:-1
	 **/
	public static int insert(String sql) {
		Connection connection = null;
		Statement SQLStament = null;
		try {
			connection = DbConnectionNoContainer.getConnection(true);
			SQLStament = connection.createStatement();
			return SQLStament.executeUpdate(sql);
		} catch (SQLException e) {
			log.error("�������ݿ���¼ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("�������ݿ��ʱ��¼�����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * ����ִ��sql�������
	 * 
	 * @since 2013.06.25
	 * @param sqlList
	 *            ��伯 sqlList
	 * @return int �ɹ�:���ز���ļ�¼��Ŀ;ʧ��:-1��һ������ʧ�����ж�ʧ��
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
			log.error("�����������ݿ��ʱ�����쳣", e);
			return -1;
		} catch (Exception e) {
			DbConnectionNoContainer.rollback(connection);
			log.error("�����������ݿ��ʱ�����쳣", e);
			return -1;
		}

		finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * ִ��sql��ѯ���
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            ���
	 * @param returnType
	 *            ���ؽ��������,0:����list�����;1:����hashmap�����;����ͬ0 returnType
	 * @return List<Object> ��ѯ�����
	 *         resultListԪ��Ϊlist<value>��HashMap<ColName,value>
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
			log.error("��ѯ���ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return null;
		} catch (Exception e) {
			log.error("��ѯ���ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return null;
		} finally {
			// System.out.println("1233ww4");
			DbConnectionNoContainer.closeResultSet(set);
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * ִ��sql��ѯ���
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            ��� sql
	 * @param returnType
	 *            ���ؽ��������,0:����list�����;1:����hashmap�����;����ͬ0 returnType
	 * @return List<Object> ��ѯ�����
	 *         resultListԪ��Ϊlist<value>��HashMap<ColName,value>
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
			log.error("��ѯ���ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return null;
		} catch (Exception e) {
			log.error("��ѯ���ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return null;
		} finally {
			DbConnectionNoContainer.closeResultSet(set);
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * ִ��sql��ѯ���
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            ��� sql
	 * @param returnType
	 *            ���ؽ��������,0:����list�����;1:����hashmap�����;����ͬ0 returnType
	 * @return List<Object> ��ѯ�����
	 *         resultListԪ��Ϊlist<value>��HashMap<ColName,value>
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
			log.error("��ѯ���ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return null;
		} catch (Exception e) {
			log.error("��ѯ���ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return null;
		} finally {
			DbConnectionNoContainer.closeResultSet(set);
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * ִ��sql��ѯ���
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            ��� sql
	 * @param returnType
	 *            ���ؽ��������,0:����list�����;1:����hashmap�����;����ͬ0 returnType
	 * @return List<Object> ��ѯ�����
	 *         resultListԪ��Ϊlist<value>��HashMap<ColName,value>
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
			log.error("��ѯ���ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return null;
		} catch (Exception e) {
			log.error("��ѯ���ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return null;
		} finally {
			DbConnectionNoContainer.closeResultSet(set);
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * ִ��sql��ѯ���
	 * 
	 * @since 2013.06.25
	 * @param sql
	 *            ��� sql
	 * @param list
	 *            ���ؽ��������,0:����list�����;1:����hashmap�����;����ͬ0 returnType
	 * @return ��ѯ����� resultListԪ��Ϊlist<value>��HashMap<ColName,value>
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
			log.error("��ѯ���ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return null;
		} catch (Exception e) {
			log.error("��ѯ���ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return null;
		} finally {
			DbConnectionNoContainer.closeResultSet(set);
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * ��ȡ��ǰ�ɲ�����еļ�¼���
	 * 
	 * @since 2013.06.25
	 * @param table
	 *            ����
	 * @param idName
	 *            ��¼����
	 * @param baseId
	 *            ��¼����ֵ
	 * @return ����ԭ����¼����Ϊ������������ķ��ص�ǰ����¼+1�����Ϊ-1��ʾ���ܻ�ȡ����¼
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
			log.error("��ȡ��ǰ�ɲ�����еļ�¼��ŷ����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeResultSet(set);
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	/**
	 * �����ݿ��ѯ���Ľ�����л�ȡָ���е�ֵ
	 * 
	 * @since 2013.06.25
	 * @param resultList
	 *            ��ѯ���list
	 * @param rowNum
	 *            �ڼ�������
	 * @param colName
	 *            �е�����
	 * @return value �е�ֵ
	 **/
	public static Object getValue(List resultList, int rowNum, String colName) {
		try {
			HashMap<String, Object> tempMap = (HashMap<String, Object>) resultList
					.get(rowNum);
			return tempMap.get(colName.toUpperCase());
		} catch (Exception e) {
			log.error("�����ݿ��ѯ���Ľ�����л�ȡָ���е�ֵ�����쳣�������size[" + resultList.size()
					+ "]������[" + rowNum + "]������" + colName, e);
			return null;
		}
	}

	/**
	 * �����ݿ��ѯ���Ľ�����л�ȡָ���е�ֵ
	 * 
	 * @since 2013.06.25
	 * @param resultList
	 *            ��ѯ���list
	 * @param rowNum
	 *            �ڼ�������
	 * @param colNum
	 *            �ڼ���
	 * @return value �е�ֵ
	 **/
	public static Object getValue(List resultList, int rowNum, int colNum) {
		try {
			ArrayList<String> tempList = (ArrayList) resultList.get(rowNum);
			return tempList.get(colNum);
		} catch (Exception e) {
			log.error("�����ݿ��ѯ���Ľ�����л�ȡָ���е�ֵ�����쳣�������size[" + resultList.size()
					+ "]������[" + rowNum + "]������" + colNum, e);
			return null;
		}
	}

	/**
	 * �����ݿ��ѯ���Ľ�����л�ȡָ���е�ֵ
	 * 
	 * @since 2013.06.25
	 * @param resultList
	 *            ��ѯ���list
	 * @param colNum
	 *            �ڼ���
	 * @return value �е�ֵ
	 **/
	public static String getValue(List resultList, int colNum) {
		try {
			return getValue(resultList, 0, colNum) == null ? "" : getValue(
					resultList, 0, colNum).toString();
		} catch (Exception e) {
			log.error("�����ݿ��ѯ���Ľ�����л�ȡָ���е�ֵ�����쳣�������size[" + resultList.size()
					+ "]������" + colNum, e);
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
			log.error("�������ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("�������ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
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
			log.error("�������ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("�������ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}

	// �����ύ�Ƕ���ĸ��²���
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
			log.error("�������ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("�������ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
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
			log.error("�������ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} catch (Exception e) {
			log.error("�������ݿ��ʱ�����쳣,SQL���Ϊ��" + sql, e);
			return -1;
		} finally {
			DbConnectionNoContainer.closeStatement(SQLStament);
			DbConnectionNoContainer.releaseConnection(connection);
		}
	}
}
