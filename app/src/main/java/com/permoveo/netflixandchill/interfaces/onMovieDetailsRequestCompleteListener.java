package com.permoveo.netflixandchill.interfaces;

import com.android.volley.VolleyError;

/**
 * Created by byfieldj on 4/28/16.
 */
public interface onMovieDetailsRequestCompleteListener {

    public abstract void onMovieDetailsRequestComplete();

    public abstract void onMovieDetailsRequestError(VolleyError error);
}
