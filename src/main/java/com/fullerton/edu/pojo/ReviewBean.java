package com.fullerton.edu.pojo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.fullerton.edu.dao.MovieDao;
import com.fullerton.edu.error.MovieError;

@ManagedBean(name = "reviewBean", eager = true)
@RequestScoped
public class ReviewBean {
	private String review;
	private String userName;
	
	public ReviewBean(String review, String userName) {
		this.review = review;
		this.userName = userName;
	}
	
	public ReviewBean(){}
	
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void storeThisReviewForThisPost(String movieNAme){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();		
		MovieError em = new MovieError();
		if(req.getParameter("review") != null ){
			this.review = req.getParameter("review");
		}
		if(this.review != null && !this.review.equals("")){
			this.userName = req.getSession().getAttribute("sessionUser").toString();
			MovieDao obj = new MovieDao();
			if (obj.getConnection() != null) {			
				Boolean stored = obj.postMovieReview(movieNAme,review,userName);
				if (null != stored && stored == true) {
					em.addMessage("Review Posted successfully");
				} else if (null != stored && stored == false) {
					em.addError("Server Down!! database Connection Failed");
				} else {
					em.addError("Sorry Somebody already took that Review");
				}
			} else {
				em.addError("Server Down!! database Connection Failed");
			}
		}else{
			em.addError("Please Review something to be pushed");
		}		
		em.serErrorsAndMessages(req, em);
	}
	
}
