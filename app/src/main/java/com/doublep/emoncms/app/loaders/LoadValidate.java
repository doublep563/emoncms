package com.doublep.emoncms.app.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.doublep.emoncms.app.GetEmonData;
import com.doublep.emoncms.app.MainActivity;

/**
 * Loader to Validate URL and API fields in Preferences
 */
public class LoadValidate extends AsyncTaskLoader<Bundle> {


    private final String strURL1;
    private final String strAPI1;
    private static final String TAG = "LoadValidate";

    public LoadValidate(Context context, String strURL, String strAPI) {
        super(context);
         strURL1 = strURL;
         strAPI1 = strAPI;

    }


    @SuppressWarnings({})
    public Bundle loadInBackground() {

       Bundle mValidate = null;

        try {
            mValidate = GetEmonData.Validate(strURL1, strAPI1);
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