package com.doublep.emoncms.app.Views;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.doublep.emoncms.app.GetEmonData;
import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.adapters.AdapterFeedsExpand;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.loaders.LoadFeeds;
import com.doublep.emoncms.app.models.FeedDetails;

import java.util.ArrayList;

/**
 * Fragment to manage feeds
 */
public class Feeds extends Fragment implements

        LoaderManager.LoaderCallbacks<ArrayList> {

    // The Loader's id (this id is specific to the ListFragment's LoaderManager)
    private static final int LOADER_ID = 2;
    private static final String TAG = "Feeds";
    private static OnFeedSelected mCallback;
    private String strEmoncmsURL;
    private String strEmoncmsAPI;
    private ExpandableListView elv;
    private ProgressBar mProgressBar;
    private TextView mTxtViewLoading;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);


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

        if (savedInstanceState == null) {
            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ onCreate() savedInstanceState is null called! +++");

        }


        // We have an Action Bar
        setHasOptionsMenu(true);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //TODO Add API field to Summary.xml
        strEmoncmsURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, getResources().getString(R.string.pref_default));
        strEmoncmsAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, getResources().getString(R.string.pref_default));


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
        elv = (ExpandableListView) v.findViewById(R.id.list);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        mTxtViewLoading = (TextView) v.findViewById(R.id.txtLoading);


        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateView() called! +++");

        return v;

    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");

        elv.setAdapter(new AdapterFeedsExpand(getActivity()));
        mProgressBar.setVisibility(View.GONE);
        mTxtViewLoading.setVisibility(View.GONE);
        elv.setVisibility(View.VISIBLE);


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
                mProgressBar.setVisibility(View.VISIBLE);
                mTxtViewLoading.setVisibility(View.VISIBLE);
                elv.setVisibility(View.GONE);
                getLoaderManager().restartLoader(LOADER_ID, null, this);
                if (MainActivity.DEBUG) Log.i(TAG, "+++ onOptionsItemSelected() called! +++");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnFeedSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public interface OnFeedSelected {
        public void onFeedSelected(String strFeedID, String strFeedTag, String strFeedName);
    }

    public static class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            ArrayList<FeedDetails> items = GetEmonData.feedList;
            FeedDetails feedDetails = items.get(position);
            String strFeedID = feedDetails.getStrID();
            String strFeedTag = feedDetails.getStrTag();
            String strFeedName = feedDetails.getStrName();
            mCallback.onFeedSelected(strFeedID, strFeedTag, strFeedName);
            if (MainActivity.DEBUG) Log.i(TAG, "+++ OnClickListener() called! +++");
        }
    }

}
