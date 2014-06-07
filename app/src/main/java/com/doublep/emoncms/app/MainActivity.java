package com.doublep.emoncms.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.doublep.emoncms.app.Views.Summary;


public class MainActivity extends ActionBarActivity {


    private static final String TAG = "MainActivity";
    public static boolean DEBUG = true;
    public static String strEmoncmsURL;
    public static String strEmoncmsAPI;
    static String strURL;
    static String strAPI;
    public SharedPreferences prefs;
    int mStackLevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.summary);
        //TODO Getting Started Screen. Setup the preferences
        if (CheckPreferences()) {
            if (MainActivity.DEBUG) Log.i(TAG, "CheckPreferences is True ");
            Intent i = new Intent(this, Summary.class);
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
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //Are the URL and API set?
        strEmoncmsURL = sharedPref.getString(common.PREF_KEY_EMONCMS_URL, getResources().getString(R.string.pref_default));
        strEmoncmsAPI = sharedPref.getString(common.PREF_KEY_EMONCMS_API, getResources().getString(R.string.pref_default));

        if (MainActivity.DEBUG) Log.i(TAG, "URL is " + strEmoncmsURL);
        if (MainActivity.DEBUG) Log.i(TAG, "API is " + strEmoncmsAPI);
        //TODO Protect against empty string in these fields
        if (strEmoncmsURL.equalsIgnoreCase(getResources().getString(R.string.pref_default))) {
            showDialog();
        } else if (strEmoncmsAPI.equalsIgnoreCase(getResources().getString(R.string.pref_default))) {
            showDialog();
        }
        return true;
    }

    void showDialog() {
        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SettingsDialogFragment.newInstance(mStackLevel);
        newFragment.show(ft, "dialog");
    }

    public void doSaveSettings(String strURL, String strAPI) {
        //TODO This needs to be moved to Preferences class or use method from class to standardise
        //TODO updating of preferences e.g. removing /n from text views
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(common.PREF_KEY_EMONCMS_URL, strURL);
        editor.putString(common.PREF_KEY_EMONCMS_API, strAPI);
        editor.commit();
        //TODO Test the URL and API provided
        //TODO Create StartActivityforResult
        Intent i = new Intent(this, Preferences.class);
        startActivity(i);
    }

    public static class SettingsDialogFragment extends DialogFragment {


        static SettingsDialogFragment newInstance(int num) {
            SettingsDialogFragment f = new SettingsDialogFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.setup, null);
            builder.setView(view);

            final EditText mURL = (EditText) view.findViewById(R.id.url);
            final EditText mAPI = (EditText) view.findViewById(R.id.api);

            Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SettingsDialogFragment.this.dismiss();
                }
            });

            Button btnSave = (Button) view.findViewById(R.id.btnSave);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    strURL = mURL.getText().toString();
                    strAPI = mAPI.getText().toString();
                    if (MainActivity.DEBUG) {
                        Log.i("MainActivity", "btnSave.setOnClickListener" + strURL + " " + strAPI);
                    }
                    ((MainActivity) getActivity()).doSaveSettings(strURL, strAPI);
                }
            });
            // Create the AlertDialog object and return it
            return builder.create();
        }


    }


}
