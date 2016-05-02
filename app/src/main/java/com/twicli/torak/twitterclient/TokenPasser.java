package com.twicli.torak.twitterclient;

import twitter4j.Twitter;
import twitter4j.auth.RequestToken;

/**
 * Created by cagdasalagoz on 5/2/16.
 */
class TokenPasser {

    private static RequestToken requestToken;

    public static Twitter getTwitter() {
        return twitter;
    }

    public static void setTwitter(Twitter twitter) {
        TokenPasser.twitter = twitter;
    }

    private static Twitter twitter;

    public static void setRequestToken(RequestToken reqTok){
        requestToken = reqTok;
    }

    public static RequestToken getRequestToken(){ return requestToken; }


}
