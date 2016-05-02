package com.twicli.torak.twitterclient;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class Tweet extends AppCompatActivity {

    // Shared Preferences
    private static SharedPreferences mSharedPreferences;

    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

    EditText edTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         edTweet = (EditText) findViewById(R.id.tweet_editText);
        Button btnTweet = (Button) findViewById(R.id.tweet_button);
        assert btnTweet != null;
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call update status function
                // Get the status from EditText
                String status = edTweet.getText().toString();

                // Check for blank text
                if (status.trim().length() > 0) {
                    // update status
                    new updateTwitterStatus().execute(status);
                } else {
                    // EditText is empty
                    Toast.makeText(getApplicationContext(),
                            "Please enter status message", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

    }

    /**
     * Function to update status
     * */
    class updateTwitterStatus extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           Toast.makeText(getApplicationContext(),"Updating to twitter...",Toast.LENGTH_SHORT).show();

        }

        /**
         * getting Places JSON
         * */
        protected String doInBackground(String... args) {
            Log.d("Tweet Text", "> " + args[0]);
            String status = args[0];
            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(getString(R.string.consumer_key));
                builder.setOAuthConsumerSecret(getString(R.string.consumer_secret));



                // Access Token
                String access_token = SharedPreferenceEditor.getUserToken(Tweet.this);
                // Access Token Secret
                String access_token_secret = SharedPreferenceEditor.getUserSecret(Tweet.this);

                AccessToken accessToken = new AccessToken(access_token, access_token_secret);
                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

                // Update status
                twitter4j.Status response = twitter.updateStatus("hhggdeneme");

                Log.d("Status", "> " + response.getText());
            } catch (TwitterException e) {
                // Error in updating status
                Log.d("Twitter Update Error", e.getMessage());
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog and show
         * the data in UI Always use runOnUiThread(new Runnable()) to update UI
         * from background thread, otherwise you will get error
         * **/
        protected void onPostExecute(String file_url) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Status tweeted successfully", Toast.LENGTH_SHORT)
                            .show();
                    // Clearing EditText field
                    edTweet.setText("");
                }
            });
        }

    }

}
