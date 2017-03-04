package com.fullerton.edu.dao;

public interface DAO{
	boolean signUp(String username,String password);
	boolean logIn(String username,String password);
}
