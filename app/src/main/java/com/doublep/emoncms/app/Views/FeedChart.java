package com.doublep.emoncms.app.Views;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.loaders.LoadFeedChart;

/**
 * Created by Paul Patchell on 08/06/2014.
 */
public class FeedChart extends Fragment implements
        LoaderManager.LoaderCallbacks<Bundle> {

    private static final String TAG = "FeedChart";
    private static final int LOADER_ID = 1;
    OnFeedChartListener mListener;
    private String strFeedID;
    private String strEmoncmsURL;
    private String strEmoncmsAPI;
    private Bundle feedData;

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
        strFeedID = getArguments().getString("strFeedID");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //TODO Add API field to Summary.xml
        strEmoncmsURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, getResources().getString(R.string.pref_default));
        strEmoncmsAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, getResources().getString(R.string.pref_default));
        setRetainInstance(true);
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (OnFeedChartListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public Loader<Bundle> onCreateLoader(int id, Bundle args) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        //return new LoadFeeds(getActivity(), strEmoncmsURL, strEmoncmsAPI);
        return new LoadFeedChart(getActivity(), strEmoncmsURL, strEmoncmsAPI, strFeedID);
    }

    @Override
    public void onLoadFinished(Loader<Bundle> loader, Bundle data) {
        feedData = data;
        handler.sendEmptyMessage(2);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");
    }

    @Override
    public void onLoaderReset(Loader<Bundle> loader) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

    }

    public interface OnFeedChartListener {

        public void onFeedChartSelected(String strFeedID, Bundle feedData);

    }


    private Handler handler = new Handler()  // handler for commiting fragment after data is loaded
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 2)
            {
                if (mListener != null) {
                    mListener.onFeedChartSelected(strFeedID, feedData);
                }
                if (MainActivity.DEBUG) Log.i(TAG, "+++ Handler() called! +++");
                // commit the fragment
            }
        }
    };
}
