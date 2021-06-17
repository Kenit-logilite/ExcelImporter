package com.excel.dao;

import java.sql.*;
import java.sql.DriverManager;

public class DatabaseConnection {
	public static Connection getCon(){
		Connection con=null;
		try{
			Class.forName("org.postgresql.Driver");
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/excelimport","postgres","Kenit@2000");
			
		}catch(Exception e)
		{
			System.out.println(e);
		}
		return con;
	}
}
