package com.doublep.emoncms.app.Views;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.adapters.AdapterSummary;
import com.doublep.emoncms.app.loaders.LoadSummaryStatus;

import java.util.ArrayList;

/**
 * Fragment to Manage Summary Information
 * <p/>
 * Load data from web page
 * Populate in custom adapter
 */
public class Summary extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList> {

    //TODO Change AdapterSummary to SimpleAdapter. Change this class to extend Fragment.
    // Change summary.xml. Add Progress layout.

    private static final String TAG = "Summary";
    private static final int LOADER_ID = 1;
    private static OnTableRowClicked mCallback;
    private ListView mListView;
    private ProgressBar mProgressBar;
    private TextView mTxtViewLoading;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ onCreate() savedInstanceState is null called! +++");

        }

        // We have an Action Bar
        setHasOptionsMenu(true);

        //getActivity().setProgressBarIndeterminateVisibility(true);

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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnTableRowClicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        //TODO LoadSummaryStatus needs to Check Preferences to see what should be checked.
        return new LoadSummaryStatus(getActivity());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.summary_list, container, false);
        mListView = (ListView) v.findViewById(R.id.list);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        mTxtViewLoading = (TextView) v.findViewById(R.id.txtLoading);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateView() called! +++");
        return v;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {
        //AdapterSummary mAdapter = new AdapterSummary(getActivity(), data);
        mListView.setAdapter(new AdapterSummary(getActivity(), data));
        mProgressBar.setVisibility(View.GONE);
        mTxtViewLoading.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);

        //mListView.setAdapter(mAdapter);
        // getActivity().setProgressBarIndeterminateVisibility(false);
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
                mProgressBar.setVisibility(View.VISIBLE);
                mTxtViewLoading.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
                getLoaderManager().restartLoader(LOADER_ID, null, this);
                if (MainActivity.DEBUG) Log.i(TAG, "+++ onOptionsItemSelected() called! +++");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public interface OnTableRowClicked {
        public void onURLSelected();

        public void onFeedsSelected();
    }

    public static class TableRowClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (Integer.parseInt(v.getTag().toString())) {

                case 0:
                    mCallback.onURLSelected();
                    if (MainActivity.DEBUG) Log.i(TAG, "+++ TableRowClickListener() trURL ! +++");
                    break;
                case 1:
                    mCallback.onFeedsSelected();
                    if (MainActivity.DEBUG) Log.i(TAG, "+++ TableRowClickListener() reFeeds ! +++");
                    break;

                default:
                    break;

            }


            if (MainActivity.DEBUG) Log.i(TAG, "+++ TableRowClickListener() called! +++");
        }
    }

}
