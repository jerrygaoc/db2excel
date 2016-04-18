package com.cgao.db2excel;

import java.util.List;
import java.util.Map;

import com.cgao.db.DBHelper;
import com.cgao.poi.ExcelProcessor;
/*
 * Export2Excel  
 * 
 * @author: cgao 
 */
public class Export2Excel {
	public static final int RESULT_SUCCESS = 1;
	public static final int RESULT_NO_RECORD_FOUND = 0;
	public static final int RESULT_FAIL = -1;
	public String driverName = "";
	public String dbURL = "";
	public String userName = "";
	public String userPwd = "";
	public String sql = "";
	/** 
	 * 实例Export2Excel 
	 *  
	 * @param driverName 加载JDBC驱动
	 * @param dbURL 连接服务器和数据库
	 * @param userName 用户名
	 * @param userPwd 密码
	 * 
	 */
	public Export2Excel(String driverName, String dbURL, String userName, String userPwd) {
		this.driverName = driverName;
		this.dbURL = dbURL;
		this.userName = userName;
		this.userPwd = userPwd;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	/** 
	 * 导出Excel文件 
	 *  
	 * @param fileName 文件名 
	 * @param sheetName 表名
	 * @return int -1:RESULT_FAIL, 0:RESULT_NO_RECORD_FOUND, 1:RESULT_SUCCESS
	 */ 
	public int doExport(String fileName, String sheetName){
		int exportResult = this.RESULT_SUCCESS;
		try {
			DBHelper dbHelper = new DBHelper(driverName, dbURL, userName, userPwd);		

			dbHelper.open();
			
			int resultCount = dbHelper.executeSQL(sql);

			if (resultCount > 0) {
				List<String> columnList = dbHelper.getColumns();
				List<Map<String, String>> dataList = dbHelper.getData();

				ExcelProcessor ep = new ExcelProcessor();

				ep.export(fileName, sheetName, columnList, dataList);
				
			}else{
				exportResult = this.RESULT_NO_RECORD_FOUND;
			}
			dbHelper.close();
		} catch (Exception e) {
			exportResult = this.RESULT_FAIL;
			e.printStackTrace();
		}		
		
		return exportResult;
		
	}
	
	
}
