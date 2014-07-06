package com.doublep.emoncms.app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.models.FeedDetails;

import java.util.ArrayList;


public class AdapterFeeds extends ArrayAdapter<FeedDetails> {

    private final ArrayList<FeedDetails> items;
    private final Context context;

    public AdapterFeeds(Context context, int textViewResourceId,
                        ArrayList<FeedDetails> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FeedHolder holder = null;
        if (row == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = vi.inflate(R.layout.feed_list_detail, parent, false);

            FeedHolder feedHolder = new FeedHolder();
            feedHolder.txtFeedName = (TextView) row.findViewById(R.id.textView_name);
            feedHolder.txtFeedTag = (TextView) row.findViewById(R.id.textView_tag);
            feedHolder.txtFeedValue = (TextView) row.findViewById(R.id.textView_value);
            feedHolder.txtFeedUpdated = (TextView) row.findViewById(R.id.lastUpdated);
            row.setTag(feedHolder);
        }

        FeedDetails feedDetails = items.get(position);
        FeedHolder feedHolder = (FeedHolder) row.getTag();
        feedHolder.txtFeedName.setText(feedDetails.getStrName());
        feedHolder.txtFeedTag.setText(feedDetails.getStrTag());
        feedHolder.txtFeedValue.setText(feedDetails.getStrValue());
        feedHolder.txtFeedUpdated.setText("Updated " + feedDetails.getStrTime() + " Secs Ago");

        int val = Integer.parseInt(feedDetails.getStrTime());
        if (val > 120) {
            feedHolder.txtFeedUpdated.setTextColor(context.getResources().getColor(R.color.holo_orange_light));
        }
        else {
            feedHolder.txtFeedUpdated.setTextColor(Color.BLACK);
        }

        return row;
    }

    static class FeedHolder {
        TextView txtFeedName;
        TextView txtFeedTag;
        TextView txtFeedValue;
        TextView txtFeedUpdated;
    }
}
