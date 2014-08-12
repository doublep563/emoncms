package com.doublep.emoncms.app.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.doublep.emoncms.app.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URI;

/**
 * Loader to Validate URL and API fields in Preferences
 */
public class LoadValidate extends AsyncTaskLoader<Bundle> {


    private static final String TAG = "LoadValidate";
    private final String strURL1;
    private final String strAPI1;

    public LoadValidate(Context context, String strURL, String strAPI) {
        super(context);
         strURL1 = strURL;
         strAPI1 = strAPI;

    }

    public static Bundle Validate(String strURL, String strAPI) {
        //TODO Fix in Preferences Setup

        if (MainActivity.DEBUG) Log.i(TAG, "Before white space URL is " + strURL);
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

    @SuppressWarnings({})
    public Bundle loadInBackground() {

        Bundle mValidate = null;

        try {
            mValidate = Validate(strURL1, strAPI1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (MainActivity.DEBUG) Log.i(TAG, "+++ loadInBackground() called! +++");
        return mValidate;
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