package com.doublep.emoncms.app.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.doublep.emoncms.app.R;

/**
 * Created by Paul Patchell on 29/07/2014.
 */
public class WebPage extends Fragment {

    private String currentURL;


    public void init(String url) {
        currentURL = url;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentURL = "http://doublep.dnsd.me/emoncms/";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("SwA", "WVF onCreateView");
        View myWebView = inflater.inflate(R.layout.web_view, container, false);
        WebView wv = (WebView) myWebView.findViewById(R.id.webview);

        if (currentURL != null) {
            Log.d("SwA", "Current URL  1[" + currentURL + "]");

            //  myWebView = (WebView) v.findViewById(R.id.webview);

            wv.loadUrl(currentURL);

        }
        return myWebView;
    }

    public void updateUrl(String url) {


    }

}