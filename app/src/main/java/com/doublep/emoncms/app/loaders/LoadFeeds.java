package com.doublep.emoncms.app.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.models.FeedDetails;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Loader to get Feeds
 */
public class LoadFeeds extends AsyncTaskLoader<ArrayList> {


    private static final String TAG = "LoadFeeds";
    public static ArrayList<FeedDetails> feedList;
    private final String strURL1;
    private final String strAPI1;

    public LoadFeeds(Context context, String strURL, String strAPI) {
        super(context);
        strURL1 = strURL;
        strAPI1 = strAPI;

    }

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


        feedList = new ArrayList<FeedDetails>();
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
                Date date = new Date(intTime * 1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                String formattedDate = sdf.format(date);

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
                feed.setStrUpdated(formattedDate);
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

        if (MainActivity.DEBUG)
            Log.i(TAG, "+++ GetFeeds() called! +++");
        return feedList;
    }

    @SuppressWarnings({})
    public ArrayList loadInBackground() {

        ArrayList mFeedDetails = null;

        try {
            mFeedDetails = GetFeeds(strURL1, strAPI1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (MainActivity.DEBUG) Log.i(TAG, "+++ loadInBackground() called! +++");
        return mFeedDetails;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public void deliverResult(ArrayList data) {

        if (MainActivity.DEBUG) Log.i(TAG, "+++ deliverResult() called! +++");
        super.deliverResult(data);

    }

}