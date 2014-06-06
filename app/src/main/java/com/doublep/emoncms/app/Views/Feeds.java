package com.doublep.emoncms.app.Views;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.Preferences;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.adapters.AdapterFeeds;
import com.doublep.emoncms.app.loaders.LoadFeeds;

import java.util.ArrayList;

/**
 * Created by Paul Patchell on 02/06/2014.
 */
public class Feeds extends ListActivity implements
        LoaderManager.LoaderCallbacks<ArrayList> {

    String strURL = "http://doublep.dnsd.me/emoncms/";
    String strAPI = "480fa3515ab1294e45a8d4854c1a0784";
    ArrayList feedDetails;
    AdapterFeeds mAdapter;
    // The Loader's id (this id is specific to the ListFragment's LoaderManager)
    private static final int LOADER_ID = 1;
    private static final String TAG = "Feeds";

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Context mContext = this;
        setContentView(R.layout.feed_list);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        return new LoadFeeds(Feeds.this, strURL, strAPI );
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader,ArrayList data) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");
        feedDetails = data;
        mAdapter = new AdapterFeeds(this, R.layout.feed_list, feedDetails);

        setListAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

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


}
