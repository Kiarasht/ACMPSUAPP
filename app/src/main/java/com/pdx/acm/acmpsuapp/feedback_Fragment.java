package com.pdx.acm.acmpsuapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * feedback_Fragment is a class that allows users to input questions or comments to the ACM.
 * It uses a webView tool to load and represent an already created Google sheet. It uses a
 * nested class of MyBrowser so the app does not exit but rather load inside the app itself.
 */
public class feedback_Fragment extends Fragment {

    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    public static final String URL = "https://docs.google.com/forms/d/15yNjmU-NgUbmqEm--ccEerCG4p-ndsDwBYPnCXh49L4/formResponse";
    public static final String FIRST_LAST = "entry_629565629";
    public static final String EMAIL_KEY = "entry_1992702626";
    public static final String FEEDBACK_MESSAGE = "entry_1912449623";

    private Context context;
    private EditText subjectEditText;
    private EditText emailEditText;
    private EditText messageEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feedback_layout, container, false);
        context = getContext();

        Button sendButton = (Button) rootView.findViewById(R.id.pushfields);
        subjectEditText = (EditText) rootView.findViewById(R.id.namefield);
        emailEditText = (EditText) rootView.findViewById(R.id.emailfield);
        messageEditText = (EditText) rootView.findViewById(R.id.feedbackfield);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(emailEditText.getText().toString()) ||
                        TextUtils.isEmpty(subjectEditText.getText().toString()) ||
                        TextUtils.isEmpty(messageEditText.getText().toString())) {
                    Toast.makeText(context, "All fields are mandatory.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
                    Toast.makeText(context, "Please enter a valid email.", Toast.LENGTH_LONG).show();
                    return;
                }

                PostDataTask postDataTask = new PostDataTask();

                postDataTask.execute(URL, emailEditText.getText().toString(),
                        subjectEditText.getText().toString(),
                        messageEditText.getText().toString());
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
            String subject = contactData[2];
            String message = contactData[3];
            String postBody = "";

            try {
                postBody = EMAIL_KEY + "=" + URLEncoder.encode(email, "UTF-8") +
                        "&" + FEEDBACK_MESSAGE + "=" + URLEncoder.encode(subject, "UTF-8") +
                        "&" + FIRST_LAST + "=" + URLEncoder.encode(message, "UTF-8");
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
                Response response = client.newCall(request).execute();
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
