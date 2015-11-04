package com.pdx.acm.acmpsuapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.calender_layout, container, false);
        WebView webView = (WebView) rootView.findViewById(R.id.webView2);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com/calendar/embed?showPrint=0&showCalendars=0&" +
                "showTz=0&wkst=2&bgcolor=%23ffffff&src=pdx.edu_mui9uk67ft1p9chrmnctcu0eug%4" +
                "0group.calendar.google.com&ctz=America/Los_Angeles");
        return rootView;
    }
}

