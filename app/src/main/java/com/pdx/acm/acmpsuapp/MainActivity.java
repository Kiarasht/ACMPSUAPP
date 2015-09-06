package com.pdx.acm.acmpsuapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    //  Fragment managing the behaviors, interactions and presentation of the navigation drawer.
    private NavigationDrawerFragment mNavigationDrawerFragment;


    //  Used to store the last screen title.
    private CharSequence mTitle;

    /**
     * onCreate simply sets up the drawer, by creating a Fragment and putting the App's
     * title on the ActionBar.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    /**
     * OnNavigationDrawerItemSelected gives an int based on which drawer was selected. We will
     * use this given parameter to call and create the correct Fragment so the correct drawer
     * view show up.
     *
     * @param position The position in its list, a drawer was pressed.
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment objFragment = null;

        switch (position) {
            case 0:
                objFragment = new home_Fragment();
                onSectionAttached(0);
                break;
            case 1:
                objFragment = new calender_Fragment();
                onSectionAttached(1);
                break;
            case 2:
                objFragment = new signup_Fragment();
                onSectionAttached(2);
                break;
            case 3:
                objFragment = new feedback_Fragment();
                onSectionAttached(3);
                break;
            case 4:
                objFragment = new settings_Fragment();
                onSectionAttached(4);
                break;
            case 5:
                objFragment = new about_Fragment();
                onSectionAttached(5);
                break;
        }

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, objFragment)
                .commit();
    }

    /**
     * Will use the String array declared in string.xml to update the app with the correct
     * title
     *
     * @param number Number will correspond to the drawer number pressed.
     */
    public void onSectionAttached(int number) {
        String[] stringArray = getResources().getStringArray(R.array.section_titles);
        mTitle = stringArray[number];
    }

    /**
     * Restores the ActionBar to what it was when the app opened up and when the drawer icon
     * (3 horizontal bars) have been clicked.
     */
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
     * Only show items in the action bar relevant to this screen
     * if the drawer is not showing. Otherwise, let the drawer
     * decide what to show in the action bar.
     *
     * @param menu Menu of the drawer
     * @return Returns a boolean on if drawer is showing or not
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {

            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     *
     * @param item Item that was selected
     * @return Call super on the item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view. Being a static class, we may call its
     * methods such as onCreateView and onAttach to manage our Fragments.
     */
    public static class PlaceholderFragment extends Fragment {
    //  The fragment argument representing the section number for this fragment.
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}