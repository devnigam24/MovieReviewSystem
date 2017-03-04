package com.fullerton.edu.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class SignUpLoginDAO extends TableCreation implements DAO{
	
	public SignUpLoginDAO(){
		createConnection();
	}

	public boolean signUp(String username, String password) {
		int result = 0;
		if(userTableExists()){
			try {
				PreparedStatement ps = con.prepareStatement("INSERT INTO USERS (USERNAME,PASSWORD) VALUES (?,?)");
				ps.setString(1,username);
				ps.setString(2,password);
				result = ps.executeUpdate();
			}catch (SQLIntegrityConstraintViolationException e){
				return false;
			}catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
			if(result > 0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	public boolean logIn(String username, String password) {
		if(userTableExists()){
			try {
				PreparedStatement ps = con.prepareStatement("SELECT PASSWORD FROM USERS WHERE USERNAME='"+username+"'");
				ResultSet set = ps.executeQuery();
				String passwordReturned = "";
				while(set.next()){
					passwordReturned = set.getString("PASSWORD");
				}
				if(passwordReturned.trim().equals(password)){
					return true;
				}else{
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{try {
				con.close();
				System.out.println("connection closed successfully");
			} catch (SQLException e) {
				e.printStackTrace();
			}}
			return false;
		}else{
			return false;
		}
	}
	
}
