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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

/**
 * settings_Fragment contains various options where a user can mange the app such as their
 * notification, updates, etc...
 */
public class settings_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View rootView;
    Switch nSwitch;
    Switch vSwitch;

    String[] strings = {"CoderzHeaven","Google",
            "Microsoft", "Apple", "Yahoo","Samsung", "Samsung","Samsung","Samsung","Samsung","Samsung"};

    String[] subs = {"Heaven of all working codes ","Google sub",
            "Microsoft sub", "Apple sub", "Yahoo sub","Samsung sub", "Samsung sub", "Samsung sub",
            "Samsung sub", "Samsung sub", "Samsung sub",};


    int arr_images[] = { R.drawable.ic_action_account_circle,
            R.drawable.ic_action_account_circle, R.drawable.ic_action_account_circle,
            R.drawable.ic_action_account_circle, R.drawable.ic_action_account_circle, R.drawable.ic_action_account_circle,
            R.drawable.ic_action_account_circle, R.drawable.ic_action_account_circle, R.drawable.ic_action_account_circle,
            R.drawable.ic_action_account_circle, R.drawable.ic_action_account_circle};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.setting_layout, container, false);
        nSwitch = (Switch) rootView.findViewById(R.id.notification);
        vSwitch = (Switch) rootView.findViewById(R.id.volume);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(getContext(), R.layout.row, strings));
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

    public class MyAdapter extends ArrayAdapter<String>{

        public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getActivity().getLayoutInflater();
            View row=inflater.inflate(R.layout.row, parent, false);
            TextView label=(TextView)row.findViewById(R.id.company);
            label.setText(strings[position]);

            TextView sub=(TextView)row.findViewById(R.id.sub);
            sub.setText(subs[position]);

            ImageView icon=(ImageView)row.findViewById(R.id.image);
            icon.setImageResource(arr_images[position]);

            return row;
        }
    }
}
