package com.permoveo.netflixandchill.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.permoveo.netflixandchill.R;
import com.permoveo.netflixandchill.model.Movie;
import com.permoveo.netflixandchill.utils.SharedPrefUtils;
import com.squareup.picasso.Picasso;


import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by byfieldj on 4/28/16.
 */
public class MovieDetailFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_MOVIE = "movie";



    @Bind(R.id.cv_movie_detail_card)
    CardView mMovieDetailCard;

    @Bind(R.id.iv_movie_poster)
    ImageView mMoviePoster;

    @Bind(R.id.tv_movie_title)
    TextView mMovieTitle;

    @Bind(R.id.tv_movie_description)
    TextView mMovieDescription;

    @Bind(R.id.iv_favorite_icon)
    ImageView mFavoriteIcon;

    @Bind(R.id.iv_share_icon)
    ImageView mShareIcon;

    private Movie mMovie;
    private View mView;

    private SharedPrefUtils mUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);

        Log.d("MovieDetailFragment", "Fragment showing");

        mMovie = (Movie) getArguments().getSerializable(EXTRA_MOVIE);
        mUtils = new SharedPrefUtils(getContext());

        if(mUtils.isThisMovieAFavorite(mMovie)){
            mFavoriteIcon.setBackgroundResource(R.drawable.ic_favorite_selected);
        }
        else{
            mFavoriteIcon.setBackgroundResource(R.drawable.ic_favorite);
        }

        updateUI();

        mView = view;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

            YoYo.with(Techniques.ZoomInUp).duration(600).playOn(mView);


    }

    private void updateUI() {
        //Load the poster
        Picasso.with(getContext()).load(mMovie.getPosterUrl()).fit().into(mMoviePoster);

        //Load the title
        mMovieTitle.setText(mMovie.getTitle());


        //Load the description
        mMovieDescription.setText(mMovie.getDescription());

        //Set listeners to our share and favorite icons
        mShareIcon.setOnClickListener(this);

        mFavoriteIcon.setOnClickListener(this);
    }

    public MovieDetailFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static MovieDetailFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_MOVIE, movie);

        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_share_icon:

                shareMovie(mMovie);
                break;

            case R.id.iv_favorite_icon:

                favoriteMovie(mMovie);
                break;


        }
    }

    private void favoriteMovie(Movie movie){


        if(!mUtils.isThisMovieAFavorite(movie)) {
            mUtils.addToFavorites(movie);
            mFavoriteIcon.setBackgroundResource(R.drawable.ic_favorite_selected);
            Toast.makeText(getContext(), movie.getTitle() + " added to favorites!", Toast.LENGTH_SHORT).show();
        }

        else{
            mUtils.removeFromFavorites(movie);
            mFavoriteIcon.setBackgroundResource(R.drawable.ic_favorite);
            Toast.makeText(getContext(), movie.getTitle() + " removed from favorites!", Toast.LENGTH_SHORT).show();

        }
    }

    private void shareMovie(Movie movie){

        Uri imageUri = Uri.parse(movie.getPosterUrl());

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Really excited about the new movie " + movie.getTitle() + ", check it out! " + "\n\n" + movie.getDescription() + "\n\n" + imageUri);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_movie)));



    }
}
