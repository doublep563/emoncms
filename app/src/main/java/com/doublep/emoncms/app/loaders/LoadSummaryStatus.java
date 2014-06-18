package com.doublep.emoncms.app.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.doublep.emoncms.app.GetEmonData;
import com.doublep.emoncms.app.MainActivity;

import java.util.ArrayList;

/**
 * Loader to get Summary Status
 */
public class LoadSummaryStatus extends AsyncTaskLoader<ArrayList> {


    private final String strURL;
    private final String strAPI;

    public LoadSummaryStatus(Context context, String strURL, String strAPI) {
        super(context);
        this.strURL = strURL;
        this.strAPI = strAPI;

    }


    @SuppressWarnings({})
    public ArrayList loadInBackground() {

        ArrayList SUMMARY_STATUS = null;

        try {
            //TODO LoadSummaryStatus needs to Check Preferences to see what should be checked.
            SUMMARY_STATUS = GetEmonData.GetStatus(strURL, strAPI);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (MainActivity.DEBUG) {
            Log.i("LoadSummaryStatus", "LoadSummaryStatus loadInBackground completed");
        }
        return SUMMARY_STATUS;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public void deliverResult(ArrayList data) {

        if (MainActivity.DEBUG) {
            Log.i("LoadSummaryStatus", "LoadSummaryStatus deliverResult completed");
        }
        super.deliverResult(data);

    }

}