package com.doublep.emoncms.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.doublep.emoncms.app.Views.Feeds;


public class MainActivity extends ActionBarActivity {
    public static final boolean DEBUG = true;
    public String strURL, strAPI;
    public static final String appPrefs = "AppPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (CheckPreferences()) {
            Context context = getApplicationContext();
            CharSequence text = "Shared Preferences set!!!!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {

            long unixTime = System.currentTimeMillis() / 1000L;
            Context context = getApplicationContext();
            CharSequence text = "Current Time" + unixTime;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Intent i = new Intent(this, Feeds.class);
            startActivity(i);

        }
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

    private boolean CheckPreferences() {

        SharedPreferences sharedpreferences = getSharedPreferences(appPrefs, Context.MODE_PRIVATE);

        if (sharedpreferences.contains(strURL) && sharedpreferences.contains(strAPI) )
        {
            return true;
        }
        else return false;

    }
}
