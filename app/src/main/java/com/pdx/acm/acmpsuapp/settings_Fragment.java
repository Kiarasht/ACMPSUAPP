package com.pdx.acm.acmpsuapp;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * settings_Fragment contains various options where a user can mange the app such as their
 * notification, updates, etc...
 */
public class settings_Fragment extends Fragment {
    View rootView;
    Switch aSwitch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.setting_layout, container, false);

        aSwitch = (Switch) rootView.findViewById(R.id.notification);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity().getApplicationContext(), "You will now recieve notifications.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "You will no longer recieve notifications.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }

    /**
     * Get functions that passes back the switch of the fragment
     * @return Returns the switch at the current view
     */
    public Switch getswitch() {
        return aSwitch;
    }

}
