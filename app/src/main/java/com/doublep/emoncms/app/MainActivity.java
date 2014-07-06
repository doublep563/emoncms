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
        Feeds.OnFeedListener, FeedChart.OnFeedChartListener, StartUp.OnStartupListener {

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
        //TODO Need to Check Preferences to see what other options are set
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


        // Are the URL and API Preferences set?
        // If so. show the Summary Fragment
        // Else, show the StartUp fragment.
        if (CheckPreferences()) {
            if (savedInstanceState == null) {
                Fragment sumFrag = new Summary();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, sumFrag)
                        .addToBackStack(null)
                        .commit();
                if (MainActivity.DEBUG)
                    Log.i(TAG, "+++ CheckPreferences() New Fragment called! +++");
            } else {

                //Summary sumFrag =  (Summary)getFragmentManager().findFragmentByTag("sumFrag");
                if (MainActivity.DEBUG)
                    Log.i(TAG, "+++ CheckPreferences() Reuse Fragment called! +++");
            }

        } else {
            Fragment StartupFrag = new StartUp();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, StartupFrag)
                    .addToBackStack(null)
                    .commit();


            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ CheckPreferences() StartUp Fragment called! +++");
        }


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

    private void selectItem(int position) {
        // 0 = Summary
        if (position == 0) {
            Fragment fragment = new Summary();


            FragmentManager fragmentManager = getFragmentManager();
            // getFragmentManager().popBackStack();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment)
                    .addToBackStack(null)
                    .commit();
            if (MainActivity.DEBUG) Log.i(TAG, "+++ selectItem() Summary called! +++");
        }
        // 1 = Feeds
        else if (position == 1) {
            Fragment feeds = new Feeds();

            FragmentManager fragmentManager = getFragmentManager();
            //getFragmentManager().popBackStack();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.content_frame, feeds)
                    .addToBackStack(null)
                    .commit();
        }
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mNavTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ selectItems() feeds called! +++");
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
    public void onFeedSelected(String strFeedID, String strFeedTag, String strFeedName) {

        Fragment fragment = new FeedChart();
        Bundle args = new Bundle();
        args.putString("strFeedID", strFeedID);
        args.putString("strFeedTag", strFeedTag);
        args.putString("strFeedName", strFeedName);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        // getFragmentManager().popBackStack();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onFeedSelected() called! +++");

    }

    public void onFeedChartSelected(String strFeedID, String strFeedTag, String strFeedName, Bundle feedData) {
        mTitle = "Feed Chart";
        setTitle((mTitle));
        ArrayList mFeedData;
        mFeedData = (ArrayList) feedData.getParcelableArrayList("feedData");

        Fragment feedChart = new FeedChartDisplay();
        Bundle args = new Bundle();
        args.putString("strFeedID", strFeedID);
        args.putString("strFeedTag", strFeedTag);
        args.putString("strFeedName", strFeedName);
        args.putParcelableArrayList("feedData", mFeedData);

        feedChart.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        // getFragmentManager().popBackStack();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //Custom Animation
        transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);

        transaction.replace(R.id.content_frame, feedChart, "abcd")
                //.addToBackStack("abcd")
                .commit();

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onFeedChartSelected() called! +++");

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {

            Fragment fragments = getFragmentManager().findFragmentByTag("abcd");
            if (fragments == null) {

                getFragmentManager().popBackStack();
                if (MainActivity.DEBUG) Log.i(TAG, "+++ popbackstack! +++");
            } else {
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("abcd")).commit();
                getFragmentManager().popBackStack();

                if (MainActivity.DEBUG)
                    Log.i(TAG, "+++ Remove the abcd fragment and popbackstack! +++");
            }

        }
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onBackPressed() called! +++");

    }

    @Override
    public void onStartUpCompleted() {
        //TODO this won't work!!!!!!!!!!
        if (CheckPreferences()) {

            Fragment sumFrag = new Summary();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, sumFrag)
                    .addToBackStack(null)
                    .commit();
            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ CheckPreferences() New Fragment called! +++");


        } else {
            Fragment StartupFrag = new StartUp();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, StartupFrag)
                    .addToBackStack(null)
                    .commit();


            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ CheckPreferences() StartUp Fragment called! +++");
        }
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onStartUpCompleted() called! +++");
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
