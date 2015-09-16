package com.pdx.acm.acmpsuapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * calender_Fragment is the class managing the Calender drawer responsible of holding dates for
 * events users can look up.
 */
public class calender_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.calender_layout, container, false);
        WebView webView = (WebView) rootView.findViewById(R.id.webView2);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/calender_Fragment.html");

        return rootView;
    }
}

