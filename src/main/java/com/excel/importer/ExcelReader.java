package com.excel.importer;

import java.io.File;
import java.io.FileFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow.CellIterator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.excel.dao.DatabaseConnection;



public class ExcelReader implements java.io.FileFilter {
        public boolean accept(File file) {
            return file != null &&
                file.isFile() &&
                file.canRead() &&
                (file.getName().endsWith("xls") || file.getName().endsWith("xlsx"));
        }
        public static void main(String[] args) {

            FileFilter filter = new ExcelFileImporter();
            File directory = new File("C:\\Users\\Kenit\\Desktop\\Git Repo\\ExcelImporter\\src\\main\\resource");
            File[] files = directory.listFiles(filter);

            for (File file: files) {
                System.out.println("File name is " + file.getName());

                // removing Extension of file to keep file name raw
                String name = file.getName();
                if (name.indexOf(".") > 0) {
                    name = name.substring(0, name.lastIndexOf("."));
                }
                try {

                    Connection con = DatabaseConnection.getCon();
                    String sql;
                    sql = "create table " + name + "(" + name + "_id serial primary key" + ");";
                    PreparedStatement ps = con.prepareStatement(sql);
                    int status = ps.executeUpdate();
                    con.close();
                }

                //    catch (SQLException e) {
                //        System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
                //    }
                catch (SQLException e) {
                    System.out.println("Table is Already there please Do not try to insert or overwrite");

                }

                /* Close Database Connection */

                try {
                    Workbook workbook = new XSSFWorkbook(file);

                    Sheet sheet = workbook.getSheetAt(0);
                    Row row = sheet.getRow(0);

                    Iterator iterator = row.cellIterator();

                    while (iterator.hasNext()) {
                        Cell cell = (Cell) iterator.next();
                        //                      System.out.print(cell+" " +" \t");

                        try {

                            // alter table kenit add name varchar(500);
                            Connection con = DatabaseConnection.getCon();
                            String sql = "ALTER TABLE " + name + " ADD " + "" + cell + " varchar(5000)";
                            PreparedStatement ps = con.prepareStatement(sql);
                            int status = ps.executeUpdate();
                            System.out.print("Columns Added Successfully");
                            con.close();
                            
                        }
                           
                          
                            
                        catch (SQLException e) 
                        {

                            System.out.print("Data is Already there please Check :)");
                        }

                    }

                } 
                catch (Exception e) 
                {
                    System.out.println(e);
                }
                
            }}}
            






        