package com.cgao.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelProcessor {
	public String fileName;
	private Workbook wb;
	private OutputStream os;
	
	public ExcelProcessor(){
		//initial exports folder
		File file = new File(".//exports");
		if(!file.exists()){
			file.mkdir();
		}
		
		
	}

	public void export(String fileName, String sheetName, List<String> columnList, List<Map<String, String>> list) throws IOException {
		this.fileName = fileName;
		// 创建Workbook对象（这一个对象代表着对应的一个Excel文件）
		// HSSFWorkbook表示以xls为后缀名的文件
		wb = new HSSFWorkbook();
		// 创建Sheet并给名字(表示Excel的一个Sheet)
		Sheet sheet = wb.createSheet(sheetName);
		// Row表示一行Cell表示一列
		Row row = null;
		Cell cell = null;

		int columnCount = columnList.size();

		row = sheet.createRow(0);
		for (int i = 0; i < columnCount; i++) {
			cell = row.createCell(i);
			cell.setCellValue(columnList.get(i));
		}

		for (int j = 0; j < list.size(); j++) {
			int rowNumber = j + 1;
			row = sheet.createRow(rowNumber);

			for (int k = 0; k < columnCount; k++) {
				cell = row.createCell(k);
				Map<String, String> item = list.get(j);
				cell.setCellValue(item.get(columnList.get(k)));
			}
		}
		//输出  
		os = new FileOutputStream(new File(".//exports//" + fileName));  
		wb.write(os);  
		os.flush();
		os.close(); 
	    System.out.println("......File " +fileName+ " Exported.");

	}
}
