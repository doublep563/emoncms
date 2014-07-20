package com.doublep.emoncms.app.Views;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.adapters.AdapterFeedsExpand;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.loaders.LoadFeeds;

import java.util.ArrayList;

/**
 * Fragment to manage feeds
 */
public class Feeds extends Fragment implements

        LoaderManager.LoaderCallbacks<ArrayList> {

    // The Loader's id (this id is specific to the ListFragment's LoaderManager)
    private static final int LOADER_ID = 1;
    private static final String TAG = "Feeds";

    private String strEmoncmsURL;
    private String strEmoncmsAPI;

    private ExpandableListView elv;


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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We have an Action Bar
        setHasOptionsMenu(true);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //TODO Add API field to Summary.xml
        strEmoncmsURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, getResources().getString(R.string.pref_default));
        strEmoncmsAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, getResources().getString(R.string.pref_default));
        getLoaderManager().initLoader(LOADER_ID, null, this);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreate() called! +++");
    }


    @Override
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        return new LoadFeeds(getActivity(), strEmoncmsURL, strEmoncmsAPI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.feed_list, null);
        elv = (ExpandableListView) v.findViewById(R.id.listView);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateView() called! +++");

        return v;

    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");

        elv.setAdapter(new AdapterFeedsExpand(getActivity()));

        getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

    }







}
