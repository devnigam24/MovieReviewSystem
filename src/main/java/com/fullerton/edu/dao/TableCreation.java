package com.fullerton.edu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableCreation {
	Connection con = null;
	protected void createConnection() {		
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/cpsc476;ifexists=true", "SA", "Passw0rd");
			if (con != null) {
				System.out.println("Connection to DB");
			} else {
				System.out.println("DB Error");
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	public Connection getConnection(){
		return this.con;
	}
	
	protected Boolean userTableExists(){
		try {
			PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS USERS(USERNAME VARCHAR(50) NOT NULL PRIMARY KEY,PASSWORD VARCHAR(50) NOT NULL);");
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	protected Boolean movieTableExists(){
		try {
			PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS MOVIERATING (MOVIENAME VARCHAR(50) NOT NULL PRIMARY KEY,RATING FLOAT NOT NULL);");
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	protected Boolean reviewTableExists(){
		try {
			PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS MOVIEREVIEW (ID INTEGER IDENTITY PRIMARY KEY,MOVIENAME VARCHAR(50) NOT NULL,REVIEW VARCHAR(100) NOT NULL,USERNAME VARCHAR(50) NOT NULL);");
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
