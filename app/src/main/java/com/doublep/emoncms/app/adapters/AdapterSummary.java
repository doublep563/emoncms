package com.doublep.emoncms.app.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.Views.Summary;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.models.SummaryStatus;

import java.util.ArrayList;


public class AdapterSummary extends ArrayAdapter<SummaryStatus> {
    //TODO Change to SimpleAdapter. This class does nos use a real ListView.

    private static final String TAG = "AdapterSummary";
    private final ArrayList<SummaryStatus> items;
    private final Context context;

    public AdapterSummary(Context context,
                          ArrayList<SummaryStatus> items) {
        super(context, R.layout.summary, items);
        this.items = items;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.summary, parent, false);
        }
        // URL Table Row
        TableRow trURL = (TableRow) v.findViewById(R.id.trURL);
        trURL.setTag(0);
        trURL.setOnClickListener(new Summary.TableRowClickListener() {
        });
        // Feeds Table Row
        TableRow trFeeds = (TableRow) v.findViewById(R.id.trFeeds);
        trFeeds.setTag(1);
        trFeeds.setOnClickListener(new Summary.TableRowClickListener() {
        });


        SummaryStatus o = items.get(0);


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String strEmoncmsURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, getContext().getResources().getString(R.string.pref_default));
        String strRaspPiStatus = o.getStrRaspPiStatus();
        String strGoodFeeds = o.getStrFeedsGood();
        String strBadFeeds = o.getStrFeedsBad();
        String strURLStatus = o.getURLStatus();
        String strFeedsTotal = o.getStrFeedsTotal();


        assert v != null;
        TextView t = (TextView) v.findViewById(R.id.txtErrorText);
        t.setText(strEmoncmsURL);

        TextView u = (TextView) v.findViewById(R.id.URLStatus);

        if (!strURLStatus.equalsIgnoreCase("OK")) {
            u.setBackgroundResource(R.drawable.traffic_light_red);
        }

        TextView f = (TextView) v.findViewById(R.id.FeedStatus);

        if (strFeedsTotal.equalsIgnoreCase("0")) {
            f.setBackgroundResource(R.drawable.traffic_light_red);
        }
        if (!strBadFeeds.equalsIgnoreCase("0")) {
            f.setBackgroundResource(R.drawable.traffic_light_red);
        }

        if (!strRaspPiStatus.equalsIgnoreCase("Not Set")) {
            TextView tv3 = (TextView) v.findViewById(R.id.textView3);
            TextView tv5 = (TextView) v.findViewById(R.id.textView5);
            tv5.setText(strRaspPiStatus);
            tv3.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);
        }


        //TextView tv8 = (TextView) v.findViewById(R.id.textView8);
        //tv8.setText(strGoodFeeds);

        //TextView tv10 = (TextView) v.findViewById(R.id.textView10);
        //tv10.setText(strBadFeeds);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ getView() called! +++");

        return v;
    }


}
