# db2excel
This is a tool to help turn a SQL statement result into a Excel file 


这是一个简单的JAVA POI的一个应用， 用于将一条SQL语句返回的结果集填入一张Excel表格文件中。

目前测试通过了MySQL, SQLServer, 其他数据库暂时没有测试。

# 使用方法
  1. 创建一个Export2Excel类的实例,并带入driverName, dbURL, userName, userPwd 这四个参数。
  2. 调用Export2Excel.setSql(String Sql) 方法，传入SQL语句
  3. 调用Export2Excel.doExport(String fileName, String sheetName)方法, 导出Excel文件, 并返回操作结果Code:RESULT_FAIL(-1), RESULT_NO_RECORD_FOUND(0), RESULT_SUCCESS(1)
