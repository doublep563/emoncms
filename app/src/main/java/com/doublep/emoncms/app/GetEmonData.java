package com.doublep.emoncms.app;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;


public class GetEmonData {

    public static ArrayList GetFeeds(String strURL, String strAPI)
            throws Exception {
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


        strURL = "http://doublep.dnsd.me/emoncms/";
        strAPI = "480fa3515ab1294e45a8d4854c1a0784";
        String strFeedList = strURL + "feed/list.json&apikey=" + strAPI;
        BufferedReader in;
        String result ;
        JSONArray feeds ;
        ArrayList<HashMap<String, String>> feedList = null;

        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(strFeedList));


            HttpResponse response = client.execute(request);

            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
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


                // tmp hashmap for single contact
                HashMap<String, String> feed = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                feed.put(FEED_ID, id);
                feed.put(FEED_USERID, userid);
                feed.put(FEED_NAME, name);
                feed.put(FEED_DATATYPE, datatype);
                feed.put(FEED_TAG, tag);
                feed.put(FEED_PUBLIC, strPublic);
                feed.put(FEED_SIZE, size);
                feed.put(FEED_ENGINE, engine);
                feed.put(FEED_TIME, time);
                feed.put(FEED_VALUE, value);

                // adding contact to contact list
                feedList.add(feed);
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }


        return feedList;
    }
}





