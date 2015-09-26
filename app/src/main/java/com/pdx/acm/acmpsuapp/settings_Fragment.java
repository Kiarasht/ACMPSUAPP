package com.pdx.acm.acmpsuapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
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
 * notification, theme, etc...
 */
public class settings_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View rootView;
    Switch nSwitch;
    Switch vSwitch;

    /**
     * Creating a setting requires us to handle a lot of button, switches, and spinners.
     * We will find them by view and start playing around with them. We also need to save
     * these values otherwise everything on the page would be useless.
     *
     * @param inflater           Required since we are extending a Fragment rather than Activity
     * @param container          Required since we are extending a Fragment rather than Activity
     * @param savedInstanceState A default parameter even when extending Activity
     * @return Returns the view of the fragment
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Creating an instant of our view using rootView will then we able to access our items
         */
        rootView = inflater.inflate(R.layout.setting_layout, container, false);
        nSwitch = (Switch) rootView.findViewById(R.id.notification);
        vSwitch = (Switch) rootView.findViewById(R.id.volume);

        /**
         * Creating our spinner and calling our function to customize it to our likings
         */
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(getContext(), R.layout.row, getResources().getStringArray(R.array.array_theme)));
        spinner.setOnItemSelectedListener(this);

        /**
         * Reading in data that had possibly been previously saved. If not, the second parameter
         * represents a default value if nothing was found. For example if themedata has not been
         * previously been saved, the spinner gets a value of 0 which is a green theme. If the user
         * did save data before will read that instead.
         */
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int savednotification = sharedPref.getInt(getString(R.string.notificationdata), 1);
        int savedvolume = sharedPref.getInt(getString(R.string.volumedata), 1);
        int savedtheme = sharedPref.getInt(getString(R.string.themedata), 0);
        spinner.setSelection(savedtheme);

        /**
         * Setting the switches to the correct true or false depending on what data was saved
         * for them
         */
        if (savednotification == 1) {
            nSwitch.setChecked(true);
        } else if (savednotification == 0) {
            nSwitch.setChecked(false);
        }
        if (savedvolume == 1) {
            vSwitch.setChecked(true);
        } else if (savedvolume == 0) {
            vSwitch.setChecked(false);
        }

        /**
         * This functions get called when the notification switch's value was changed.
         */
        nSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                /**
                 * We ready up sharedpreferences to edit mode and we set in the value which is
                 * really either 0, or 1. Then we apply the edit to be saved.
                 */
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

        /**
         * This functions get called when the volume switch's value was changed.
         */
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

    /**
     * When a row in our spinner is selected, we will use sharedpreferences to save
     * our data and hold that value even when the app is closed. So the theme of the
     * application doesn't change on eat start or fragment change.
     *
     * @param parent   Parent of adapter
     * @param view     View where the spinner is located
     * @param position Position of a row that is selected
     * @param id       Not used but required for overriding
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.themedata), position);
        editor.apply();
    }

    /**
     * We don't care if nothing is selected, so we will leave the function empty. We still
     * need it since our class implements AdapterView.OnItemSelectedListener
     *
     * @param parent Parent of adapter
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * class responsible of designing our spinner. It uses an xml to create a text,
     * subtext and an image. Each of these three elements will be used for each row
     * of the spinner.
     */
    public class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        /**
         * Contains three blocks of code where the first creates the title of a row,
         * second is the subtitle of a row, and third is the icon representing that row.
         * All these arrays are stored in strings.xml
         *
         * @param position    Position of a row that is selected
         * @param convertView Not used but required for parent
         * @param parent      Parent of adapter
         * @return Updates the view of the row and returns it
         */
        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            TextView label = (TextView) row.findViewById(R.id.company);
            label.setText(getResources().getStringArray(R.array.array_theme)[position]);

            TextView sub = (TextView) row.findViewById(R.id.sub);
            sub.setText(getResources().getStringArray(R.array.array_theme_describe)[position]);

            TypedArray imgs = getResources().obtainTypedArray(R.array.array_theme_icon);
            ImageView icon = (ImageView) row.findViewById(R.id.image);
            icon.setImageResource(imgs.getResourceId(position, 0));

            return row;
        }
    }
}
