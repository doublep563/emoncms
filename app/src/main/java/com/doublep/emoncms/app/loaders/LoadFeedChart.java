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
public class LoadFeedChart extends AsyncTaskLoader<ArrayList> {


    private final String mFeedID;
    private static final String TAG = "LoadFeedChart";

    public LoadFeedChart(Context context, String strFeedID) {
        super(context);
        mFeedID = strFeedID;


    }


    @SuppressWarnings({})
    public ArrayList loadInBackground() {

        ArrayList mFeedData = null;

        try {
            mFeedData = GetEmonData.GetFeedData(mFeedID);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (MainActivity.DEBUG) {
            if (MainActivity.DEBUG) Log.i(TAG, "+++ loadInBackground() called! +++");
        }
        return mFeedData;
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