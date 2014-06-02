package com.doublep.emoncms.app.loaders;


import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.adapters.AdapterFeeds;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LoadData extends ListActivity implements LoaderManager.LoaderCallbacks<ArrayList> {

    private static final int THE_LOADER = 0x01;
    // JSON Node names
    private static final String FEED_ID = "id";
    private static final String FEED_USERID = "userid";
    private static final String FEED_NAME = "name";
    private static final String FEED_DATATYPE = "datatype";
    private static final String FEED_TAG = "tag";
    private static final String FEED_PUBLIC = "public";
    private static final String FEED_SIZE = "size";
    private static final String FEED_ENGINE = "engine";
    private static final String FEED_TIME = "time";
    private static final String FEED_VALUE = "value";

    private AdapterFeeds mAdapterFeeds;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader);
        getLoaderManager().initLoader(THE_LOADER, null, this).forceLoad();
    }
    @Override
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        SampleLoader loader = new SampleLoader(this);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList list) {
        this.mAdapterFeeds = new AdapterFeeds(this,
                R.layout.club_course_captains, list);
        setListAdapter(this.mAdapterFeeds);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
        final ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(null);
    }

    private static class SampleLoader extends AsyncTaskLoader<ArrayList> {

        public SampleLoader(Context context) {
            super(context);
        }

        public  List loadInBackground()
                {
            List arrClub = new ArrayList();
            BufferedReader in = null;
            String strURL = "http://doublep.dnsd.me/emoncms/";
            String strAPI = "480fa3515ab1294e45a8d4854c1a0784";
            String strFeedList = strURL + "feed/list.json&apikey=" + strAPI ;
            String result = null;
            JSONArray feeds = null;
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
                feeds =  new JSONArray(result);
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

            }
                 catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }



            return feedList;
        }
    }



}