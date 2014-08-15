package com.jszczygiel.twitterhashtagg.api;

import java.io.IOException;

import twitter4j.TwitterException;

/**
 * Abstract class for proxy connection to external twitter libraries
 *
 * Created by jszczygiel on 15.08.14.
 */
public abstract class ProxyApi {
    public abstract void searchTweets(String[] hashTag, final ITweetListener listener);
}
