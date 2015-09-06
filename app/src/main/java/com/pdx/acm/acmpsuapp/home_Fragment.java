package com.pdx.acm.acmpsuapp;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * home_Fragment is the main page of the applications and it holds the logo of the organization at
 * the top while a small description at the bottom of it. It mainly uses the home_layout previously
 * coded in the .xml and simply updates the view before returning it.
 */
public class home_Fragment extends Fragment {
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_layout, container, false);
        return rootView;
    }
}
