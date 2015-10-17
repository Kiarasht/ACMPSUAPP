package com.pdx.acm.acmpsuapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * signup_Fragment is a class that allows users to input questions or comments to the ACM.
 * It uses a bunch of text boxes to hold data and then send them to a google sheet when the
 * button is pressed. The text boxes are varified to see if valid data have been entered.
 */
public class signup_Fragment extends Fragment {

    /**
     * These values were taken from the google sheet. The Url represents where the form is being
     * held while the entry represents each text box on that form. So in this case our form has
     * total of 3 text boxes.
     */
    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    public static final String URL = "https://docs.google.com/forms/d/17-5nt3oKS4ZOc1ERlBnOs8UbPkupwfUpVLBWvIsqt58/formResponse";
    public static final String FIRST_LAST = "entry_1926831283";
    public static final String EMAIL_KEY = "entry_1699419107";

    /**
     * Getting a variable from our fragment's context and one for each textbox on
     * the it.
     */
    private Context context;
    private EditText First_last;
    private EditText Email_address;

    /**
     * Our onCreatView on signup includes textbox fields to have the user enter their name,
     * email. The textboxes are then varified to not be empty while also using
     * a java parsing tool to check the validity of the email.
     *
     * @param inflater           Required since we are extending a Fragment rather than Activity
     * @param container          Required since we are extending a Fragment rather than Activity
     * @param savedInstanceState A default parameter even when extending Activity
     * @return Returns the view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.signup_layout, container, false);
        context = getContext();

        /**
         * Set up a variable for each of the elements on the view.
         */
        Button sendButton = (Button) rootView.findViewById(R.id.pushfields2);
        First_last = (EditText) rootView.findViewById(R.id.namefield2);
        Email_address = (EditText) rootView.findViewById(R.id.emailfield2);

        /**
         * This function is only called when the sendButton is clicked.
         */
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(Email_address.getText().toString()) ||
                        TextUtils.isEmpty(First_last.getText().toString())) {
                    Toast.makeText(context, "All fields are mandatory.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email_address.getText().toString()).matches()) {
                    Toast.makeText(context, "Please enter a valid email.", Toast.LENGTH_LONG).show();
                    return;
                }

                PostDataTask postDataTask = new PostDataTask();

                postDataTask.execute(URL, Email_address.getText().toString(),
                        First_last.getText().toString());
            }
        });
        return rootView;
    }

    /**
     * Class responsible of sending the data in the background. In only contains two functions.
     * One doing most of the work while other doing the checking. This class could have been
     * moved into a file of its own but since its only used by settings it has been moved
     * in this file instead.
     */
    private class PostDataTask extends AsyncTask<String, Void, Boolean> {

        /**
         * Our post function that takes the url, name, email, gets ready to post them
         *
         * @param contactData A list of strings where in this case we should only have 4
         * @return Returns a boolean to see if the data was posted successfully or not
         */
        @Override
        protected Boolean doInBackground(String... contactData) {
            Boolean result = true;
            String url = contactData[0];
            String email = contactData[1];
            String name = contactData[2];
            String postBody = "";

            /**
             * First we try to encode the entire strings combined. This is to get rid of
             * any illegal characters or returning if something goes really wrong in the
             * process of parsing.
             */
            try {
                postBody = EMAIL_KEY + "=" + URLEncoder.encode(email, "UTF-8") +
                        "&" + FIRST_LAST + "=" + URLEncoder.encode(name, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                result = false;
            }

            /**
             * After the entire strings were validated we start to use OkHttp, an open source
             * library to send these data to google sheet.
             */
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                client.newCall(request).execute();
            } catch (IOException exception) {
                result = false;
            }
            return result;
        }

        /**
         * An end function that gets called to display a toast message depending on how the
         * post request to the google sheet went.
         *
         * @param result A boolean result that determines that result
         */
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(context, "Message successfully sent!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "There was some error in sending the request. Please try again after some time.", Toast.LENGTH_LONG).show();
            }
        }

    }
}
