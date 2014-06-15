package com.doublep.emoncms.app.Views;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.adapters.AdapterSummary;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.loaders.LoadSummaryStatus;

import java.util.ArrayList;

/**
 * Created by Paul Patchell on 07/06/2014.
 */
public class Summary extends ListFragment implements
        LoaderManager.LoaderCallbacks<ArrayList> {


    private static final String TAG = "Summary";
    private static final int LOADER_ID = 1;
    public static String strEmoncmsURL = "";
    private String strEmoncmsAPI;

    public static Summary newInstance(int index) {
        Summary f = new Summary();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO Add API field to Summary.xml
        //TODO OnCreate does not refresh preferences when fragment is reused.. Need to move to another methhod
        //TODO check other fragments for this behaviour


        //if (MainActivity.DEBUG) Log.i(TAG, "URL is " + strEmoncmsURL);
        //if (MainActivity.DEBUG) Log.i(TAG, "API is " + strEmoncmsAPI);



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
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
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

    @Override
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        //TODO LoadSummaryStatus needs to Check Preferences to see what should be checked.
        return new LoadSummaryStatus(getActivity(), strEmoncmsURL, strEmoncmsAPI);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {


        AdapterSummary mAdapter = new AdapterSummary(getActivity(), R.layout.summary, data);
        setListAdapter(mAdapter);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");

    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

    }


}
