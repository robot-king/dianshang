package com.util.excel;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.util.StringUtil;

public class ExcelUtils {
	/**
	 * 写excel 数据
	 * 
	 * @param sheetName
	 *            sheet 名称
	 * @param head
	 *            头
	 * @param bodyData
	 *            主体数据
	 * @param response
	 * @author wzl
	 * */
	public static void createExcelData(HSSFWorkbook workbook, String sheetName, List head, List bodyData) {
		HSSFSheet sheet = workbook.createSheet(sheetName);
		sheet.setDefaultColumnWidth((short) 15);

		int rowIndex = 0;
		HSSFRow titleRow = sheet.createRow(rowIndex);
		for (int i = 0; i < head.size(); i++) {// 写入head
			HSSFCell cell = titleRow.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString((String) head.get(i));
			cell.setCellValue(text);

		}
		rowIndex++;
		for (int i = 0; i < bodyData.size(); i++) {// 写入内容

			HSSFRow row = sheet.createRow(rowIndex);
			List<Object> oneDataList = (List<Object>) bodyData.get(i);

			for (int j = 0; j < oneDataList.size(); j++) {
				String oneDataVal = String.valueOf(oneDataList.get(j));
				String handelData = "-";
				if (!StringUtil.isEmpty(oneDataVal))
					handelData = oneDataVal.toString();

				HSSFCell cell = row.createCell(j);
				HSSFRichTextString text = new HSSFRichTextString(handelData);
				cell.setCellValue(text);
			}
			rowIndex++;
		}

	}
}
