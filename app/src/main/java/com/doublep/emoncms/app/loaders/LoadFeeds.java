package com.doublep.emoncms.app.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.doublep.emoncms.app.GetEmonData;
import com.doublep.emoncms.app.MainActivity;

import java.util.ArrayList;

/**
 * Loader to get Feeds
 */
public class LoadFeeds extends AsyncTaskLoader<ArrayList> {


    private final String strURL1;
    private final String strAPI1;
    private static final String TAG = "LoadFeeds";

    public LoadFeeds(Context context, String strURL, String strAPI) {
        super(context);
         strURL1 = strURL;
         strAPI1 = strAPI;

    }


    @SuppressWarnings({})
    public ArrayList loadInBackground() {

        ArrayList mFeedDetails = null;

        try {
            mFeedDetails = GetEmonData.GetFeeds(strURL1, strAPI1);
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