package com.editsolutions.prd.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.editsolutions.prd.output.FooterSection;
import com.editsolutions.prd.output.HeaderSection;
import com.editsolutions.prd.service.DataHeaderService;
import com.editsolutions.prd.service.DataHeaderServiceImpl;
import com.editsolutions.prd.service.DataService;
import com.editsolutions.prd.service.DataServiceImpl;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.FileTemplateField;
import com.editsolutions.prd.vo.PRDSettings;

public class OutputUtils {
	
	public static byte[] createCSVExtract(PRDSettings prdSettings, DataHeader dataHeader, String fileName) {
		DataHeaderService dataHeaderService = new DataHeaderServiceImpl();
		dataHeader.setTransmissionDate(new java.sql.Date(new Date().getTime()));
		dataHeaderService.updateDataHeader(dataHeader);
		DataService dataService = new DataServiceImpl();
//		List<Object[]> data = dataService.getPayrollDeductionDataForPRD(
//				prdSettings.getPrdSetupPK(), dataHeader.getDataHeaderPK());
		List<Object[]> data = dataService.getPayrollDeductionTableForPRD(
				prdSettings, dataHeader);
		// Get footer if required
		if (prdSettings.getFooterTemplate() != null) {
			FooterSection footerSection = new FooterSection(prdSettings, dataHeader); 
			footerSection.getSection();
			data.add(footerSection.getFooterData());
		}
		String delimiter = prdSettings.getFileTemplate().getDelimiterCT();
		if (delimiter == null) {
			delimiter = ",";
		}

		StringBuilder sb = new StringBuilder();

		// Get header if required
		if (prdSettings.getHeaderTemplate() != null) {
			HeaderSection headerSection = new HeaderSection(prdSettings, dataHeader); 
			sb.append(headerSection.getSection());
		}

		Iterator<Object[]> it = data.iterator();
		while (it.hasNext()) {
			Object[] objects = it.next();
			for (int i = 0; i < objects.length; i++) {
				String field = "";
				if (objects[i] != null) {
				    field = objects[i].toString();
				}
				if (delimiter.equals(",") && (field.contains(",") || field.contains("'"))) {
			        sb.append("\"");	
			        if (prdSettings.isMaskSsn()) {
			            sb.append(maskSsn(field));	
			        } else {
			            sb.append(field);	
			        }
			        sb.append("\"");	
				} else {
                    sb.append(field);
				}
				if (i < (objects.length - 1)) {
                    sb.append(delimiter);
				}
			}
			sb.append(System.getProperty("line.separator"));
		}

		// Get footer if required
		if (prdSettings.getFooterTemplate() != null) {
			FooterSection footerSection = new FooterSection(prdSettings, dataHeader); 
			sb.append(footerSection.getSection());
		}

		byte[] bytes = null;
		try {
			FileUtils.writeStringToFile(new File(fileName), sb.toString(), "UTF-8");
			bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} finally {

		}
		return bytes;

	}
	
	public static XSSFSheet createReviewFile(DataHeader dataHeader, XSSFSheet sheet, CellStyle dateCellStyle) {
		DataHeaderService dataHeaderService = new DataHeaderServiceImpl();
		dataHeader.setTransmissionDate(new java.sql.Date(new Date().getTime()));
		dataHeaderService.updateDataHeader(dataHeader);
		DataService dataService = new DataServiceImpl();
		

		List<Object[]> data = dataService.getPayrollDeductionDataForPRD(
				dataHeader.getPrdSettings().getPrdSetupPK(), dataHeader.getDataHeaderPK());
		System.out.println("data size: " + data.size());
		Iterator<Object[]> it = data.iterator();
		int rowNumber = sheet.getLastRowNum() + 1;
		Row row = sheet.createRow(rowNumber);
		if (rowNumber > 1) {
			// skip header
			if (it.hasNext()) {
			    it.next();
			}
		}
		Cell cell = row.createCell(0);
		int totalNumberOfColumns = 0;
		while (it.hasNext()) {
			Object[] objects = it.next();
			row = sheet.createRow(rowNumber++);
			int cellCount = 0;
			for (int i = 0; i < objects.length; i++) {
				if (totalNumberOfColumns < (i + 1)) {
					totalNumberOfColumns++;
				}
				if (objects[i] instanceof Long) {
					cell = row.createCell(cellCount++);
					cell.setCellValue((Long) objects[i]);
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				} else
				if (objects[i] instanceof String) {
					cell = row.createCell(cellCount++);
//					cell.setCellValue(maskSsn((String) objects[i]));
					cell.setCellValue((String) objects[i]);
					cell.setCellType(Cell.CELL_TYPE_STRING);
				} else
				if (objects[i] instanceof Date) {
					cell = row.createCell(cellCount++);
					cell.setCellValue((Date) objects[i]);
					cell.setCellStyle(dateCellStyle);
				} else
				if (objects[i] instanceof Timestamp) {
					cell = row.createCell(cellCount++);
					cell.setCellValue((Timestamp) objects[i]);
					cell.setCellStyle(dateCellStyle);
				} else
				if (objects[i] instanceof Integer) {
					cell = row.createCell(cellCount++);
					cell.setCellValue((Integer) objects[i]);
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				} else
				if (objects[i] instanceof BigDecimal) {
					cell = row.createCell(cellCount++);
					cell.setCellValue(((BigDecimal) objects[i]).setScale(2,
							BigDecimal.ROUND_HALF_UP).toString());
					// CellStyle style = workbook.createCellStyle();
					// DataFormat format = workbook.createDataFormat();
					// cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					// style.setDataFormat(format.getFormat("0.00"));
					// cell.setCellType(Cell.CELL_TYPE_STRING);
				} else 
				if (objects[i] == null) {
					cell = row.createCell(cellCount++);
					cell.setCellValue("");
					cell.setCellType(Cell.CELL_TYPE_STRING);
				} else {
					System.out.println("TYPE not stated ");
				}
			}

		}

		return sheet;
	}

	public static byte[] createTXTExtract(PRDSettings prdSettings,
			DataHeader dataHeader, String fileName) {

		DataHeaderService dataHeaderService = new DataHeaderServiceImpl();
		dataHeader.setTransmissionDate(new java.sql.Date(new Date().getTime()));
		dataHeaderService.updateDataHeader(dataHeader);

		StringBuilder sb = new StringBuilder(); 

		// get header if required
		if (prdSettings.getHeaderTemplate() != null) {
			HeaderSection headerSection = new HeaderSection(prdSettings, dataHeader); 
			sb.append(headerSection.getSection());
		}
		DataService dataService = new DataServiceImpl();

		List<Object[]> data = dataService.getPayrollDeductionTableForPRD(
				prdSettings, dataHeader);
		Iterator<Object[]> it = data.iterator();

		while (it.hasNext()) {
			Object[] objects = it.next();
			for (int i = 0; i < objects.length; i++) {
				if (prdSettings.isMaskSsn()) {
                    sb.append(maskSsn((String)objects[i]));
				} else {
                    sb.append((String)objects[i]);
				}
			}
			sb.append(System.getProperty("line.separator"));
		}
		// Get footer if required
		if (prdSettings.getFooterTemplate() != null) {
			FooterSection footerSection = new FooterSection(prdSettings, dataHeader); 
			sb.append(footerSection.getSection());
		}

		byte[] bytes = null;
		try {
			FileUtils.writeStringToFile(new File(fileName), sb.toString(), "UTF-8");
			bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} finally {

		}
		return bytes;

	}

	public static byte[] createXLSExtract(PRDSettings prdSettings,
			DataHeader dataHeader, String fileName) {
		//SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		DataHeaderService dataHeaderService = new DataHeaderServiceImpl();
		dataHeader.setTransmissionDate(new java.sql.Date(new Date().getTime()));
		dataHeaderService.updateDataHeader(dataHeader);

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();

		CellStyle dateCellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat(
				"mm-dd-yyyy"));

		DataService dataService = new DataServiceImpl();

//		List<Object[]> data = dataService.getPayrollDeductionDataForPRD(
//				prdSettings.getPrdSetupPK(), dataHeader.getDataHeaderPK());
		List<Object[]> data = dataService.getPayrollDeductionTableForPRD(
				prdSettings, dataHeader);

		// Get footer if required
		if (prdSettings.getFooterTemplate() != null) {
			FooterSection footerSection = new FooterSection(prdSettings, dataHeader); 
			footerSection.getSection();
			data.add(footerSection.getFooterData());
		}
		Iterator<Object[]> it = data.iterator();

		int rowNumber = 0;
		//Row  row = sheet..createRow(rowNumber++);
		Row  row = null;
		Cell cell = null;
		//Cell cell = row.createCell(0);
		// Get header if required
		//if (prdSettings.getHeaderTemplate() != null) {
		//	HeaderSection headerSection = new HeaderSection(prdSettings, dataHeader); 
		//	cell.setCellValue(headerSection.getSection().toString());
		//	cell.setCellType(Cell.CELL_TYPE_STRING);
		//}
		int totalNumberOfColumns = 0;
		while (it.hasNext()) {
			Object[] objects = it.next();
			row = sheet.createRow(rowNumber++);
			int cellCount = 0;
			for (int i = 0; i < objects.length; i++) {
				if (totalNumberOfColumns < (i + 1)) {
					totalNumberOfColumns++;
				}
				if (objects[i] instanceof Long) {
					cell = row.createCell(cellCount++);
					cell.setCellValue((Long) objects[i]);
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				} else
				if (objects[i] instanceof String) {
					cell = row.createCell(cellCount++);
					if (prdSettings.isMaskSsn()) {
					    cell.setCellValue(maskSsn((String)objects[i]));
					} else {
					    cell.setCellValue((String)objects[i]);
					}
					cell.setCellType(Cell.CELL_TYPE_STRING);
				} else
				if (objects[i] instanceof Date) {
					cell = row.createCell(cellCount++);
					cell.setCellValue((Date) objects[i]);
					cell.setCellStyle(dateCellStyle);
				} else
				if (objects[i] instanceof Timestamp) {
					cell = row.createCell(cellCount++);
					cell.setCellValue((Timestamp) objects[i]);
					cell.setCellStyle(dateCellStyle);
				} else
				if (objects[i] instanceof Integer) {
					cell = row.createCell(cellCount++);
					cell.setCellValue((Integer) objects[i]);
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				} else
				if (objects[i] instanceof BigDecimal) {
					cell = row.createCell(cellCount++);
					cell.setCellValue(((BigDecimal) objects[i]).setScale(2,
							BigDecimal.ROUND_HALF_UP).toString());
					// CellStyle style = workbook.createCellStyle();
					// DataFormat format = workbook.createDataFormat();
					// cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					// style.setDataFormat(format.getFormat("0.00"));
					// cell.setCellType(Cell.CELL_TYPE_STRING);
				} else 
				if (objects[i] == null) {
					cell = row.createCell(cellCount++);
					cell.setCellValue("");
					cell.setCellType(Cell.CELL_TYPE_STRING);
				} else {
					System.out.println("TYPE not stated ");
				}
			}

		}


		// Autosize columns
		for (int c = 0; c < totalNumberOfColumns; c++) {
			sheet.autoSizeColumn(c + 1);
		}

		FileOutputStream out;
		byte[] bytes = null;
		try {
			File file = new File(fileName);
			out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			bytes = com.google.common.io.Files.toByteArray(file);
			workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} finally {

		}
		return bytes;

	}
	
	public static String maskSsn(String ssn) {
		String regex = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";

		String regexNoDashes = "^(?!219099999|078051120)(?!666|000|9\\d{2})\\d{3}(?!00)\\d{2}(?!0{4})\\d{4}$";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(ssn);
		if(matcher.matches()) {
			ssn = "XXX-XX" + ssn.substring(6);	
		} else {
			pattern = Pattern.compile(regexNoDashes);
			matcher = pattern.matcher(ssn);
		    if(matcher.matches()) {
		    	ssn = "XXXXX" + ssn.substring(5);	
		    }
		}
		return ssn;
	}
	
	public static String padField(String value, FileTemplateField fileTemplateField) {
		// if value same length as field length no reason to pad. Just return.
		if (value.length() == fileTemplateField.getFieldLength()) {
			return value;
		}
		if (value.length() > fileTemplateField
				.getFieldLength()) {
			value = value.substring(0,
					fileTemplateField.getFieldLength() - 1);
		}
		// append spaces to reach specified field length for
		// fixed length file
		value = String.format(
				"%1$-" + fileTemplateField.getFieldLength()
						+ "s", value);
		return value;
	}

}
