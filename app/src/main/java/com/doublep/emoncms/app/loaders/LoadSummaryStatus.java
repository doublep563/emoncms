package com.doublep.emoncms.app.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.models.SummaryStatus;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Loader to get Summary Status
 */
public class LoadSummaryStatus extends AsyncTaskLoader<ArrayList> {

    private static final String TAG = "AdapterSummary";

    private boolean data;
    private ArrayList SUMMARY_STATUS = null;

    public LoadSummaryStatus(Context context) {
        super(context);
        data = false;


    }

    public static ArrayList<SummaryStatus> GetStatus(Context context) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String strURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, context.getResources().getString(R.string.pref_default));
        String strAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, context.getResources().getString(R.string.pref_default));
        boolean raspberryPI = sharedPref.getBoolean(common.PREF_KEY_RASPBERRYPI, false);

        int responseCode = 0;


        ArrayList<SummaryStatus> summaryList = new ArrayList<SummaryStatus>();

        String RASPBERRY_PI_STATUS = context.getResources().getString(R.string.pref_default);
        int FEEDS_GOOD = 0;
        int FEEDS_BAD = 0;
        String URLStatus = "OK";

        String FEED_TIME = "time";
        //TODO Fix in Preferences Setup
        strURL = strURL.replace("\n", "");

        String strRaspURL = strURL + "/raspberrypi/getrunning.json&apikey=" + strAPI;
        String strFeedList = strURL + "/feed/list.json&apikey=" + strAPI;

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(strURL).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
            //Without this, getting EOFexceptions. No idea why it works.
            connection.addRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestMethod("HEAD");

            responseCode = connection.getResponseCode();
        } catch (IOException e) {
            URLStatus = "Not Available";
            e.printStackTrace();
        }

        if (responseCode != 200) {
            URLStatus = "Not Available";

            // Not OK.
        }


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
        int FEEDS_TOTAL = FEEDS_GOOD + FEEDS_BAD;
        SummaryStatus summaryStatus = new SummaryStatus();
        summaryStatus.setStrRaspPiStatus(RASPBERRY_PI_STATUS);
        summaryStatus.setStrFeedsGood(Integer.toString(FEEDS_GOOD));
        summaryStatus.setStrFeedsBad(Integer.toString(FEEDS_BAD));
        summaryStatus.setStrFeedsTotal(Integer.toString(FEEDS_TOTAL));
        summaryStatus.setURLStatus(URLStatus);
        summaryList.add(summaryStatus);
        return summaryList;
    }

    @SuppressWarnings({})
    public ArrayList loadInBackground() {


        Context mContext;
        mContext = getContext();

        try {

            SUMMARY_STATUS = GetStatus(mContext);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (MainActivity.DEBUG) {
            Log.i("LoadSummaryStatus", "+++ loadInBackground completed! +++");
        }
        data = true;

        return SUMMARY_STATUS;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        //FIXME This is causing the loader to refresh data. What's calling it????
        // Will this work? Will it ever deliver new data?
        //Looks like it does!!!!
        //TEST
        if (data) {
            deliverResult(SUMMARY_STATUS);
            Log.i("LoadSummaryStatus", "+++ onStartLoading we have data completed! +++");
        } else {
            forceLoad();
        }
        if (MainActivity.DEBUG) {
            Log.i("LoadSummaryStatus", "+++ onStartLoading completed! +++");
        }
    }

    @Override
    public void deliverResult(ArrayList data) {

        if (MainActivity.DEBUG) {
            Log.i("LoadSummaryStatus", "+++ deliverResult completed! +++");
        }
        super.deliverResult(data);

    }

}