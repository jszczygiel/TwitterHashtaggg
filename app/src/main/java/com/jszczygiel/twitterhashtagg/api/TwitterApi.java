package com.jszczygiel.twitterhashtagg.api;


import com.jszczygiel.twitterhashtagg.model.Tweet;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Proxy implementation of twitter4j library
 *
 * Created by jszczygiel on 15.08.14.
 */
public class TwitterApi extends ProxyApi {

    public static final String CONSUMER_KEY = "2kGHtIPMgDjYngLoUGIPCH8VA";
    public static final String CONSUMER_SECRET = "a0NwvVr9YYAOWqHyDBkXAYqU3wM5zOjMQbUlFbSs9lUMdPkETX";

    public static final String ACCESS_TOKEN = "2515426799-WRq70Kzcmdmhaesny3iZqdANqI5E0pmUVKufIVd";
    public static final String ACCESS_TOKEN_SECRET = "YcQQUl8wEfo1F8mZtRKRoLxQix0AVMmGnf5c5w4retNDA";

    private TwitterStream twitterStream;

    /**
     * Proxy constructor for Twitter4j library.
     */
        public TwitterApi() {
        super();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(CONSUMER_KEY);
        cb.setOAuthConsumerSecret(CONSUMER_SECRET);
        cb.setOAuthAccessToken(ACCESS_TOKEN);
        cb.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    }

    /**
     * Connects to Twitter stream API and starts queering for tweets with provided hashtags array
     *
     * @param hashTag  arrays of hashtags for searching
     * @param listener listener that returns new found tweets
     */
    public void searchTweets(String[] hashTag, final ITweetListener listener) {
        StatusListener statusListener = new StatusListener() {

            @Override
            public void onStatus(twitter4j.Status status) {
                //mapping twitter4j tweet to application tweet object
                Tweet tweet = new Tweet();
                tweet.setContent(status.getText());
                tweet.setUserName(status.getUser().getName());
                if (listener != null) {
                    listener.onNewTweet(tweet);
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                //ignored
            }

            @Override
            public void onTrackLimitationNotice(int i) {
                //ignored
            }

            @Override
            public void onScrubGeo(long l, long l2) {
                //ignored
            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {
                //ignored
            }

            @Override
            public void onException(Exception e) {
                // forwarding exception
                if (listener != null) {
                    listener.onException(e);
                }
            }
        };
        twitterStream.addListener(statusListener);

        FilterQuery fq = new FilterQuery();
        fq.track(hashTag);

        twitterStream.filter(fq);
    }


}
