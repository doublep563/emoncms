package com.doublep.emoncms.app.Views;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.loaders.LoadFeedChart;
import com.doublep.emoncms.app.models.FeedData;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;

/**
 * Fragment to Manage retrieval of Feed Details
 */
public class FeedChart extends Fragment implements
        LoaderManager.LoaderCallbacks<Bundle> {

    private static final String TAG = "FeedChart";
    private static final int LOADER_ID = 1;
    private String strFeedID;
    private String strEmoncmsURL;
    private String strEmoncmsAPI;


    private TimeSeries time_series;
    private LinearLayout chartLayout;
    private TextView chartTitle;
    private ArrayList<FeedData> feedData;
    private ProgressBar mProgressBar;
    private TextView mTxtViewLoading;
    private LinearLayout myLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        //getActivity().getActionBar().setTitle("Feed Chart");

        super.onCreate(savedInstanceState);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreate() called! +++");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.feed_chart, container, false);
        assert root != null;
        chartLayout = (LinearLayout) root.findViewById(R.id.chart);
        myLayout = (LinearLayout) root.findViewById(R.id.ll1);
        chartTitle = (TextView) root.findViewById(R.id.chartTitle);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progress_bar);
        mTxtViewLoading = (TextView) root.findViewById(R.id.txtLoading);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateView() called! +++");
        return root;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getActionBar().setTitle("Feed Chart");
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
        String strFeedTag = getArguments().getString("strFeedTag");
        String strFeedName = getArguments().getString("strFeedName");

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

    @Override
    public Loader<Bundle> onCreateLoader(int id, Bundle args) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        //return new LoadFeeds(getActivity(), strEmoncmsURL, strEmoncmsAPI);
        return new LoadFeedChart(getActivity(), strEmoncmsURL, strEmoncmsAPI, strFeedID);
    }

    @Override
    public void onLoadFinished(Loader<Bundle> loader, Bundle data) {
        DisplayChart(data);
        mProgressBar.setVisibility(View.GONE);
        mTxtViewLoading.setVisibility(View.GONE);
        myLayout.setVisibility(View.VISIBLE);
        chartTitle.setVisibility(View.VISIBLE);
        chartLayout.setVisibility(View.VISIBLE);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");
    }

    @Override
    public void onLoaderReset(Loader<Bundle> loader) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

    }

    void DisplayChart(Bundle bundle) {
        //TODO Need more info on the Feed to display in the chart i.e tag, name
        //TODO USe setTitle to include above info in title bar. In MainActivity?
        String strFeedID = getArguments().getString("strFeedID");
        String strFeedTag = getArguments().getString("strFeedTag");
        String strFeedName = getArguments().getString("strFeedName");
        feedData = bundle.getParcelableArrayList("feedData");

        // create dataset and renderer
        XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        float val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, metrics);
        mRenderer.setLabelsTextSize(val);

        //Text Colors
        mRenderer.setYLabelsColor(0, getResources().getColor(R.color.Black));
        mRenderer.setXLabelsColor(getResources().getColor(R.color.Black));

        //Chart Title
        chartTitle.setText(strFeedName + " " + strFeedTag);

        mRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        mRenderer.setMarginsColor(getResources().getColor(R.color.emoncms_pale));
        //mRenderer.setMargins(new int[]{0, 100, 90, 50});
        if (getRotation().equalsIgnoreCase("portrait")) {
            mRenderer.setMargins(new int[]{-30, 120, -120, 30});
        } else {
            mRenderer.setMargins(new int[]{-30, 120, 0, 30});
        }

        mRenderer.setPointSize(5.0f);

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(getResources().getColor(R.color.White));

        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLUE);
        r.setPointStyle(PointStyle.POINT);
        r.setPointStrokeWidth(10.0f);

        r.setShowLegendItem(false);

        r.setFillPoints(true);
        mRenderer.addSeriesRenderer(r);
        mRenderer.setClickEnabled(true);
        mRenderer.setSelectableBuffer(20);
        mRenderer.setPanEnabled(true);

        time_series = new TimeSeries("");

        mDataset.addSeries(time_series);

        LoadData();

        GraphicalView mChartView = ChartFactory.getTimeChartView(getActivity(), mDataset, mRenderer,
                "HH:mm");

        chartLayout.addView(mChartView);

    }

    private void LoadData() {
        for (FeedData aFeedData : feedData) {

            long mTime = aFeedData.getFeedTime();
            double mData = aFeedData.getFeedData();
            time_series.add(mTime, mData);
        }


        if (MainActivity.DEBUG) Log.i(TAG, "+++ LoadData() called! +++");
    }

    public String getRotation() {
        final int rotation = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return "portrait";
            case Surface.ROTATION_90:
                return "landscape";
            case Surface.ROTATION_180:
                return "reverse portrait";
            default:
                return "reverse landscape";
        }
    }

}
