package com.permoveo.netflixandchill.constants;

/**
 * Created by byfieldj on 4/28/16.
 */
public class AppConstants {

    public static final String MOVIE_DB_API_KEY = "fb1c60cff4612fe2cc5772c7b6e222f9";
    public static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie/550?api_key="+MOVIE_DB_API_KEY;
    public static final String MOVIE_DB_MOVIES_ENDPOINT = "https://api.themoviedb.org/3/discover/movie?primary_release_date.gte=2016-04-29&primary_release_date.lte=2016-12-29&api_key="+MOVIE_DB_API_KEY;
    public static final String MOVIE_DB_POSTER_ENDPOINT = "http://image.tmdb.org/t/p/w500/%s"+"?api_key="+MOVIE_DB_API_KEY;
}
