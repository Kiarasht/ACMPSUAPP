package com.pdx.acm.acmpsuapp;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * about_Fragment is the class responsible of handling the About drawer. This fragment includes
 * descriptions about the ACM organization. It uses the basic view data type to represent its
 * layout.
 */
public class about_Fragment extends Fragment {
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.about_layout, container, false);
        WebView view = (WebView) rootView.findViewById(R.id.textView2);
        view.loadUrl("file:///android_asset/about.html");
        view.setBackgroundColor(Color.TRANSPARENT);
        return rootView;
    }
}
