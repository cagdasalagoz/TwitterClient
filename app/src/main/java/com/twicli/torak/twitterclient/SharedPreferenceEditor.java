package com.twicli.torak.twitterclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by cagdasalagoz on 5/2/16.
 */
class SharedPreferenceEditor {

    private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    private static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

    private static final String USER_TOKEN = "user_token";
    private static final String USER_SECRET = "user_secret";



    private static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserKeys(Context ctx, String token, String secret)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_TOKEN, token);
        editor.putString(USER_SECRET,secret);
        editor.apply();
    }

    public static void setIsLogedIn(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
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
