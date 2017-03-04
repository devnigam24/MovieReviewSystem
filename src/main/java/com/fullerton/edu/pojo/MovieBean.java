package com.fullerton.edu.pojo;

import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.fullerton.edu.dao.MovieDao;
import com.fullerton.edu.error.MovieError;

@ManagedBean(name="movieInfo",eager=true)
@RequestScoped
public class MovieBean {
	private String movieId;
	private String movieName;
	private float movieRating;
	private ArrayList<ReviewBean> movieReview;
	
	public MovieBean(){}
	
	public MovieBean(String movieId, String movieName, float movieRating, ArrayList<ReviewBean> movieReview) {
		this.movieName = movieName;
		this.movieRating = movieRating;
		this.movieReview = movieReview;
		this.movieId=movieId;
	}
	
	
	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public float getMovieRating() {
		return movieRating;
	}
	public void setMovieRating(float movieRating) {
		movieRating = movieRating;
	}
	public ArrayList<ReviewBean> getMovieReview() {
		return movieReview;
	}
	public void setMovieReview(ArrayList<ReviewBean> movieReview) {
		this.movieReview = movieReview;
	}
	
	public ArrayList<MovieBean> fetchMovieInfo(){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		MovieError me = new MovieError();
		MovieDao obj = new MovieDao();
		if (obj.getConnection() != null) {
			return obj.fetchAllMovieListAndReviews();
		} else {
			me.addError("Server Down!! database Connection Failed");
			me.serErrorsAndMessages(req, me);
			return null;
		}
	}
	
	public void submitRating(String movieName){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		MovieError me = new MovieError();
		MovieDao obj = new MovieDao();
		if(req.getParameter("movieRating") != null)
			movieRating = Float.parseFloat(req.getParameter("movieRating"));
		if (obj.getConnection() != null) {
			if(obj.setMovieRating(movieName,movieRating)){
				me.addMessage("Rating Updated");
			}
		} else {
			me.addError("Server Down!! database Connection Failed");
		}
		me.serErrorsAndMessages(req, me);
	}
}
