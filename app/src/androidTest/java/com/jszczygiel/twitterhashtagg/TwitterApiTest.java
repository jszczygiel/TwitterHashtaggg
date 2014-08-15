package com.jszczygiel.twitterhashtagg;

import android.test.InstrumentationTestCase;

import com.jszczygiel.twitterhashtagg.api.TwitterApi;


/**
 * Created by radny on 15.08.14.
 */
public class TwitterApiTest extends InstrumentationTestCase {

    public void testTwitterApiCreation()   {
        TwitterApi twitter = new TwitterApi();
        assertNotNull(twitter);
    }

}
