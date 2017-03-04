package com.fullerton.edu.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import com.fullerton.edu.pojo.MovieBean;
import com.fullerton.edu.pojo.ReviewBean;

public class MovieDao extends TableCreation implements DAOMovie{
	
	public MovieDao(){
		super.createConnection();
	}

	public ArrayList<MovieBean> fetchAllMovieListAndReviews() {
		try{
			if(movieTableExists()){
				ArrayList<MovieBean> returnList = new ArrayList<MovieBean>();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM MOVIERATING");
				ResultSet set = ps.executeQuery();
				String movieName = "";
				float rating = 0;
				String movieID = "";
				ArrayList<ReviewBean> reviews = new ArrayList<ReviewBean>();
				while(set.next()){
					movieName = set.getString("MOVIENAME");
					rating = set.getFloat("RATING");
					movieID = set.getString("MOVIEID");
					reviews = fetchThisMovieRevies(movieName);
					returnList.add(new MovieBean(movieID,movieName, rating, reviews));
				}
				return returnList;
			}else{
				return null;
			}		
		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private ArrayList<ReviewBean> fetchThisMovieRevies(String movieName) {
		try{
			if(reviewTableExists()){
				PreparedStatement ps = con.prepareStatement("SELECT REVIEW,USERNAME FROM MOVIEREVIEW WHERE MOVIENAME='"+movieName+"'");
				ResultSet set = ps.executeQuery();
				String review = "";
				String username = "";
				ArrayList<ReviewBean> reviewList = new ArrayList<ReviewBean>();
				while(set.next()){
					review = set.getString("REVIEW");
					username = set.getString("USERNAME");
					ReviewBean rb = new ReviewBean(review,username);
					reviewList.add(rb);
				}
				return reviewList;
			}else{
				return null;
			}		
		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	public float getMovieRating(String movieName) {
		float rating = 0;
		try{
			if(movieTableExists()){
				PreparedStatement ps = con.prepareStatement("SELECT RATING FROM MOVIERATING WHERE MOVIENAME='"+movieName+"'");
				ResultSet set = ps.executeQuery();
				while(set.next()){
					rating = set.getFloat("RATING");
				}
				return rating;
			}
			return rating;		
		}catch(Exception e){
			e.printStackTrace();
			return rating;
		}
	}

	public Boolean setMovieRating(String movieName, float newRating) {
		try{
			if(movieTableExists()){
				float oldRating = getMovieRating(movieName);
				float ratingToSet = 0;
				if(oldRating != 0){
					ratingToSet = (oldRating + newRating)/2;
				}else{
					ratingToSet = newRating;
				}				
				PreparedStatement ps = con.prepareStatement("UPDATE MOVIERATING SET RATING="+ratingToSet+" WHERE MOVIENAME='"+movieName+"'");
				if(ps.executeUpdate() > 0){
					return true;
				}else{
					return false;
				}
			}
			return false;		
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public Boolean postMovieReview(String movie, String review,String username) {
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO MOVIEREVIEW(MOVIENAME,REVIEW,USERNAME) VALUES(?,?,?)");
			ps.setString(1, movie);
			ps.setString(2, review);
			ps.setString(3, username);
			if(ps.executeUpdate() >0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				e.printStackTrace();
				return null;
			} else {
				e.printStackTrace();
				return false;
			}
		} finally {
			try {
				con.close();
				System.out.println("connection closed successfully");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		}
	
	
}
