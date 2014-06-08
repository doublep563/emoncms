package com.doublep.emoncms.app.Views;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.loaders.LoadFeedChart;
import com.doublep.emoncms.app.loaders.LoadFeeds;

import java.util.ArrayList;

/**
 * Created by Paul Patchell on 08/06/2014.
 */
public class FeedChart extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList>{

    private static final String TAG = "FeedChart";
    private String strFeedID;
    private static final int LOADER_ID = 1;
    private String strEmoncmsURL;
    private String strEmoncmsAPI;

    public static FeedChart newInstance(int index) {
        FeedChart f = new FeedChart();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strFeedID = getArguments().getString("strFeedID");
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreate() called! +++  " + strFeedID);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreate() called! +++");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //TODO Add API field to Summary.xml
         strEmoncmsURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, getResources().getString(R.string.pref_default));
         strEmoncmsAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, getResources().getString(R.string.pref_default));

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        //return new LoadFeeds(getActivity(), strEmoncmsURL, strEmoncmsAPI);
        return new LoadFeedChart(getActivity(), strEmoncmsURL, strEmoncmsAPI, strFeedID);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");

    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

    }
}
