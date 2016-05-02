package com.twicli.torak.twitterclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import twitter4j.auth.RequestToken;

/**
 * Created by torak on 5/2/16.
 */
public class SharedPreferenceEditor {

    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

    static final String USER_TOKEN = "user_token";
    static final String USER_SECRET = "user_secret";



    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setKeys(Context ctx, String token, String secret)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_KEY_OAUTH_TOKEN, token);
        editor.putString(PREF_KEY_OAUTH_SECRET,secret);
        editor.apply();
    }

    public static void setUserKeys(Context ctx, String token, String secret)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_TOKEN, token);
        editor.putString(USER_SECRET,secret);
        editor.apply();
    }

    public static void setIsLogedIn(Context ctx, boolean value)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_KEY_TWITTER_LOGIN, value);
        editor.apply();
    }

    public static String getUserToken(Context ctx)
    {
        return getSharedPreferences(ctx).getString(USER_TOKEN, "");
    }

    public static String getUserSecret(Context ctx)
    {
        return getSharedPreferences(ctx).getString(USER_SECRET, "");
    }

}
