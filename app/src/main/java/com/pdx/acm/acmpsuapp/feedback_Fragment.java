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
 * feedback_Fragment is a class that allows users to input questions or comments to the ACM.
 * It uses a bunch of text boxes to hold data and then send them to a google sheet when the
 * button is pressed. The text boxes are varified to see if valid data have been entered.
 */
public class feedback_Fragment extends Fragment {

    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    public static final String URL = "https://docs.google.com/forms/d/15yNjmU-NgUbmqEm--ccEerCG4p-ndsDwBYPnCXh49L4/formResponse";
    public static final String FIRST_LAST = "entry_629565629";
    public static final String EMAIL_KEY = "entry_1992702626";
    public static final String FEEDBACK_MESSAGE = "entry_1912449623";

    private Context context;
    private EditText First_last;
    private EditText Email_address;
    private EditText Feedback_message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feedback_layout, container, false);
        context = getContext();

        Button sendButton = (Button) rootView.findViewById(R.id.pushfields);
        First_last = (EditText) rootView.findViewById(R.id.namefield);
        Email_address = (EditText) rootView.findViewById(R.id.emailfield);
        Feedback_message = (EditText) rootView.findViewById(R.id.feedbackfield);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(Email_address.getText().toString()) ||
                        TextUtils.isEmpty(First_last.getText().toString()) ||
                        TextUtils.isEmpty(Feedback_message.getText().toString())) {
                    Toast.makeText(context, "All fields are mandatory.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email_address.getText().toString()).matches()) {
                    Toast.makeText(context, "Please enter a valid email.", Toast.LENGTH_LONG).show();
                    return;
                }

                PostDataTask postDataTask = new PostDataTask();

                postDataTask.execute(URL, Email_address.getText().toString(),
                        Feedback_message.getText().toString(),
                        First_last.getText().toString());
            }
        });
        return rootView;
    }

    private class PostDataTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... contactData) {
            Boolean result = true;
            String url = contactData[0];
            String email = contactData[1];
            String feedback = contactData[2];
            String name = contactData[3];
            String postBody = "";

            try {
                postBody = EMAIL_KEY + "=" + URLEncoder.encode(email, "UTF-8") +
                        "&" + FEEDBACK_MESSAGE + "=" + URLEncoder.encode(feedback, "UTF-8") +
                        "&" + FIRST_LAST + "=" + URLEncoder.encode(name, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                result = false;
            }

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

        @Override
        protected void onPostExecute(Boolean result) {
            Toast.makeText(context, result ? "Message successfully sent!" : "There was some error in sending message. Please try again after some time.", Toast.LENGTH_LONG).show();
        }

    }
}
