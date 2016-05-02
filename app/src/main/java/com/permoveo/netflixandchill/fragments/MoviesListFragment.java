package com.permoveo.netflixandchill.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.permoveo.netflixandchill.R;
import com.permoveo.netflixandchill.adapters.MoviesListAdapter;
import com.permoveo.netflixandchill.interfaces.onMoviesRequestCompleteListener;
import com.permoveo.netflixandchill.model.Movie;
import com.permoveo.netflixandchill.network.MovieListRequest;
import com.permoveo.netflixandchill.viewutils.GridItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesListFragment extends Fragment implements onMoviesRequestCompleteListener, MoviesListAdapter.OnMovieItemClickListener {

    private static final String TAG = "MoviesListFragment";
    @Bind(R.id.rv_movies_list)
    RecyclerView mMoviesList;

    private int mCurrentPage = 1;

    private MoviesListAdapter mAdapter;
    private MovieListRequest mRequest;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private MoviesListAdapter.OnMovieItemClickListener mListener;


    private StaggeredGridLayoutManager staggeredGridLayoutManager;


    public MoviesListFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (MoviesListAdapter.OnMovieItemClickListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
            Log.d("MoviesListFragment", "ClassCastException: The parent Activity must implement OnMoviteItemClickListener!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        int spanCount = (int) getResources().getInteger(R.integer.main_recycler_span_count);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        mMoviesList.setLayoutManager(staggeredGridLayoutManager);
        mMoviesList.setHasFixedSize(false);

        mMoviesList.addItemDecoration(new GridItemDecoration(3));

        mAdapter = new MoviesListAdapter(getContext(), mMovies);
        mMoviesList.setAdapter(mAdapter);

        mMoviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mAdapter.isLastItemVisible()) {
                    Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                    mRequest.requestMoviesList(++mCurrentPage);
                    Log.d(TAG, "CURRENT PAGE ->" + mCurrentPage);
                }

            }
        });

        mAdapter.setOnMovieItemClickListener(this);


        getMoviesList();


        return view;
    }

    private void getMoviesList() {

        mRequest = new MovieListRequest(this);
        mRequest.requestMoviesList(mCurrentPage);
    }

    @Override
    public void onMoviesRequestError(VolleyError error) {

        //TODO: Update the UI and notify the user an error has occured
        Toast.makeText(getContext(), "Oops! There was an error getting the movies!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMoviesRequestComplete(ArrayList<Movie> movies) {

        mAdapter.addMovies(movies);
    }

    @Override
    public void onMovieItemClicked(Movie movie) {

        mListener.onMovieItemClicked(movie);
    }


}
