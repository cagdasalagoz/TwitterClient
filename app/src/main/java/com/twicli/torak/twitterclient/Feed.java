package com.twicli.torak.twitterclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;


public class Feed extends AppCompatActivity {

    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";

    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "https://api.twitter.com/oauth/authorize";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "https://api.twitter.com/oauth/access_token";

    ListView listView;
    List<FeedListClass> posts =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.feed_listView);

        savePrefsAsync save = new savePrefsAsync();
        save.execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), Tweet.class);
                startActivity(intent);
            }
        });
    }

    private class savePrefsAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.i("async", "doInBackground");
            savePrefs();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("async", "onPostExecute");
            new Handler().post(new Runnable() {
                public void run() {
                    getFeedAsync task = new getFeedAsync();
                    task.execute();
                }
            });

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

    public void savePrefs(){
        RequestToken requestToken = TokenPasser.getRequestToken();
        Twitter twitter = TokenPasser.getTwitter();

        Log.i("savetoken", "on new intent started. ");

        //region Save to the sharedpref
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
            // oAuth verifier
            String verifier = uri
                    .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);

            Log.i("savetoken", "verifier : "+ verifier);

            try {
                Log.e("savetoken",TWITTER_CALLBACK_URL);
                Log.e("savetoken","token :" + requestToken.getToken());

                //requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);

                Log.e("savetoken","saving token to the sharedprefs. 1");

                // Get the access token
                AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                Log.e("savetoken","saving token to the sharedprefs. 2");


                // After getting access token, access token secret
                // store them in application preferences
                Log.e("feed", "saving user keys");
                SharedPreferenceEditor.setUserKeys(Feed.this,accessToken.getToken(),accessToken.getTokenSecret());
                SharedPreferenceEditor.setIsLogedIn(Feed.this,true);

                Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
            }catch (TwitterException te) {
                te.printStackTrace();

                Log.e("feed", "Failed to get timeline: " + te.getMessage());

            }

        }
        //endregion

    }

    private class getFeedAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.i("async", "doInBackground");
            getFeed();
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

    public void getFeed(){
        //region get the feed


        try {

            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(getString(R.string.consumer_key));
            builder.setOAuthConsumerSecret(getString(R.string.consumer_secret));

            Log.i("feed", "consumer key: "+ getString(R.string.consumer_key));
            Log.i("feed", "consumer secret: "+ getString(R.string.consumer_secret));
            // Access Token
            String access_token = SharedPreferenceEditor.getUserToken(Feed.this);
            Log.i("feed", "acc token: "+ SharedPreferenceEditor.getUserToken(Feed.this));
            // Access Token Secret
            String access_token_secret = SharedPreferenceEditor.getUserSecret(Feed.this);
            Log.i("feed", "acc secret: "+ SharedPreferenceEditor.getUserSecret(Feed.this));

            AccessToken accessToken = new AccessToken(access_token, access_token_secret);
            Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

            // gets Twitter instance with default credentials
            User user = twitter.verifyCredentials();
            List<Status> statuses = twitter.getHomeTimeline();

            Log.i("feed", "get feed for user: "+ user.getScreenName());
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());

                posts.add(new FeedListClass(status.getUser().getName(),status.getText()));

            }

            final FeedListAdapter adaptorumuz = new FeedListAdapter(Feed.this, posts);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(adaptorumuz);
                }
            });


        } catch (TwitterException te) {
            te.printStackTrace();
            // System.out.println("Failed to get timeline: " + te.getMessage());
            Log.e("feed", "Failed to get timeline: " + te.getMessage());
            //  System.exit(-1);
        }
        //endregion

    }

}
