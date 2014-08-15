package com.jszczygiel.twitterhashtagg.api;

import com.jszczygiel.twitterhashtagg.model.Tweet;

/**
 * Interface that proxies Twitter Stream API calls
 *
 * Created by jszczygiel on 15.08.14.
 */
public interface ITweetListener {

    public void onNewTweet(Tweet tweet);

    public void onException(Exception e);
}
