package com.doublep.emoncms.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.doublep.emoncms.app.GetEmonData;
import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.models.FeedDetails;
import com.doublep.emoncms.app.views.Feeds;

import java.util.ArrayList;

/**
 * Created by Paul Patchell on 08/07/2014.
 */
public class AdapterFeedsExpand extends BaseExpandableListAdapter {

    private static final String TAG = "AdapterFeedsExpand";
    private final ArrayList<FeedDetails> items;
    private final Context mContext;


    public AdapterFeedsExpand(Context context) {
        items = GetEmonData.feedList;
        mContext = context;

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ getChild() called! +++");
        return items.get(groupPosition);

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ getChildId() called! +++");
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater view = (LayoutInflater) mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        FeedDetails feedDetails = items.get(groupPosition);
        String txtFeedID = feedDetails.getStrID();
        String txtFeedName = feedDetails.getStrName();
        String txtFeedTag = feedDetails.getStrTag();
        String txtFeedUserid = feedDetails.getStrUserID();
        String txtFeedDataType = feedDetails.getStrDataType();
        String txtFeedEngine = feedDetails.getStrEngine();
        String txtFeedPublic = feedDetails.getStrPublic();
        String txtFeedSize = feedDetails.getStrSize();
        String txtFeedUpdated = feedDetails.getStrUpdated();
        String txtFeedValue = feedDetails.getStrValue();


        if (convertView == null) {
            convertView = view.inflate(R.layout.feed_list_detail_child, parent);
        }
        TextView tvFeedID = (TextView) convertView.findViewById(R.id.textView11);
        TextView tvFeedName = (TextView) convertView.findViewById(R.id.textView12);
        TextView tvFeedTag = (TextView) convertView.findViewById(R.id.textView13);
        TextView tvFeedUserid = (TextView) convertView.findViewById(R.id.textView14);
        TextView tvFeedDataType = (TextView) convertView.findViewById(R.id.textView15);
        TextView tvFeedEngine = (TextView) convertView.findViewById(R.id.textView16);
        TextView tvFeedPublic = (TextView) convertView.findViewById(R.id.textView17);
        TextView tvFeedSize = (TextView) convertView.findViewById(R.id.textView18);
        TextView tvFeedUpdated = (TextView) convertView.findViewById(R.id.textView19);
        TextView tvFeedValue = (TextView) convertView.findViewById(R.id.textView20);

        tvFeedID.setText((txtFeedID));
        tvFeedName.setText(txtFeedName);
        tvFeedTag.setText(txtFeedTag);
        tvFeedUserid.setText(txtFeedUserid);
        tvFeedDataType.setText(txtFeedDataType);
        tvFeedEngine.setText(txtFeedEngine);
        tvFeedPublic.setText(txtFeedPublic);
        tvFeedSize.setText(txtFeedSize);
        tvFeedUpdated.setText(txtFeedUpdated);
        tvFeedValue.setText(txtFeedValue);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ getChildView() called! +++");
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ getChildrenCount() called! +++");
        //return items.size();
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ getGroup() called! +++");
        return items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ getGroupCount() called! +++");
        return items.size();

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onGroupCollapsed() called! +++");
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

        super.onGroupExpanded(groupPosition);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onGroupExpanded() called! +++");
    }

    @Override
    public long getGroupId(int groupPosition) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ getGroupId() called! +++");
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View row = convertView;
        final FeedHolder feedHolder;

        if (row == null) {

            LayoutInflater view = (LayoutInflater) mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);


            row = view.inflate(R.layout.feed_list_detail, parent, false);

            feedHolder = new FeedHolder();
            feedHolder.txtFeedName = (TextView) row.findViewById(R.id.textView_name);
            feedHolder.txtFeedTag = (TextView) row.findViewById(R.id.textView_tag);
            feedHolder.btnFeedValue = (Button) row.findViewById(R.id.button_value);
            feedHolder.btnFeedValue.setTag(groupPosition);
            //feedHolder.viewPlusSign =  row.findViewById(R.id.plus_sign);
            feedHolder.viewVerticalLine = row.findViewById(R.id.vertical_line);

            feedHolder.btnFeedValue.setOnClickListener(new Feeds.OnClickListener() {

            });

            row.setTag(feedHolder);

            if (MainActivity.DEBUG) Log.i(TAG, "+++ getGroupView() row == null called! +++");
        } else {
            feedHolder = (FeedHolder) convertView.getTag();
            feedHolder.btnFeedValue.setTag(groupPosition);

            if (MainActivity.DEBUG) Log.i(TAG, "+++ getGroupView() row not null called! +++");
        }
        FeedDetails feedDetails = items.get(groupPosition);

        feedHolder.txtFeedName.setText(feedDetails.getStrName());
        feedHolder.txtFeedTag.setText(feedDetails.getStrTag());
        feedHolder.btnFeedValue.setText(feedDetails.getStrValue());

        int val = Integer.parseInt(feedDetails.getStrTime());
        if (val > 120) {
            feedHolder.btnFeedValue.setBackgroundResource(R.drawable.traffic_light_red);
        } else if (val > 60) {
            feedHolder.btnFeedValue.setBackgroundResource(R.drawable.traffic_light_amber);
        } else {
            feedHolder.btnFeedValue.setBackgroundResource(R.drawable.traffic_light_green);
        }
        if (MainActivity.DEBUG) Log.i(TAG, "+++ getGroupView() called! +++");

        if (isExpanded) {
            feedHolder.viewVerticalLine.setVisibility(View.INVISIBLE);
            row.setBackgroundResource(R.color.emoncms_pale);
        } else {
            feedHolder.viewVerticalLine.setVisibility(View.VISIBLE);
            row.setBackgroundResource(R.color.White);
        }


        return row;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class FeedHolder {
        TextView txtFeedName;
        TextView txtFeedTag;
        Button btnFeedValue;
        //View viewPlusSign;
        View viewVerticalLine;
        //TextView txtFeedUpdated;
        //TextView txtFeedLight;
        //Button btn;


    }


}