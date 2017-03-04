package com.fullerton.edu.dao;

import java.util.ArrayList;

import com.fullerton.edu.pojo.MovieBean;

public interface DAOMovie {
	ArrayList<MovieBean> fetchAllMovieListAndReviews();
	
	float getMovieRating(String movieName);
	
	Boolean setMovieRating(String movieName, float movieRating);
	
	Boolean postMovieReview(String movie,String review,String user);
}
