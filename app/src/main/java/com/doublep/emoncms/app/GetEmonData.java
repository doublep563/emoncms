package com.doublep.emoncms.app;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.doublep.emoncms.app.models.FeedData;
import com.doublep.emoncms.app.models.FeedDetails;
import com.doublep.emoncms.app.models.SummaryStatus;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class GetEmonData{

    private static final String TAG = "GetEmonData";
    private static Context mContext ;


    public static ArrayList<FeedDetails> GetFeeds(String strURL, String strAPI) {
        // JSON Node names
        String FEED_ID = "id";
        String FEED_USERID = "userid";
        String FEED_NAME = "name";
        String FEED_DATATYPE = "datatype";
        String FEED_TAG = "tag";
        String FEED_PUBLIC = "public";
        String FEED_SIZE = "size";
        String FEED_ENGINE = "engine";
        String FEED_TIME = "time";
        String FEED_VALUE = "value";


        strURL = strURL.replace("\n", "");
        String strFeedList = strURL + "/feed/list.json&apikey=" + strAPI;
        BufferedReader in;
        String result;
        JSONArray feeds;





        ArrayList<FeedDetails> feedList = new ArrayList<FeedDetails>();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(strFeedList));


            HttpResponse response = client.execute(request);

            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));
            StringBuilder sb = new StringBuilder("");
            String line;
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line).append(NL);
            }
            in.close();
            result = sb.toString();
            feeds = new JSONArray(result);
            for (int i = 0; i < feeds.length(); i++) {
                JSONObject c = feeds.getJSONObject(i);

                String id = c.getString(FEED_ID);
                String userid = c.getString(FEED_USERID);
                String name = c.getString(FEED_NAME);
                String datatype = c.getString(FEED_DATATYPE);
                String tag = c.getString(FEED_TAG);
                String strPublic = c.getString(FEED_PUBLIC);
                String size = c.getString(FEED_SIZE);
                String engine = c.getString(FEED_ENGINE);
                String time = c.getString(FEED_TIME);
                String value = c.getString(FEED_VALUE);

                // Calculate Last Updated Value
                int intTime = Integer.parseInt(time);
                long unixTime = System.currentTimeMillis() / 1000L;
                int myTime = (int) unixTime - intTime;


                // tmp hashmap for feed details
                FeedDetails feed = new FeedDetails();
                feed.setStrDataType(datatype);
                feed.setStrEngine(engine);
                feed.setStrID(id);
                feed.setStrName(name);
                feed.setStrPublic(strPublic);
                feed.setStrSize(size);
                feed.setStrTag(tag);
                feed.setStrTime(Integer.toString(myTime));
                feed.setStrUserID(userid);
                feed.setStrValue(value);
                // adding each child node to HashMap key => value

                // adding contact to contact list
                feedList.add(feed);
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        Collections.sort(feedList, new Comparator<FeedDetails>() {
            @Override
            public int compare(FeedDetails fd1, FeedDetails fd2) {

                return fd1.getStrName().compareTo(fd2.getStrName());
            }
        });


        return feedList;
    }

    public static ArrayList<SummaryStatus> GetStatus(Context context) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String strURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, context.getResources().getString(R.string.pref_default));
        String strAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, context.getResources().getString(R.string.pref_default));
        boolean raspberryPI = sharedPref.getBoolean(common.PREF_KEY_RASPBERRYPI, false);


        ArrayList<SummaryStatus> summaryList = new ArrayList<SummaryStatus>();

        String RASPBERRY_PI_STATUS = context.getResources().getString(R.string.pref_default);
        int FEEDS_GOOD = 0;
        int FEEDS_BAD = 0;

        String FEED_TIME = "time";
        //TODO Fix in Preferences Setup
        strURL = strURL.replace("\n", "");

        String strRaspURL = strURL + "/raspberrypi/getrunning.json&apikey=" + strAPI;
        String strFeedList = strURL + "/feed/list.json&apikey=" + strAPI;



        if (raspberryPI) {


            try {

                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(strRaspURL));


                HttpResponse response = client.execute(request);

                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent()));
                StringBuilder sb = new StringBuilder("");
                String line;
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line).append(NL);
                }
                in.close();
                String result = sb.toString();
                result = result.replace("\n", "");
                RASPBERRY_PI_STATUS = result;


            } catch (Exception e) {
                //TODO Handle Error with Dialog to User Immediately
                //TODO Many errors occur because of this
                Log.d("InputStream", e.getLocalizedMessage());
            }

            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ GetStatus() raspberryPI called! +++");
        }

        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(strFeedList));


            HttpResponse response = client.execute(request);

            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));
            StringBuilder sb = new StringBuilder("");
            String line;
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line).append(NL);
            }
            in.close();
            String result = sb.toString();
            JSONArray feeds = new JSONArray(result);

            for (int i = 0; i < feeds.length(); i++) {
                JSONObject c = feeds.getJSONObject(i);


                String time = c.getString(FEED_TIME);


                // Calculate Last Updated Value
                int intTime = Integer.parseInt(time);
                long unixTime = System.currentTimeMillis() / 1000L;
                int myTime = (int) unixTime - intTime;
                //TODO Externalise 120 to Preferences
                if (myTime > 120) {
                    FEEDS_BAD = FEEDS_BAD + 1;
                } else FEEDS_GOOD = FEEDS_GOOD + 1;

            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        if (MainActivity.DEBUG)
            Log.i(TAG, "+++ GetStatus() Summary called! +++");

        SummaryStatus summaryStatus = new SummaryStatus();
        summaryStatus.setStrRaspPiStatus(RASPBERRY_PI_STATUS);
        summaryStatus.setStrFeedsGood(Integer.toString(FEEDS_GOOD));
        summaryStatus.setStrFeedsBad(Integer.toString(FEEDS_BAD));
        summaryList.add(summaryStatus);
        return summaryList;
    }

    public static Bundle Validate(String strURL, String strAPI) {
        if (MainActivity.DEBUG) Log.i(TAG, "Before white space URL is " + strURL);
        //TODO Fix in Preferences Setup
        strURL = strURL.replace("\n", "");
        if (MainActivity.DEBUG) Log.i(TAG, "After white space URL is " + strURL);
        String strFeedList = strURL + "/feed/list.json&apikey=" + strAPI;


        Bundle mBundle = null;
        try {
            mBundle = new Bundle();
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(strFeedList));

            HttpResponse response = client.execute(request);
            //String responseBody = EntityUtils.toString(response.getEntity());

            // If there is an error, catch it below and return in the bundle

        } catch (ClientProtocolException e) {
            mBundle.putString("ClientProtocolException", e.getLocalizedMessage());
            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ Validate() ClientProtocolException called! +++" + e.getLocalizedMessage());
        } catch (Exception e) {

            assert mBundle != null;
            mBundle.putString("Exception", e.getLocalizedMessage());
            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ Validate() Exception called! +++" + e.getLocalizedMessage());
        }

        if (MainActivity.DEBUG) Log.i(TAG, "+++ Validate() called! +++");

        return mBundle;

    }

    public static Bundle GetFeedData(String strURL, String strAPI, String strFeedID) {

        ArrayList<FeedData> feedData = new ArrayList<FeedData>();
        // Take 2 minutes off the end time to ensure that data exists in the database
        long endTime = System.currentTimeMillis() - 120000;

        long startTime = endTime - 86400000;
        JSONArray feedArray;
        //TODO Fix in Preferences Setup
        strURL = strURL.replace("\n", "");

        String strFeedURL = strURL + "/feed/data.json&apikey=" + strAPI + "?id=" + strFeedID + "&start=" + startTime + "&end=" + endTime + "&dp=800";

        if (MainActivity.DEBUG) Log.i(TAG, "+++ GetFeedData() strFeedURL is " + strFeedURL);


        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(strFeedURL));

            HttpResponse response = client.execute(request);

            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));
            StringBuilder sb = new StringBuilder("");
            String line;
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line).append(NL);
            }
            in.close();
            String result = sb.toString();
            //TODO Need to handle null data in here in cases where the query to database returns
            //TODO no values
            feedArray = new JSONArray(result);
            for (int i = 0; i < feedArray.length(); i++) {
                //JSONObject c = feedArray.getJSONObject(i);
                long mTime = feedArray.getJSONArray(i).getLong(0);
                double mData = feedArray.getJSONArray(i).getDouble(1);
                FeedData fData = new FeedData();

                fData.setFeedTime(mTime);
                fData.setFeedData(mData);
                feedData.add(fData);

            }

        } catch (Exception e) {
            //TODO Handle Error with Dialog to User
            Log.d("InputStream", e.getLocalizedMessage());
        }

        if (MainActivity.DEBUG) Log.i(TAG, "+++ GetFeedData() called! +++");
        Bundle mBundle = new Bundle();
        mBundle.putParcelableArrayList("feedData", feedData);

        return mBundle;

    }


}





