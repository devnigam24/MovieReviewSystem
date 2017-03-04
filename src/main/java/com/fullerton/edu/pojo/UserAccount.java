package com.fullerton.edu.pojo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.fullerton.edu.dao.SignUpLoginDAO;
import com.fullerton.edu.error.MovieError;

@ManagedBean(name="userAcc", eager=true)
@RequestScoped
public class UserAccount {
	private String userName;
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void submit(String action){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		MovieError em = new MovieError();
		if(action.equals("login")){
			SignUpLoginDAO ad = new SignUpLoginDAO();
			if(ad.logIn(this.userName, this.password)){
				em.addMessage("Login Successfull");
				req.getSession().setAttribute("sessionUser", this.userName);
			}else{
				em.addError("Username and password does not match");
			}
		}else if(action.equals("signUp")){
			SignUpLoginDAO ad = new SignUpLoginDAO();
			if(ad.signUp(this.userName, this.password)){
				em.addMessage("SignUp Successfull");
				req.getSession().setAttribute("sessionUser", this.userName);
			}else{
				em.addError("SignUp Failed!!! Database Down!!!");
			}
		}else{
			req.getSession().invalidate();
			em.addMessage("Logged Out successfully");
		}
		em.serErrorsAndMessages(req, em);
	}
}
