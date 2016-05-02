package com.permoveo.netflixandchill.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.permoveo.netflixandchill.application.NetflixAndChillApplication;
import com.permoveo.netflixandchill.constants.AppConstants;
import com.permoveo.netflixandchill.interfaces.onMoviesRequestCompleteListener;
import com.permoveo.netflixandchill.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by byfieldj on 4/28/16.
 * <p/>
 * This class executes a Volley request to simply return a list of movies, organized by release date, rating
 * or if no constraints are provided, in whatever order the API returns
 */
public class MovieListRequest {


    private onMoviesRequestCompleteListener mListener;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private static final String TAG = "MovieListRequest";

    public MovieListRequest(final onMoviesRequestCompleteListener listener) {

        this.mListener = listener;

    }


    public void requestMoviesList(int page) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, AppConstants.MOVIE_DB_MOVIES_ENDPOINT + "&page=" + page, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {


                Log.d(TAG, "Movies response ->  " + jsonObject);
                setMoviesList(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.d(TAG, "Error getting list of movies!");
                mListener.onMoviesRequestError(volleyError);
            }
        });

        NetflixAndChillApplication.getInstance().addToRequestQueue(request);
    }

    private void setMoviesList(JSONObject object) {

        try {
            JSONArray moviesArray = object.getJSONArray("results");
            Movie movie;


            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movieObject = moviesArray.getJSONObject(i);
                movie = new Movie(movieObject);
                mMovies.add(movie);

                Log.d(TAG, "Current movie data -> " + movieObject.toString());


            }

            if(!mMovies.isEmpty()){
                mListener.onMoviesRequestComplete(mMovies);
            }
            else{
                mListener.onMoviesRequestError(new VolleyError("Oops! Movie data is empty!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
