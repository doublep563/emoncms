package com.doublep.emoncms.app.Views;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.adapters.AdapterFeeds;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.loaders.LoadFeeds;
import com.doublep.emoncms.app.models.FeedDetails;

import java.util.ArrayList;

/**
 * Fragment to manage feeds
 */
public class Feeds extends ListFragment implements
        AdapterFeeds.BtnChartListener,
        LoaderManager.LoaderCallbacks<ArrayList> {

    // The Loader's id (this id is specific to the ListFragment's LoaderManager)
    private static final int LOADER_ID = 1;
    private static final String TAG = "Feeds";
    private OnFeedListener mListener;
    private String strEmoncmsURL;
    private String strEmoncmsAPI;

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
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //TODO Add API field to Summary.xml
        strEmoncmsURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, getResources().getString(R.string.pref_default));
        strEmoncmsAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, getResources().getString(R.string.pref_default));
        getLoaderManager().initLoader(LOADER_ID, null, this);
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We have an Action Bar
        setHasOptionsMenu(true);


        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreate() called! +++");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (OnFeedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        return new LoadFeeds(getActivity(), strEmoncmsURL, strEmoncmsAPI);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");
        AdapterFeeds mAdapter;
        mAdapter = new AdapterFeeds(getActivity(), R.layout.feed_list, data,  new AdapterFeeds.BtnChartListener()
        {

            public void onBtnClick(int position) {
                // TODO Auto-generated method stub
                // Call your function which creates and shows the dialog here
                if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() onBtnClick called! +++");
            }

        }
        );

        setListAdapter(mAdapter);

        getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FeedDetails feedDetails = (FeedDetails) getListAdapter().getItem(position);
        String strFeedID = feedDetails.getStrID();
        String strFeedTag = feedDetails.getStrTag();
        String strFeedName = feedDetails.getStrName();
        if (mListener != null) {
            mListener.onFeedSelected(strFeedID, strFeedTag, strFeedName);
        }
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onListItemClick() called! +++  " + strFeedID);

    }

    @Override
    public void onBtnClick(int position) {

    }

    public interface OnFeedListener {

        public void onFeedSelected(String feedID, String strFeedTag, String strFeedID);

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
