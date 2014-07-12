package com.doublep.emoncms.app.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.doublep.emoncms.app.GetEmonData;
import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.models.FeedDetails;

import java.util.ArrayList;

/**
 * Created by Paul Patchell on 08/07/2014.
 */
public class AdapterFeedsExpand extends Fragment {
    private static final String TAG = "AdapterFeedsExpand";
    public LayoutInflater inflater;
    public Activity activity;
    OnFeedListener mListener;
    private ArrayList<FeedDetails> items = null;
    private ExpandableListView elv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items = GetEmonData.feedList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.feed_list, null);
        elv = (ExpandableListView) v.findViewById(R.id.listView);
        elv.setAdapter(new AdapterFeedsExpandable());
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateView() called! +++");
        return v;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (OnFeedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    public interface OnFeedListener {

        public void onFeedSelected(String feedID, String strFeedTag, String strFeedID);

    }

    public class AdapterFeedsExpandable extends BaseExpandableListAdapter {

        public AdapterFeedsExpandable() {

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
            LayoutInflater view = getActivity().getLayoutInflater();

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
                convertView = view.inflate(R.layout.feed_list_detail_child, null);
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
                LayoutInflater view = getActivity().getLayoutInflater();

                row = view.inflate(R.layout.feed_list_detail, parent, false);

                feedHolder = new FeedHolder();
                feedHolder.txtFeedName = (TextView) row.findViewById(R.id.textView_name);
                feedHolder.txtFeedTag = (TextView) row.findViewById(R.id.textView_tag);
                feedHolder.txtFeedValue = (TextView) row.findViewById(R.id.textView_value);
                feedHolder.txtFeedUpdated = (TextView) row.findViewById(R.id.lastUpdated);
                feedHolder.txtFeedLight = (TextView) row.findViewById(R.id.traffic_light);
                feedHolder.btn = (Button) row.findViewById(R.id.btnChart);
                feedHolder.btn.setTag(groupPosition);


                feedHolder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String btnTag = feedHolder.btn.getTag().toString();
                        int intTag = Integer.parseInt(btnTag);
                        FeedDetails feedDetails = items.get(intTag);
                        String strFeedID = feedDetails.getStrID();
                        String strFeedTag = feedDetails.getStrTag();
                        String strFeedName = feedDetails.getStrName();
                        if (mListener != null) {
                            mListener.onFeedSelected(strFeedID, strFeedTag, strFeedName);
                        }

                    }
                });
                row.setTag(feedHolder);

                if (MainActivity.DEBUG) Log.i(TAG, "+++ getGroupView() row == null called! +++");
            } else {
                feedHolder = (FeedHolder) convertView.getTag();
                feedHolder.btn.setTag(groupPosition);

                if (MainActivity.DEBUG) Log.i(TAG, "+++ getGroupView() row not null called! +++");
            }
            FeedDetails feedDetails = items.get(groupPosition);

            feedHolder.txtFeedName.setText(feedDetails.getStrName());
            feedHolder.txtFeedTag.setText(feedDetails.getStrTag());
            feedHolder.txtFeedValue.setText(feedDetails.getStrValue());
            feedHolder.txtFeedUpdated.setText("Updated " + feedDetails.getStrTime() + " Secs Ago");

            int val = Integer.parseInt(feedDetails.getStrTime());
            if (val > 40) {
                feedHolder.txtFeedLight.setBackgroundResource(R.drawable.traffic_light_red);
            } else if (val > 20) {
                feedHolder.txtFeedLight.setBackgroundResource(R.drawable.traffic_light_amber);
            } else {
                feedHolder.txtFeedLight.setBackgroundResource(R.drawable.traffic_light_green);
            }
            if (MainActivity.DEBUG) Log.i(TAG, "+++ getGroupView() called! +++");
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
            TextView txtFeedValue;
            TextView txtFeedUpdated;
            TextView txtFeedLight;
            Button btn;


        }


    }

}