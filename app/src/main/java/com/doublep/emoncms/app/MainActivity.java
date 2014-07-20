package com.doublep.emoncms.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.doublep.emoncms.app.Views.FeedChart;
import com.doublep.emoncms.app.Views.FeedChartDisplay;
import com.doublep.emoncms.app.Views.Feeds;
import com.doublep.emoncms.app.Views.StartUp;
import com.doublep.emoncms.app.Views.Summary;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements
        Feeds.OnFeedListener,
        FeedChart.OnFeedChartListener,
        StartUp.OnStartupListener,
        Feeds.OnFeedLoad {

    //Check out for start up implementation http://www.informit.com/articles/article.aspx?p=2066699
    public static final boolean DEBUG = true;
    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mNavTitles;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();

        mNavTitles = getResources().getStringArray(R.array.nav_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mNavTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                Fragment f = getFragmentManager().findFragmentById(R.id.content_frame);
                if (f != null) {
                    if (MainActivity.DEBUG)
                        Log.i(TAG, "+++ onCreate() onBackStackChanged called! +++");
                }

            }
        });


        // Are the URL and API Preferences set?
        // If so. show the Summary Fragment
        // Else, show the StartUp fragment.
        Fragment mFragment = null;
        String strFragmentTag = "";

        if (CheckPreferences()) {
            mFragment = new Summary();
            strFragmentTag = mFragment.getClass().getName();

                if (MainActivity.DEBUG)
                    Log.i(TAG, "+++ CheckPreferences() New Summary called! +++");


        } else {
            mFragment = new StartUp();
            strFragmentTag = mFragment.getClass().getName();

            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ CheckPreferences() StartUp Fragment called! +++");
        }

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, mFragment, strFragmentTag)
                .addToBackStack(strFragmentTag)
                .commit();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ OnCreate() called! +++");

    }

    @Override
    public void onStart() {
        super.onStart();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onStart() called! +++");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onResume() called! +++");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onPause() called! +++");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onStop() called! +++");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onDestroy() called! +++");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {

            case R.id.action_settings:
                // Launch settings activity
                Intent i = new Intent(this, Preferences.class);
                startActivity(i);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void selectItem(int position) {

        Fragment mFragment = null;
        String strFragmentTag = "";

        switch (position) {
            // Summary
            case 0:
                mFragment = new Summary();
                strFragmentTag = mFragment.getClass().getName();
                break;
            // Feeds
            case 1:
                mFragment = new Feeds();
                strFragmentTag = mFragment.getClass().getName();
                break;
            default:
                break;
        }

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, mFragment, strFragmentTag)
                .addToBackStack(strFragmentTag)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mNavTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ selectItems() called! +++ Fragment=" + mFragment);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ setTitle() called! +++");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onPostCreate() called! +++");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onConfigurationChanged() called! +++");
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onBackPressed() called! +++");

    }

    private boolean CheckPreferences() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ CheckPreferences() called! +++");
        //Are the URL and API set?
        String strEmoncmsURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, getResources().getString(R.string.pref_default));
        String strEmoncmsAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, getResources().getString(R.string.pref_default));

        if (MainActivity.DEBUG) Log.i(TAG, "URL is " + strEmoncmsURL);
        if (MainActivity.DEBUG) Log.i(TAG, "API is " + strEmoncmsAPI);
        //TODO Protect against empty string in these fields
        if (strEmoncmsURL.equalsIgnoreCase(getResources().getString(R.string.pref_default))) {

            return false;
        } else if (strEmoncmsAPI.equalsIgnoreCase(getResources().getString(R.string.pref_default))) {

            return false;
        } else {
            return true;
        }

    }


    public void onFeedChartSelected(String strFeedID, String strFeedTag, String strFeedName, Bundle feedData) {
        mTitle = "Feed Chart";
        setTitle((mTitle));
        ArrayList mFeedData;
        mFeedData = (ArrayList) feedData.getParcelableArrayList("feedData");

        Fragment FeedChartDisplay = new FeedChartDisplay();
        String strFragmentTag = FeedChartDisplay.getClass().getName();
        Bundle args = new Bundle();
        args.putString("strFeedID", strFeedID);
        args.putString("strFeedTag", strFeedTag);
        args.putString("strFeedName", strFeedName);
        args.putParcelableArrayList("feedData", mFeedData);

        FeedChartDisplay.setArguments(args);


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.content_frame, FeedChartDisplay, strFragmentTag)
                //.addToBackStack(strFragmentTag)
                .commit();

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onFeedChartSelected() called! +++");

    }



    @Override
    public void onStartUpCompleted() {
        //TODO this won't work!!!!!!!!!!
        if (CheckPreferences()) {

            Fragment Summary = new Summary();
            String strFragmentTag = Summary.getClass().getName();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, Summary)
                    .addToBackStack(strFragmentTag)
                    .commit();
            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ CheckPreferences() New Fragment called! +++");


        } else {
            Fragment Startup = new StartUp();
            String strFragmentTag = Startup.getClass().getName();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, Startup)
                    .addToBackStack(strFragmentTag)
                    .commit();


            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ CheckPreferences() StartUp Fragment called! +++");
        }
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onStartUpCompleted() called! +++");
    }

    @Override
    public void OnFeedLoadComplete(ArrayList data) {

        if (MainActivity.DEBUG) Log.i(TAG, "+++ OnFeedLoadComplete() called! +++");
    }

    public void onFeedSelected(String strFeedID, String strFeedTag, String strFeedName) {

        Fragment FeedChart = new FeedChart();
        String strFragmentTag = FeedChart.getClass().getName();
        Bundle args = new Bundle();
        args.putString("strFeedID", strFeedID);
        args.putString("strFeedTag", strFeedTag);
        args.putString("strFeedName", strFeedName);
        FeedChart.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.content_frame, FeedChart, strFragmentTag)
                .addToBackStack(strFragmentTag)
                .commit();

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onFeedSelected() called! +++");

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

}
