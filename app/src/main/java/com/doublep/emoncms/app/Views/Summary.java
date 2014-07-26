package com.doublep.emoncms.app.Views;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.adapters.AdapterSummary;
import com.doublep.emoncms.app.loaders.LoadSummaryStatus;

import java.util.ArrayList;

/**
 * Fragment to Manage Summary Information
 *
 * Load data from web page
 * Populate in custom adapter
 */
public class Summary extends ListFragment implements
        LoaderManager.LoaderCallbacks<ArrayList> {


    private static final String TAG = "Summary";
    private static final int LOADER_ID = 1;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onSaveInstanceState() called! +++");
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ setRetainInstance() called! +++");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ onCreate() savedInstanceState is null called! +++");

        }

        // We have an Action Bar
        setHasOptionsMenu(true);

        setRetainInstance(true);

        getLoaderManager().initLoader(LOADER_ID, null, this);
        getActivity().setProgressBarIndeterminateVisibility(true);

        //TODO Add API field to Summary.xml
        //TODO OnCreate does not refresh preferences when fragment is reused.. Need to move to another methhod
        //TODO check other fragments for this behaviour
        //TODO Tried using onResume but this causes the data to be loaded again. Need to avoid this behaviour.
        //TODO Refresh button should handle all web calls after initial web call

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreate() called! +++");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onActivityCreated() called! +++");
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
    public void onDetach() {
        super.onDetach();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onDetach() called! +++");
    }

    @Override
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        //TODO LoadSummaryStatus needs to Check Preferences to see what should be checked.
        return new LoadSummaryStatus(getActivity());
    }


    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {


        AdapterSummary mAdapter = new AdapterSummary(getActivity(), R.layout.summary, data);
        setListAdapter(mAdapter);
        getActivity().setProgressBarIndeterminateVisibility(false);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");

    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_refresh:
                getActivity().setProgressBarIndeterminateVisibility(true);
                getLoaderManager().restartLoader(LOADER_ID, null, this);
                if (MainActivity.DEBUG) Log.i(TAG, "+++ onOptionsItemSelected() called! +++");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
