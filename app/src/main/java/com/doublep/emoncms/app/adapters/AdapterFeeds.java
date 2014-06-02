package com.doublep.emoncms.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
            v = vi.inflate(R.layout.club_course_info_list, null);
        }
        feedList o = items.get(position);
        if (o != null) {
            TextView tvClubContactType = (TextView) v
                    .findViewById(R.id.ContactType);
            TextView tvClubContactInfo = (TextView) v
                    .findViewById(R.id.ContactInfo);

            if (tvClubContactType != null) {
                tvClubContactType.setText(o.getContactType());
            }
            if (tvClubContactInfo != null) {
                tvClubContactInfo.setText(o.getContactInfo());
            }

        }
        return v;
    }
}
