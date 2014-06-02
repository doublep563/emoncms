package com.doublep.emoncms.app.adapters;

import android.content.Context;
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
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.feed_list_detail, null);
        }
        FeedDetails o = items.get(position);
        if (o != null) {
            TextView tvfeed_id = (TextView) v
                    .findViewById(R.id.textView_id);
            TextView tvfeed_userid = (TextView) v
                    .findViewById(R.id.textView_userid);
            TextView tvfeed_name = (TextView) v
                    .findViewById(R.id.textView_name);

            if (tvfeed_id != null) {
                tvfeed_id.setText(o.getStrID());
            }
            if (tvfeed_userid != null) {
                tvfeed_userid.setText(o.getStrUserID());
            }
            if (tvfeed_name != null) {
                tvfeed_name.setText(o.getStrName());
            }
        }
        return v;
    }
}
