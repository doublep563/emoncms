package com.doublep.emoncms.app.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.doublep.emoncms.app.GetEmonData;
import com.doublep.emoncms.app.MainActivity;

/**
 * Loader to get Feed Chart Data
 */
public class LoadFeedChart extends AsyncTaskLoader<Bundle> {


    private final String mFeedID;
    private static final String TAG = "LoadFeedChart";
    private final String mURL;
    private final String mAPI;

    public LoadFeedChart(Context context, String strEmoncmsURL, String strEmoncmsAPI, String strFeedID) {
        super(context);
        mFeedID = strFeedID;
        mURL = strEmoncmsURL;
        mAPI = strEmoncmsAPI;


    }


    @SuppressWarnings({})
    public Bundle loadInBackground() {

        Bundle mFeedData = null;

        try {
            mFeedData = GetEmonData.GetFeedData(mURL, mAPI, mFeedID);
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