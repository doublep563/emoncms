package com.doublep.emoncms.app.Views;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.Preferences;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.loaders.LoadSummaryStatus;
import com.doublep.emoncms.app.models.SummaryStatus;

import java.util.ArrayList;

/**
 * Created by Paul Patchell on 07/06/2014.
 */
public class Summary extends Activity implements
        LoaderManager.LoaderCallbacks<ArrayList> {


    private static final String TAG = "Summary";
    String strEmoncmsURL;
    String strEmoncmsAPI;
    ArrayList summaryStatus;
    private static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //TODO Add API field to Summary.xml
        strEmoncmsURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, getResources().getString(R.string.pref_default));
        strEmoncmsAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, getResources().getString(R.string.pref_default));

        if (MainActivity.DEBUG) Log.i(TAG, "URL is " + strEmoncmsURL);
        if (MainActivity.DEBUG) Log.i(TAG, "API is " + strEmoncmsAPI);



        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                // Launch settings activity
                Intent i = new Intent(this, Preferences.class);
                startActivity(i);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        return new LoadSummaryStatus(Summary.this, strEmoncmsURL, strEmoncmsAPI);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");
        summaryStatus = data;
        BuildSummaryView(summaryStatus);

    }

    private void BuildSummaryView(ArrayList summaryStatus) {
        setContentView(R.layout.summary);

        SummaryStatus status = (SummaryStatus) summaryStatus.get(0);
        String strRaspPiStatus = status.getStrRaspPiStatus();
        String strGoodFeeds = status.getStrFeedsGood();
        String strBadFeeds = status.getStrFeedsBad();

        TextView t = (TextView) findViewById(R.id.textView2);
        t.setText(strEmoncmsURL);

        TextView tv5 = (TextView) findViewById(R.id.textView5);
        tv5.setText(strRaspPiStatus);

        TextView tv8 = (TextView) findViewById(R.id.textView8);
        tv8.setText(strGoodFeeds);

        TextView tv10 = (TextView) findViewById(R.id.textView10);
        tv10.setText(strBadFeeds);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ BuildSummaryView() called! +++");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

    }
}
