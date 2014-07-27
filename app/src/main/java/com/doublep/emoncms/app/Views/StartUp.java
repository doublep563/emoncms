package com.doublep.emoncms.app.Views;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.doublep.emoncms.app.MainActivity;
import com.doublep.emoncms.app.R;
import com.doublep.emoncms.app.common;
import com.doublep.emoncms.app.loaders.LoadValidate;

/**
 * Fragment to get URL and API info from user.
 */
public class StartUp extends Fragment implements
        LoaderManager.LoaderCallbacks<Bundle> {

    private static final String TAG = "StartUp";
    private static final int LOADER_ID = 1;
    private String strURL;
    private String strAPI;
    private EditText mURL;
    private EditText mAPI;
    private TextView mErrorDescription;
    private TextView mErrorText;
    private OnStartupListener mListener;
    private final Handler handler = new Handler()  // handler for commiting fragment after data is loaded
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                if (mListener != null) {
                    mListener.onStartUpCompleted();
                }
                if (MainActivity.DEBUG) Log.i(TAG, "+++ Handler() called! +++");
                // commit the fragment
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateView() called! +++");
        View rootView = inflater.inflate(R.layout.setup, container, false);

        assert rootView != null;
        mURL = (EditText) rootView.findViewById(R.id.url);
        mAPI = (EditText) rootView.findViewById(R.id.api);
        mErrorDescription = (TextView) rootView.findViewById(R.id.txtErrorDescription);
        mErrorText = (TextView) rootView.findViewById(R.id.txtErrorText);

        Button btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.DEBUG) Log.i(TAG, "+++ btnCancel() onClick called! +++");

            }
        }));
        Button btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mErrorDescription.setVisibility(View.INVISIBLE);
                mErrorText.setVisibility(View.INVISIBLE);
                strURL = mURL.getText().toString();

                //Remove new line if copied into text field.
                strURL = strURL.replace("\n", "");

                strAPI = mAPI.getText().toString();

                if (MainActivity.DEBUG) Log.i(TAG, "URL is " + strURL);
                if (MainActivity.DEBUG) Log.i(TAG, "API is " + strAPI);
                if (MainActivity.DEBUG)
                    Log.i(TAG, "+++ btnSave() onClick called! +++" + strURL + strAPI);

                Loader<Object> mLoader = getLoaderManager().getLoader(LOADER_ID);
                if (mLoader == null) {

                    if (MainActivity.DEBUG) Log.i(TAG, "+++ btnSave() initLoader called! +++");
                    getLoaderManager().initLoader(LOADER_ID, null, StartUp.this);
                } else {
                    if (MainActivity.DEBUG) Log.i(TAG, "+++ btnSave() restartLoader called! +++");
                    getLoaderManager().restartLoader(LOADER_ID, null, StartUp.this);
                }


            }
        });


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreate() called! +++");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onActivityCreated() called! +++");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onStart() called! +++");
    }

    @Override
    public void onResume() {
        super.onResume();
        setRetainInstance(true);

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onResume() called! +++");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onPause() called! +++");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onStop() called! +++");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onDestroy() called! +++");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onDetach() called! +++");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnStartupListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public Loader<Bundle> onCreateLoader(int id, Bundle args) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        //return new LoadFeeds(getActivity(), strEmoncmsURL, strEmoncmsAPI);
        return new LoadValidate(getActivity(), strURL, strAPI);
    }

    @Override
    public void onLoadFinished(Loader<Bundle> loader, Bundle data) {
        Bundle mBundle;
        mBundle = data;
        if (mBundle.containsKey("Exception")) {
            mErrorDescription.setVisibility(View.VISIBLE);
            mErrorText.setText(mBundle.getString("Exception"));
            mErrorText.setVisibility(View.VISIBLE);
            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ onLoadFinished() mBundle Exception called! +++");
        } else {
            //TODO This needs to be moved to Preferences class or use method from class to standardise
            //TODO updating of preferences e.g. removing /n from text
            //TODO Validate input and expandable_sign. What happens with no "http://"? Invalid URL or API Key?
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(common.PREF_KEY_EMONCMS_URL, strURL);
            editor.putString(common.PREF_KEY_EMONCMS_API, strAPI);
            editor.commit();
            handler.sendEmptyMessage(2);

            if (MainActivity.DEBUG)
                Log.i(TAG, "+++ onLoadFinished() mBundle No Exception called! +++");
        }

        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");
    }

    @Override
    public void onLoaderReset(Loader<Bundle> loader) {
        if (MainActivity.DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

    }

    public interface OnStartupListener {

        public void onStartUpCompleted();

    }
}
