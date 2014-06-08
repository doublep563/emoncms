package com.doublep.emoncms.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.doublep.emoncms.app.Views.FeedChart;
import com.doublep.emoncms.app.Views.Feeds;
import com.doublep.emoncms.app.Views.Summary;


public class MainActivity extends ActionBarActivity implements
            Feeds.OnFeedListener{


    public static final boolean DEBUG = true;
    private static final String TAG = "MainActivity";
    private static String strURL;
    private static String strAPI;
    public SharedPreferences prefs;
    private int mStackLevel = 0;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mNavTitles;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //TODO Getting Started Screen. Setup the preferences
        if (CheckPreferences()) {
            if (MainActivity.DEBUG) Log.i(TAG, "CheckPreferences is True ");
            Fragment fragment = new Summary();
            Bundle args = new Bundle();

            FragmentManager fragmentManager = getFragmentManager();
            //getFragmentManager().popBackStack();
            FragmentTransaction transaction =fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment)
                    .addToBackStack(null)
                    .commit();
        }

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
        //Are the URL and API set?
        String strEmoncmsURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, getResources().getString(R.string.pref_default));
        String strEmoncmsAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, getResources().getString(R.string.pref_default));

        if (MainActivity.DEBUG) Log.i(TAG, "URL is " + strEmoncmsURL);
        if (MainActivity.DEBUG) Log.i(TAG, "API is " + strEmoncmsAPI);
        //TODO Protect against empty string in these fields
        if (strEmoncmsURL.equalsIgnoreCase(getResources().getString(R.string.pref_default))) {
            showDialog();
        } else if (strEmoncmsAPI.equalsIgnoreCase(getResources().getString(R.string.pref_default))) {
            showDialog();
        }
        return true;
    }

    void showDialog() {
        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SettingsDialogFragment.newInstance(mStackLevel);
        newFragment.show(ft, "dialog");
    }

    void doSaveSettings(String strURL, String strAPI) {
        //TODO This needs to be moved to Preferences class or use method from class to standardise
        //TODO updating of preferences e.g. removing /n from text views
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(common.PREF_KEY_EMONCMS_URL, strURL);
        editor.putString(common.PREF_KEY_EMONCMS_API, strAPI);
        editor.commit();
        //TODO Test the URL and API provided
        //TODO Create StartActivityforResult
        Intent i = new Intent(this, Preferences.class);
        startActivity(i);
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        // 0 = Summary
        if (position == 0) {
            Fragment fragment = new Summary();
            Bundle args = new Bundle();

            FragmentManager fragmentManager = getFragmentManager();
           // getFragmentManager().popBackStack();
            FragmentTransaction transaction =fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        // 1 = Feeds
        else if (position == 1) {
            Fragment feeds = new Feeds();

            Bundle args = new Bundle();

            FragmentManager fragmentManager = getFragmentManager();
            //getFragmentManager().popBackStack();
            FragmentTransaction transaction =fragmentManager.beginTransaction();

            transaction.replace(R.id.content_frame, feeds)
                    .addToBackStack(null)
                    .commit();
        }
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mNavTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onFeedSelected(String strFeedID) {

        Fragment fragment = new FeedChart();
        Bundle args = new Bundle();
        args.putString("strFeedID", strFeedID);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
       // getFragmentManager().popBackStack();
        FragmentTransaction transaction =fragmentManager.beginTransaction();

        transaction.replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onFeedSelected() called! +++");

    }

    public static class SettingsDialogFragment extends DialogFragment {


        static SettingsDialogFragment newInstance(int num) {
            SettingsDialogFragment f = new SettingsDialogFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.setup, null);
            builder.setView(view);

            final EditText mURL = (EditText) view.findViewById(R.id.url);
            final EditText mAPI = (EditText) view.findViewById(R.id.api);

            Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SettingsDialogFragment.this.dismiss();
                }
            });

            Button btnSave = (Button) view.findViewById(R.id.btnSave);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    strURL = mURL.getText().toString();
                    strAPI = mAPI.getText().toString();
                    if (MainActivity.DEBUG) {
                        Log.i("MainActivity", "btnSave.setOnClickListener" + strURL + " " + strAPI);
                    }
                    ((MainActivity) getActivity()).doSaveSettings(strURL, strAPI);
                }
            });
            // Create the AlertDialog object and return it
            return builder.create();
        }


    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
