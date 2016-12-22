package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Fidele;

public class DataCollector {
	
	public static void copyToDb(){
		 try {			 
			 	File myFile = new File("D:\\WORK\\2016\\git\\gestFideles\\WebContent\\fideles.xlsx");
	            FileInputStream fis = new FileInputStream(myFile);
	            
	            // Finds the workbook instance for XLSX file
	            XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
	
	            // Return first sheet from the XLSX workbook
	            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
	            
	            Row row;
	            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	            
	            for(int i=1; i<=mySheet.getLastRowNum(); i++){
		             row = mySheet.getRow(i);
		             
		             String dobInString = row.getCell(0).getStringCellValue();
		             Date dob = formatter.parse(dobInString);
		             
		             String lieu_naissance = row.getCell(1).getStringCellValue();
		             String nom = row.getCell(2).getStringCellValue();
		             
		             Fidele f = new Fidele(dob, lieu_naissance, nom, "xls", "xls", "xls", "xls", "xls", "xls", "xls", "xls", "xls", "xls", "xls");
		             OperationsDb.persistObject(f);
		             System.out.println("Import rows "+i);
	            }
	            
		         System.out.println("Success import excel to sqlite table");
		
	            myWorkBook.close();
	
		 } catch (Exception e) {
				e.printStackTrace();
			}
		 
	}
}
