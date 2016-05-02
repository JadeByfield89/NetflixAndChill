package com.permoveo.netflixandchill.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.permoveo.netflixandchill.R;
import com.permoveo.netflixandchill.constants.AppConstants;
import com.permoveo.netflixandchill.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by byfieldj on 4/28/16.
 */
public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {

    private ArrayList<Movie> mMovies;
    private Context mContext;
    private boolean mLastItemVisible;
    private static OnMovieItemClickListener mItemClickListener;
    private static int mCurrentCount;


    public MoviesListAdapter(Context context, ArrayList<Movie> movies) {

        mMovies = movies;
        mContext = context;
        mCurrentCount = movies.size();
    }

    public boolean isLastItemVisible() {
        return mLastItemVisible;
    }

    public void addMovies(ArrayList<Movie> movies) {
        mLastItemVisible = false;

        // Ensure that we're not adding any duplicates to our display
        for (Movie movie : movies) {
            if (!mMovies.contains(movie)) {
                mMovies.add(movie);
            }
        }

        notifyItemRangeChanged(movies.size() - 1, mCurrentCount);
    }

    @Override
    public int getItemCount() {
        Log.d("MovieListAdapter", "GetItemCount -> " + mMovies.size());
        return
                mMovies.size();
    }

    @Override
    public void onBindViewHolder(MoviesListAdapter.ViewHolder holder, final int position) {

        holder.movie = mMovies.get(position);

        holder.movieTitle.setText(mMovies.get(position).getTitle());
        String posterUrl = String.format(AppConstants.MOVIE_DB_POSTER_ENDPOINT, mMovies.get(position).getPosterUrl());
        holder.movie.setPosterUrl(posterUrl);
        Picasso.with(mContext).load(posterUrl).fit().into(holder.moviePoster);


        if (position == getItemCount() - 1) {
            mLastItemVisible = true;
            Log.d("MoviesListAdapter", "Last item visible!");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);

        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Movie movie;

        @Bind(R.id.iv_movie_poster)
        ImageView moviePoster;

        @Bind(R.id.tv_movie_title)
        TextView movieTitle;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null && movie != null) {
                mItemClickListener.onMovieItemClicked(movie);
            }

        }
    }

    public interface OnMovieItemClickListener {

        public abstract void onMovieItemClicked(Movie movie);

    }

    public void setOnMovieItemClickListener(OnMovieItemClickListener listener) {
        this.mItemClickListener = listener;

    }


}
