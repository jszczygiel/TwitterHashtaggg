package com.jszczygiel.twitterhashtagg;

import android.test.InstrumentationTestCase;

import com.jszczygiel.twitterhashtagg.api.TwitterApi;


/**
 * Test for TwitterApi
 *
 * Created by jszczygiel on 15.08.14.
 */
public class TwitterApiTest extends InstrumentationTestCase {

    public void testTwitterApiCreation()   {
        TwitterApi twitter = new TwitterApi();
        assertNotNull(twitter);
    }

}
