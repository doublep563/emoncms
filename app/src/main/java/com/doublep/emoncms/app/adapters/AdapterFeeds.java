package com.doublep.emoncms.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.Views.Feeds;
import com.doublep.emoncms.app.models.FeedDetails;

import java.util.ArrayList;


public class AdapterFeeds extends ArrayAdapter<FeedDetails> {
    private static final String TAG = "AdapterFeeds";
    private final ArrayList<FeedDetails> items;
    private final Context context;

    private BtnChartListener mClickListener = null;

    public AdapterFeeds(Context context, int textViewResourceId,
                        ArrayList<FeedDetails> items, BtnChartListener listener) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
        mClickListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (MainActivity.DEBUG) Log.i(TAG, "+++ getView() called! +++");
        View row = convertView;
        FeedHolder holder = null;
        if (row == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = vi.inflate(R.layout.feed_list_detail, parent, false);

            Button btn = (Button) row.findViewById(R.id.btnChart);
            btn.setTag(position);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(mClickListener != null)
                        mClickListener.onBtnClick((Integer) v.getTag());
                }
            });

            FeedHolder feedHolder = new FeedHolder();
            feedHolder.txtFeedName = (TextView) row.findViewById(R.id.textView_name);
            feedHolder.txtFeedTag = (TextView) row.findViewById(R.id.textView_tag);
            feedHolder.txtFeedValue = (TextView) row.findViewById(R.id.textView_value);
            feedHolder.txtFeedUpdated = (TextView) row.findViewById(R.id.lastUpdated);
            feedHolder.txtFeedLight = (TextView) row.findViewById(R.id.traffic_light);
            row.setTag(feedHolder);
        }

        FeedDetails feedDetails = items.get(position);
        FeedHolder feedHolder = (FeedHolder) row.getTag();
        feedHolder.txtFeedName.setText(feedDetails.getStrName());
        feedHolder.txtFeedTag.setText(feedDetails.getStrTag());
        feedHolder.txtFeedValue.setText(feedDetails.getStrValue());
        feedHolder.txtFeedUpdated.setText("Updated " + feedDetails.getStrTime() + " Secs Ago");

        int val = Integer.parseInt(feedDetails.getStrTime());
        if (val > 40) {
            feedHolder.txtFeedLight.setBackgroundResource(R.drawable.traffic_light_red);
        }
        else if (val > 20)
        {
            feedHolder.txtFeedLight.setBackgroundResource(R.drawable.traffic_light_amber);
        }
        else {
            feedHolder.txtFeedLight.setBackgroundResource(R.drawable.traffic_light_green);
        }

        return row;

    }

    static class FeedHolder {
        TextView txtFeedName;
        TextView txtFeedTag;
        TextView txtFeedValue;
        TextView txtFeedUpdated;
        TextView txtFeedLight;
    }

    public interface BtnChartListener {
        public abstract void onBtnClick(int position);
    }
}
