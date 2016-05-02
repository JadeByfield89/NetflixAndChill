package com.permoveo.netflixandchill.interfaces;

import com.android.volley.VolleyError;
import com.permoveo.netflixandchill.model.Movie;

import java.util.ArrayList;

/**
 * Created by byfieldj on 4/28/16.
 */
public interface onMoviesRequestCompleteListener {

    public abstract void onMoviesRequestComplete(ArrayList<Movie> movies);
    public abstract void onMoviesRequestError(VolleyError error);
}
