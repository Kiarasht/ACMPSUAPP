package com.pdx.acm.acmpsuapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * calender_Fragment is the class managing the Calender drawer responsible of holding dates for
 * events users can look up.
 */
public class calender_Fragment extends Fragment {
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.calender_layout, container, false);
        String url = "https://www.google.com/calendar/embed?src=pdx.edu_mui9uk67ft1p9chrmnctcu0eug%40group.calendar.google.com&ctz=America/Los_Angeles";
        WebView view = (WebView) rootView.findViewById(R.id.webView2);
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new MyBrowser());
        view.loadUrl(url);
        return rootView;
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
