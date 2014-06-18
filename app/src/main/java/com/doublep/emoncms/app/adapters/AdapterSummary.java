package com.doublep.emoncms.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.Views.Summary;
import com.doublep.emoncms.app.models.SummaryStatus;

import java.util.ArrayList;


public class AdapterSummary extends ArrayAdapter<SummaryStatus> {

    private static final String TAG = "AdapterSummary";
    private final ArrayList<SummaryStatus> items;
    private final Context context;

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
            v = vi.inflate(R.layout.summary, parent , false);
        }
        SummaryStatus o = items.get(0);


        //TODO LoadSummaryStatus needs to Check Preferences to see what should be checked.
        //TODO Views will be invisible or gone.
        String strEmoncmsURL = Summary.strEmoncmsURL;
        String strRaspPiStatus = o.getStrRaspPiStatus();
        String strGoodFeeds = o.getStrFeedsGood();
        String strBadFeeds = o.getStrFeedsBad();

        assert v != null;
        TextView t = (TextView) v.findViewById(R.id.txtErrorText);
        t.setText(strEmoncmsURL);

        TextView tv5 = (TextView) v.findViewById(R.id.textView5);
        tv5.setText(strRaspPiStatus);

        TextView tv8 = (TextView) v.findViewById(R.id.textView8);
        tv8.setText(strGoodFeeds);

        TextView tv10 = (TextView) v.findViewById(R.id.textView10);
        tv10.setText(strBadFeeds);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ getView() called! +++");

        return v;
    }
}
