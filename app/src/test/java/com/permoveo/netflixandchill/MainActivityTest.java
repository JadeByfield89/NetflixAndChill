package com.permoveo.netflixandchill;


// Static imports for assertion methods
import com.permoveo.netflixandchill.activities.MainActivity;
import com.permoveo.netflixandchill.fragments.MovieDetailFragment;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;



@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity mActivity;
    private MovieDetailFragment mMainFragment;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        mActivity = Robolectric.setupActivity(MainActivity.class);
    }


    @Test
    public void activityIsNotNull(){

        assertNotNull(mActivity);
    }

    @Test
    public void isMainFragmentVisible(){

    }




}