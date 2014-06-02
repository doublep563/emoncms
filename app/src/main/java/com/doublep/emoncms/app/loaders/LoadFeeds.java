package com.doublep.emoncms.app.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;

import android.util.Log;

import com.doublep.emoncms.app.GetEmonData;
import com.doublep.emoncms.app.MainActivity;

import java.util.ArrayList;

/**
 * Created by Paul Patchell on 02/06/2014.
 */
public class LoadFeeds extends AsyncTaskLoader<ArrayList> {


    private String strURL;
    private String strAPI;

    public LoadFeeds(Context context, String strURL, String strAPI) {
        super(context);
        this.strURL = strURL;
        this.strAPI = strAPI;

    }


    @SuppressWarnings({})
    public ArrayList loadInBackground() {

        ArrayList mFeedDetails = null;

        try {
            mFeedDetails = GetEmonData.GetFeeds(strURL, strAPI);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (MainActivity.DEBUG) {
            Log.i("MyActivity", "LoadFeeds loadInBackground completed");
        }
        return mFeedDetails;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public void deliverResult(ArrayList data) {

        if (MainActivity.DEBUG) {
            Log.i("MyActivity", "LoadFeeds deliverResult completed");
        }
        super.deliverResult(data);

    }

}