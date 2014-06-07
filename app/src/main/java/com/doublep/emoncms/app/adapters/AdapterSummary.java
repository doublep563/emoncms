package com.doublep.emoncms.app.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.Views.Summary;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.models.FeedDetails;
import com.doublep.emoncms.app.models.SummaryStatus;

import java.util.ArrayList;


public class AdapterSummary extends ArrayAdapter<SummaryStatus> {

    private final ArrayList<SummaryStatus> items;
    private final Context context;
    private String strRaspPiStatus;
    private String strGoodFeeds;
    private String strBadFeeds;
    private String strEmoncmsURL;

    public AdapterSummary(Context context, int textViewResourceId,
                          ArrayList<SummaryStatus> items) {
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
            v = vi.inflate(R.layout.summary, null);
        }
        SummaryStatus o = items.get(0);

        //SummaryStatus status = (SummaryStatus) summaryStatus.get(0);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        strEmoncmsURL = Summary.strEmoncmsURL;
        strRaspPiStatus =  o.getStrRaspPiStatus();
        strGoodFeeds = o.getStrFeedsGood();
        strBadFeeds = o.getStrFeedsBad();

        if (o != null) {
            TextView t = (TextView) v.findViewById(R.id.textView2);
            t.setText(strEmoncmsURL);

            TextView tv5 = (TextView) v.findViewById(R.id.textView5);
            tv5.setText(strRaspPiStatus);

            TextView tv8 = (TextView) v.findViewById(R.id.textView8);
            tv8.setText(strGoodFeeds);

            TextView tv10 = (TextView) v.findViewById(R.id.textView10);
            tv10.setText(strBadFeeds);
        }
        return v;
    }
}
