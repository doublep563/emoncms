package com.doublep.emoncms.app.Views;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
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
 * Created by Paul Patchell on 08/06/2014.
 */
public class FeedChartDisplay extends Fragment {
    private static final String TAG = "FeedChartDisplay";

    private TimeSeries time_series;

    private String strFeedID;
    private ArrayList<FeedData> feedData;
    private String strFeedTag;
    private String strFeedName;
    private LinearLayout chartlayout;
    private TextView chartTitle;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TODO Need more info on the Feed to display in the chart i.e tag, name
        //TODO USe setTitle to include above info in title bar. In MainActivity?
        strFeedID = getArguments().getString("strFeedID");
        strFeedTag = getArguments().getString("strFeedTag");
        strFeedName = getArguments().getString("strFeedName");
        feedData = getArguments().getParcelableArrayList("feedData");

        // create dataset and renderer
        XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        //Text Size
        //mRenderer.setAxisTitleTextSize(60);
        //mRenderer.setChartTitleTextSize(60);

        //mRenderer.setLegendTextSize(45);

        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        float val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, metrics);
        mRenderer.setLabelsTextSize(val);
        //Text Colors
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setAxesColor(Color.BLACK);

        //Chart Title
        chartTitle.setText(strFeedName + " " + strFeedTag);

        mRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        mRenderer.setMarginsColor(Color.TRANSPARENT);
        mRenderer.setMargins(new int[]{10, 100, 90, 0});
        mRenderer.setPointSize(5f);
        //TODO Min of 0 is not correct in all circumstances - negative temps
        //TODO Need to set min/max values based on data returned or just leave it!!!
        //mRenderer.setYAxisMin(0);



        //mRenderer.setMarginsColor(getResources().getColor(R.color.emoncms_btn_Color));
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.TRANSPARENT);
        //mRenderer.setChartTitle(strFeedName);


        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLUE);
        r.setPointStyle(PointStyle.CIRCLE);
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
                "H:mm:ss");

        chartlayout.addView(mChartView);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onActivityCreated() called! +++");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreate() called! +++");
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

    private void LoadData() {
        for (FeedData aFeedData : feedData) {

            long mTime = aFeedData.getFeedTime();
            double mData = aFeedData.getFeedData();
            time_series.add(mTime, mData);
        }


        if (MainActivity.DEBUG) Log.i(TAG, "+++ fillData() called! +++");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.feed_chart, null);
        assert root != null;
        chartlayout = (LinearLayout) root.findViewById(R.id.chart);
         chartTitle = (TextView) root.findViewById(R.id.chartTitle);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateView() called! +++");
        return root;

    }
}
