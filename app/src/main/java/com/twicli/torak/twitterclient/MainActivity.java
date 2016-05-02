package com.twicli.torak.twitterclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.auth.RequestToken;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends AppCompatActivity {

    // Constants
    /**
     * Register your here app https://dev.twitter.com/apps/new and get your
     * consumer key and secret
     * */

    public static Bundle mMyAppsBundle = new Bundle();

    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";

    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = " https://api.twitter.com/oauth/authorize";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = " https://api.twitter.com/oauth/access_token";

    Button btnTweet,btnLogin;
    EditText editTextTweet;

    private static Twitter twitter;
    private  static RequestToken requestToken;

    // Shared Preferences
    private static SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if twitter keys are set
        if(getString(R.string.consumer_key).trim().length() == 0 || getString(R.string.consumer_secret).trim().length() == 0){
            // Internet Connection is not present
            Toast.makeText(getApplicationContext(),"Twitter oAuth tokens," +
                    " Please set your twitter oauth tokens first!",Toast.LENGTH_SHORT).show();
            // stop executing code by return
            return;
        }

        btnLogin = (Button) findViewById(R.id.login_btn);
        btnTweet = (Button) findViewById(R.id.tweet_button);
        editTextTweet = (EditText) findViewById(R.id.tweet_editText);

        mSharedPreferences = getApplicationContext().getSharedPreferences("TwitPref",0);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Call login twitter function
                loginAsync task = new loginAsync();
                task.execute();
            }
        });
    }

    private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(getString(R.string.consumer_key));
            builder.setOAuthConsumerSecret(getString(R.string.consumer_secret));
            Configuration configuration = builder.build();

            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
            TokenPasser.setTwitter(twitter);
            try {
                requestToken = twitter
                        .getOAuthRequestToken(TWITTER_CALLBACK_URL);
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse(requestToken.getAuthenticationURL())));

                //save the token for next intent.
                TokenPasser.setRequestToken(requestToken);

            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
            // user already logged into twitter
            Toast.makeText(getApplicationContext(),
                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
        }
    }

    private class loginAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.i("async", "doInBackground");
           loginToTwitter();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("async", "onPostExecute");

        }

        @Override
        protected void onPreExecute() {
            Log.i("async", "onPreExecute");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i("async", "onProgressUpdate");
        }

    }


        /**
         * Check user already logged in your application using twitter Login flag is
         * fetched from Shared Preferences
         * */
    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }


}

