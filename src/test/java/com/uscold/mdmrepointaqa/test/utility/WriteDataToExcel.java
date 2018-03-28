package com.uscold.mdmrepointaqa.test.utility;

//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.hssf.util.HSSFColor;

import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ********************************************
 * * Created by ${"Robert Morales"} on 3/6/2018.
 * ********************************************
 **/
public class WriteDataToExcel {

    public FileInputStream fis = null;
    public FileOutputStream fos = null;
    public XSSFWorkbook workbook = null;
    public XSSFSheet sheet = null;
    public XSSFRow row = null;
    public XSSFCell cell = null;
    String xlFilePath;
    //int rowcount ="";

    public WriteDataToExcel(String xlFilePath) throws Exception {
        this.xlFilePath = xlFilePath;
        fis = new FileInputStream(xlFilePath);
        workbook = new XSSFWorkbook(fis);
        fis.close();
    }

    public boolean setCellData(String sheetName, String colName, String value,String type, String type2, String type3,String status) throws Exception {
        try {
            FileInputStream fis = new FileInputStream(xlFilePath);
            FileOutputStream fos = null;
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = null;
            XSSFCell cell = null;
            XSSFFont font = workbook.createFont();
            XSSFCellStyle style = workbook.createCellStyle();


            int rowcount = sheet.getLastRowNum();
            System.out.println("Line 65 - Total Number of rows present in the sheet: " + rowcount);
            int rowcounts = sheet.getLastRowNum() + 1;
            System.out.println("Line 67 - Total Number of rows present in the sheet: " + rowcounts);


            int colcount = sheet.getRow(0).getLastCellNum();
            System.out.println("Line 69 - Total Number of columns present in the sheet: " + colcount);

            //get data from sheet by iterating thru-out the columns from left to right
            for (int i = 0; i <= colcount; i++) {
                XSSFCell celll = sheet.getRow(0).getCell(i);
                String celltext = "";
                System.out.println("Line 79 - This value was found on column " + i + " - Actual value: " + celll);


                //int col_Num = sheet.getLastRowNum(i);
                if (celll.getStringCellValue().trim().equals(colName)) {

//                    System.out.println("line 85 - After the left to right loop, this value was found: " + rowcounts);
//                    System.out.println("line - 87 - This value :" + value + " should be inserted into this empty row: " + celll + " in column: " + i);
//                    System.out.println("Line 111 - " + colcount + " rows were found");
//                    System.out.println("Line 112 - " + i + " columns were found");

                    //Create First blank row and update with customer number, status, and timestampt
                    XSSFRow row1 = sheet.createRow(rowcounts);

                    //Update the customer number
                    XSSFCell r1c1 = row1.createCell(i);
                    r1c1.setCellValue(value);

                    //Update type
                    XSSFCell r1c2 = row1.createCell(i + 1);
                    r1c2.setCellValue(type);

                    //Update type2
                    XSSFCell r1c3 = row1.createCell(i + 2);
                    r1c3.setCellValue(type2);

                    //Update type2
                    XSSFCell r1c4 = row1.createCell(i + 3);
                    r1c4.setCellValue(type3);

                    //Update status
                    XSSFCell r1c5 = row1.createCell(i + 4);
                    r1c5.setCellValue(status);

                    //Generate TS to update TS on the excel document
                    Calendar dateTwo = Calendar.getInstance();
                    Date dateOne = dateTwo.getTime();
                    DateFormat dateForm = new SimpleDateFormat("YYMMddhhmmss");
                    String tDay = dateForm.format(dateOne);

                    //Update the TS based on the TS from above
                    XSSFCell r1c6 = row1.createCell(i + 5);
                    r1c6.setCellValue("20" + tDay);

                    fos = new FileOutputStream(xlFilePath);
                    workbook.write(fos);
                    fos.close();
                    break;
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}
