package com.doublep.emoncms.app.Views;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.doublep.emoncms.app.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Date;

/**
 * Created by Paul Patchell on 08/06/2014.
 */
public class FeedChartDisplay extends Fragment {

    private XYMultipleSeriesDataset mDataset;
    private XYMultipleSeriesRenderer mRenderer;
    private TimeSeries time_series;
    private GraphicalView mChartView;
    private LinearLayout layout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // create dataset and renderer
        mDataset = new XYMultipleSeriesDataset();
        mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.setAxisTitleTextSize(16);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.setPointSize(3f);

        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.GREEN);
        r.setPointStyle(PointStyle.CIRCLE);
        r.setFillPoints(true);
        mRenderer.addSeriesRenderer(r);
        mRenderer.setClickEnabled(true);
        mRenderer.setSelectableBuffer(20);
        mRenderer.setPanEnabled(true);

        time_series = new TimeSeries("test");

        mDataset.addSeries(time_series);

        fillData();

        mChartView = ChartFactory.getTimeChartView(getActivity(), mDataset, mRenderer,
                "H:mm:ss");

        layout.addView(mChartView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void fillData() {
        long value = new Date().getTime() - 3 * TimeChart.DAY;
        for (int i = 0; i < 100; i++) {
            time_series.add(new Date(value + i * TimeChart.DAY / 4), i);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.feed_chart, null);
        layout = (LinearLayout) root.findViewById(R.id.chart);
        return root;

    }
}
