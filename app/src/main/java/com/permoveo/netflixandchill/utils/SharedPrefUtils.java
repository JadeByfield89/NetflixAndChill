package com.permoveo.netflixandchill.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.permoveo.netflixandchill.model.Movie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by byfieldj on 5/1/16.
 * <p/>
 * This is a reusable class that gives us access to persistent storage via Shared Preferences
 */
public class SharedPrefUtils {

    private Context mContext;
    private static final String PREFS_FAVORITE_MOVIES = "favorite_movies";
    private static final String APP_PREFERENCES = "app_settings";
    private SharedPreferences mPreferences;
    private HashSet<String> mDefaultSet = new HashSet<>();

    public SharedPrefUtils(Context context) {
        this.mContext = context;

        mPreferences = mContext.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void addToFavorites(Movie movie) {


        //First, try to retrieve the current list of favorite movie IDs as a HashSet
        try {
            Set<String> favoriteMovies = getCurrentFavorites();

            //If our set does not currently contain this movie Id, add it
            if (!favoriteMovies.contains(movie.getMovieId())) {

                favoriteMovies.add(movie.getMovieId());

                //Update our preferences value
                mPreferences.edit().putStringSet(PREFS_FAVORITE_MOVIES, favoriteMovies).commit();

                Log.d("SharedPrefsUtils", "Added " + movie.getMovieId() + " to favorites!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SharedPrefsUtils", "Error adding " + movie.getMovieId() + " to favorites!");

        }
    }

    public void removeFromFavorites(Movie movie) {

        try {

            Set<String> favoriteMovies = getCurrentFavorites();

            //If our set already contains this movie's ID, then remove it
            if (favoriteMovies.contains(movie.getMovieId())) {
                favoriteMovies.remove(movie.getMovieId());


                //Update our preferences value
                mPreferences.edit().putStringSet(PREFS_FAVORITE_MOVIES, favoriteMovies).commit();

                Log.d("SharedPrefsUtils", "Removed " + movie.getMovieId() + " from favorites!");

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SharedPrefsUtils", "Error removing " + movie.getMovieId() + " from favorites!");

        }
    }

    public boolean isThisMovieAFavorite(Movie movie) {
        boolean isFavorite = false;

        try {
            Set<String> favoriteMovies = getCurrentFavorites();

            if (favoriteMovies != null && !favoriteMovies.isEmpty()) {
                if (favoriteMovies.contains(movie.getMovieId())) {
                    isFavorite = true;
                } else {
                    isFavorite = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isFavorite;
    }

    private HashSet<String> getCurrentFavorites() {

        HashSet<String> favoriteIds = new HashSet<>();

        try {
            favoriteIds = (HashSet) mPreferences.getStringSet(PREFS_FAVORITE_MOVIES, mDefaultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return favoriteIds;


    }


}
