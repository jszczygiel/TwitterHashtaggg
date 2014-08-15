package com.jszczygiel.twitterhashtagg.ui.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.jszczygiel.twitterhashtagg.R;
import com.jszczygiel.twitterhashtagg.api.ITweetListener;
import com.jszczygiel.twitterhashtagg.api.ProxyApi;
import com.jszczygiel.twitterhashtagg.api.TwitterApi;
import com.jszczygiel.twitterhashtagg.model.Tweet;
import com.jszczygiel.twitterhashtagg.ui.adapter.TweetsAdapter;

import java.util.ArrayList;

/**
 * Main Fragment of application. Displays list of tweets with hashtag input by user.
 *
 * Created by jszczygiel on 15.08.14.
 */
public class MainFragment extends Fragment implements SearchView.OnQueryTextListener {

    private ProxyApi twitterApi;
    private TweetsAdapter adapter;
    private SearchTask searchTask;
    private View bar;
    private View info;
    private Menu menu;

    /**
     * Constructor
     */
    public MainFragment() {

    }

    /**
     * Static function that creates instance of Fragment
     *
     * @return instance of MainFragment class
     */
    public static MainFragment getInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    /**
     * creates twitter proxy object and adapter for displaying tweets
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twitterApi = new TwitterApi();
        adapter = new TweetsAdapter(getActivity(), 0);
        setHasOptionsMenu(true);
    }

    /**
     * stops async task queering Twitter Stream API
     */
    @Override
    public void onDestroy() {
        if (searchTask != null) {
            searchTask.cancel(true);
            searchTask = null;
        }
        super.onDestroy();
    }

    /**
     * Creates search action view for user to input hashtags
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        if (getActivity().getActionBar() != null) {
            SearchView searchView = new SearchView(getActivity().getActionBar().getThemedContext());
            searchView.setQueryHint(getString(R.string.main_fragment_hint));
            searchView.setOnQueryTextListener(this);
            menu.add(0, R.id.main_fragment_search, 1, "search").setActionView(searchView).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }
    }

    /**
     * Inflates view from xml and creates references to widgets. Sets adapter class for listView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = View.inflate(getActivity(), R.layout.fragment_main, null);
        bar = root.findViewById(R.id.fragment_main_bar);
        info = root.findViewById(R.id.fragment_main_empty);
        bar.setVisibility(View.GONE);
        ListView list = (ListView) root.findViewById(R.id.fragment_main_list);
        list.setAdapter(adapter);
        return root;
    }

    /**
     * Task that handles Twitter Stream API queering, publishes new
     * tweets to list and handles visibility of following widgets:
     * bar - is displayed till first tweet appears in list,
     * info - is displayed only before user performs first search.
     * Every start of task clears listView from Tweet objects.
     */
    private class SearchTask extends AsyncTask<String, Tweet, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clear();
            bar.setVisibility(View.VISIBLE);
            info.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... params) {
            twitterApi.searchTweets(params, new ITweetListener() {
                @Override
                public void onNewTweet(Tweet tweet) {
                    publishProgress(tweet);
                }

                @Override
                public void onException(Exception e) {
                    cancel(true);
                }
            });
            while (!isCancelled()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onProgressUpdate(Tweet... values) {
            bar.setVisibility(View.GONE);
            for (Tweet item : values) {
                adapter.add(item);
            }
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Callback called after user finishes input to search text field.
     * Query string is split and each is checked against being hashtag.
     * If no valid hashtag was input then user is presented with toast,
     * otherwise search is being started and current hashtags are displayed
     * in action bar.
     *
     * @param query raw input from search text field
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        ArrayList<String> hashTags = new ArrayList<String>();
        for (String item : query.split(" ")) {
            if (item.startsWith("#")) {
                hashTags.add(item);
            }
        }
        if (hashTags.size() == 0) {
            Toast.makeText(getActivity(), R.string.no_valid_hashtag, Toast.LENGTH_SHORT).show();
        } else {
            if (menu != null) {
                (menu.findItem(R.id.main_fragment_search)).collapseActionView();
            }
            if (searchTask != null) {
                searchTask.cancel(true);
                searchTask = null;
            }
            if (getActivity().getActionBar() != null) {
                StringBuilder result = new StringBuilder();
                for (String item : hashTags) {
                    result.append(item);
                    result.append(" ");
                }
                getActivity().getActionBar().setTitle(result.toString());
            }
            searchTask = new SearchTask();

            String[] hashTagsArray = new String[hashTags.size()];
            hashTagsArray = hashTags.toArray(hashTagsArray);
            searchTask.execute(hashTagsArray);
        }
        return true;
    }

    /**
     * Not used.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }
}
