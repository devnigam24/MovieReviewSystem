package com.fullerton.edu.error;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class MovieError {
	private ArrayList<String> error;
	private ArrayList<String> messages;

public MovieError() {
	this.error = new ArrayList<String>();
	this.messages = new ArrayList<String>();
}

public ArrayList<String> getError() {
	return error;
}

public void setError(ArrayList<String> error) {
	this.error = error;
}

public ArrayList<String> getMessages() {
	return messages;
}

public void setMessages(ArrayList<String> messages) {
	this.messages = messages;
}

public void addMessage(String message) {
	this.messages.add(message);
}

public void addError(String error) {
	this.error.add(error);
}

public void serErrorsAndMessages(HttpServletRequest req, MovieError me) {
	req.setAttribute("infoMessage", me.getMessages());
	req.setAttribute("errorMessage", me.getError());		
}}
