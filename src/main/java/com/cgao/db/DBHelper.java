package com.cgao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {
	private String driverName; // 加载JDBC驱动
	private String dbURL; // 连接服务器和数据库test
	private String userName; // 默认用户名
	private String userPwd; // 密码
	private Connection dbConn = null;
	private ResultSet resultSet = null;
	private ResultSetMetaData rsmd = null;

	public DBHelper(String driverName, String dbURL, String userName, String userPwd) {
		this.driverName = driverName;
		this.dbURL = dbURL;
		this.userName = userName;
		this.userPwd = userPwd;
	}

	public void open() {
		System.out.println("Connecting to DB...");
		try {
			Class.forName(driverName);
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("Connection Successful!"); // 如果连接成功
		} catch (Exception e) {
			System.out.println("Fail to open connection to DB.");
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			dbConn.close();
			dbConn = null;
		} catch (Exception ex) {
			System.out.println("Fail to close connection to DB.");
			System.out.println(ex.getMessage());
			dbConn = null;
		}
	}

	public int executeSQL(String sqlStat) {
		System.out.println("Executing SQL--" + sqlStat);
		int count = 0;
		try {
			PreparedStatement ps = dbConn.prepareStatement(sqlStat, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			resultSet = ps.executeQuery();
			rsmd = resultSet.getMetaData();
			while (resultSet.next()) {
				count = count + 1;
			}
			System.out.println(count + " rows were found");
		} catch (SQLException e) {
			System.out.println("Fail to execute SQL");
			e.printStackTrace();
		}
		return count;
	}

	public int executeInsertSQL(String sqlStat){
		System.out.println("Executing SQL--" + sqlStat);

		int count = 0;
		try {
			PreparedStatement ps = dbConn.prepareStatement(sqlStat);
			
			ps.executeUpdate();
			System.out.println("Executing SQL--sccuess!");
		} catch (SQLException e) {
			System.out.println("Fail to execute SQL");
			e.printStackTrace();
		}
		return count;
	}
	
	
	public List<String> getColumns() {
		System.out.println("Getting Columns--");
		List<String> list = new ArrayList<String>();
		try {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String columnName;

				columnName = rsmd.getColumnLabel(i);
				System.out.println("--Column Name: " + columnName);
				list.add(columnName);
			}
		} catch (SQLException e) {
			System.out.println("Fail to get Columns");
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, String>> getData() {
		System.out.println("Getting Data--");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			resultSet.beforeFirst();
			while (resultSet.next()) {
				Map<String, String> row = new HashMap<String, String>();
				Map<String, String> item = new HashMap<String, String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String columnName = rsmd.getColumnLabel(i);
					Object value = resultSet.getObject(columnName);
					row.put(columnName, String.valueOf(value));
					System.out.println("--" + columnName + ": " + String.valueOf(value));
					item.put(columnName, String.valueOf(value));
				}
				list.add(item);
			}
		} catch (SQLException e) {
			System.out.println("Fail to get data");
			e.printStackTrace();
		}

		return list;
	}

}
