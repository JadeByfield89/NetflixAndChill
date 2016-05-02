package com.permoveo.netflixandchill.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by byfieldj on 4/28/16.
 * <p/>
 * This is a simple POJO class representing an individual "movie" item that will be returned in our JSON
 */
public class Movie implements Serializable {


    private String mTitle;
    private String mReleaseDate;
    private String mDescription;
    private String mRating;
    private String mPosterUrl;
    private String mMovieId;


    public Movie(JSONObject object) {

        try {
            setTitle(object.getString("title"));
            setMovieId(object.getString("id"));
            setPosterUrl(object.getString("poster_path"));
            setReleaseDate(object.getString("release_date"));
            setDescription(object.getString("overview"));
        }catch(JSONException e){
            e.printStackTrace();
        }

    }


    public void setTitle(final String title) {
        this.mTitle = title;
    }

    public String getTitle() {

        return mTitle;
    }

    public void setReleaseDate(final String date) {
        this.mReleaseDate = date;
    }

    public String getReleaseDate() {

        return mReleaseDate;
    }

    public void setDescription(final String description) {
        this.mDescription = description;
    }

    public String getDescription() {

        return mDescription;
    }

    public void setRating(final String rating) {
        this.mRating = rating;
    }

    public String getRating() {
        return mRating;
    }

    public void setPosterUrl(final String url) {
        this.mPosterUrl = url;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public void setMovieId(final String id) {
        this.mMovieId = id;
    }

    public String getMovieId() {


        return mMovieId;
    }
}
