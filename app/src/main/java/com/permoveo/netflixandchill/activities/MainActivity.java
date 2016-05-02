package com.permoveo.netflixandchill.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.permoveo.netflixandchill.R;
import com.permoveo.netflixandchill.adapters.MoviesListAdapter;
import com.permoveo.netflixandchill.fragments.MovieDetailFragment;
import com.permoveo.netflixandchill.fragments.MoviesListFragment;
import com.permoveo.netflixandchill.model.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesListAdapter.OnMovieItemClickListener {

    private FragmentManager mFragmentManager;

    @Bind(R.id.overlay)
    LinearLayout mOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MoviesListFragment fragment = new MoviesListFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.master_frame, fragment, "main").commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemClicked(Movie movie) {
        MovieDetailFragment fragment = MovieDetailFragment.newInstance(movie);

        mFragmentManager.beginTransaction().add(R.id.detail_frame, fragment, "movie_detail").addToBackStack("movie_detail").commit();
        mOverlay.setVisibility(View.VISIBLE);

        //Blur the background
        /*BlurBehind.getInstance().execute(this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {

            }
        });*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mOverlay.setVisibility(View.GONE);
    }
}
