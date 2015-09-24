package com.pdx.acm.acmpsuapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

/**
 * settings_Fragment contains various options where a user can mange the app such as their
 * notification, updates, etc...
 */
public class settings_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View rootView;
    Switch nSwitch;
    Switch vSwitch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.setting_layout, container, false);

        nSwitch = (Switch) rootView.findViewById(R.id.notification);
        vSwitch = (Switch) rootView.findViewById(R.id.volume);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.array_theme, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int savednotification = sharedPref.getInt(getString(R.string.notificationdata), 1);
        int savedvolume = sharedPref.getInt(getString(R.string.volumedata), 1);
        int savedtheme = sharedPref.getInt(getString(R.string.themedata), 0);
        spinner.setSelection(savedtheme);

        if (savednotification == 1) {
            nSwitch.setChecked(true);
        } else if (savednotification == 0) {
            nSwitch.setChecked(false);
        } if (savedvolume == 1) {
            vSwitch.setChecked(true);
        } else if (savedvolume == 0) {
            vSwitch.setChecked(false);
        }

        nSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                if (isChecked) {
                    editor.putInt(getString(R.string.notificationdata), 1);
                } else {
                    editor.putInt(getString(R.string.notificationdata), 0);
                }
                editor.apply();
            }
        });

        vSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                if (isChecked) {
                    editor.putInt(getString(R.string.volumedata), 1);
                } else {
                    editor.putInt(getString(R.string.volumedata), 0);
                }
                editor.apply();
            }
        });
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.themedata), position);
        editor.apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
