package com.doublep.emoncms.app.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.models.FeedData;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

/**
 * Loader to get Feed Chart Data
 */
public class LoadFeedChart extends AsyncTaskLoader<Bundle> {


    private static final String TAG = "LoadFeedChart";
    private final String mFeedID;
    private final String mURL;
    private final String mAPI;

    public LoadFeedChart(Context context, String strEmoncmsURL, String strEmoncmsAPI, String strFeedID) {
        super(context);
        mFeedID = strFeedID;
        mURL = strEmoncmsURL;
        mAPI = strEmoncmsAPI;


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

    @SuppressWarnings({})
    public Bundle loadInBackground() {

        Bundle mFeedData = null;

        try {
            mFeedData = GetFeedData(mURL, mAPI, mFeedID);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        if (MainActivity.DEBUG) Log.i(TAG, "+++ loadInBackground() called! +++");

        return mFeedData;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public void deliverResult(Bundle data) {

        if (MainActivity.DEBUG) Log.i(TAG, "+++ deliverResult() called! +++");
        super.deliverResult(data);

    }

}