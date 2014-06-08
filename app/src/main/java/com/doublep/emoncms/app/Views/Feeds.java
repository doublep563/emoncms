package com.doublep.emoncms.app.Views;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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
import com.doublep.emoncms.app.adapters.AdapterFeeds;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.loaders.LoadFeeds;
import com.doublep.emoncms.app.models.FeedDetails;

import java.util.ArrayList;

/**
 * Created by Paul Patchell on 02/06/2014.
 */
public class Feeds extends ListFragment implements
        LoaderManager.LoaderCallbacks<ArrayList> {

    // The Loader's id (this id is specific to the ListFragment's LoaderManager)
    private static final int LOADER_ID = 1;
    private static final String TAG = "Feeds";
    private String strEmoncmsURL;
    private String strEmoncmsAPI;
    OnFeedListener mListener;

    public static Feeds newInstance(int index) {
        Feeds f = new Feeds();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //TODO Add API field to Summary.xml
        strEmoncmsURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, getResources().getString(R.string.pref_default));
        strEmoncmsAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, getResources().getString(R.string.pref_default));

        if (MainActivity.DEBUG) Log.i(TAG, "URL is " + strEmoncmsURL);
        if (MainActivity.DEBUG) Log.i(TAG, "API is " + strEmoncmsAPI);


        getLoaderManager().initLoader(LOADER_ID, null, this);
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
        ArrayList feedDetails = data;
        AdapterFeeds mAdapter = new AdapterFeeds(getActivity(), R.layout.feed_list, feedDetails);

        setListAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FeedDetails feedDetails = (FeedDetails) getListAdapter().getItem(position);
        String strFeedID = feedDetails.getStrID();
        if (mListener!=null) {
            mListener.onFeedSelected(strFeedID);
        }
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onListItemClick() called! +++  " + strFeedID);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onListItemClick() called! +++");
    }

    public interface OnFeedListener {

        public void onFeedSelected(String strFeedID);

    }

}
