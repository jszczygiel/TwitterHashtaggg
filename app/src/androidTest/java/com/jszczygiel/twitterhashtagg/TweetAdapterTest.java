package com.jszczygiel.twitterhashtagg;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jszczygiel.twitterhashtagg.model.Tweet;
import com.jszczygiel.twitterhashtagg.ui.activity.MainActivity;
import com.jszczygiel.twitterhashtagg.ui.adapter.TweetsAdapter;

/**
 * Test for TweetAdapter
 *
 * Created by jszczygiel on 15.08.14.
 */
public class TweetAdapterTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private TweetsAdapter adapter;

    public TweetAdapterTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        adapter = new TweetsAdapter(getActivity(), 0);
    }

    public void testAddNewElementToAdapter() {
        Tweet tweet = new Tweet();
        String expectedName = "username";
        String expectedContent = "some content";
        tweet.setUserName(expectedName);
        tweet.setContent(expectedContent);

        adapter.add(tweet);

        RelativeLayout view = (RelativeLayout) adapter.getView(0, null, null);
        String actualUserName = ((TextView) view.findViewById(R.id.row_tweet_user_name)).getText().toString();
        String actualContent = ((TextView) view.findViewById(R.id.row_tweet_content)).getText().toString();

        assertSame(expectedName, actualUserName);
        assertSame(expectedContent, actualContent);
    }
}
