package com.doublep.emoncms.app.Views;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
    private String strRaspPiStatus;
    private String strGoodFeeds;
    private String strBadFeeds;
    private AdapterSummary mAdapter;

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
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //TODO Add API field to Summary.xml
        strEmoncmsURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, getResources().getString(R.string.pref_default));
        strEmoncmsAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, getResources().getString(R.string.pref_default));

        if (MainActivity.DEBUG) Log.i(TAG, "URL is " + strEmoncmsURL);
        if (MainActivity.DEBUG) Log.i(TAG, "API is " + strEmoncmsAPI);


        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        return new LoadSummaryStatus(getActivity(), strEmoncmsURL, strEmoncmsAPI);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {

        ArrayList summaryStatus = data;
        mAdapter = new AdapterSummary(getActivity(), R.layout.summary, summaryStatus);
        setListAdapter(mAdapter);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");

    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

    }


}
