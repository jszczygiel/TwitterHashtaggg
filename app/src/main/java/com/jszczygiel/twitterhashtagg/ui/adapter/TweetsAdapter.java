package com.jszczygiel.twitterhashtagg.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jszczygiel.twitterhashtagg.R;
import com.jszczygiel.twitterhashtagg.model.Tweet;

/**
 * Adapter that displays Tweet object
 *
 * Created by jszczygiel on 15.08.14.
 */
public class TweetsAdapter extends ArrayAdapter<Tweet> {

    private final LayoutInflater layoutInflater;

    /**
     * Constructor
     */
    public TweetsAdapter(Context context, int resource) {
        super(context, resource);
        layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Populates row of list with data based on known position.
     * For optimisation of loading view resources, viewHolder has been added.
     *
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FeedViewHolder feedViewHolder;

        Tweet item = getItem(position);
        if (convertView == null) {
            feedViewHolder = new FeedViewHolder();
            convertView = layoutInflater.inflate(R.layout.row_tweet, parent, false);
            feedViewHolder.userName = (TextView) convertView.findViewById(R.id.row_tweet_user_name);
            feedViewHolder.content = (TextView) convertView.findViewById(R.id.row_tweet_content);
        } else {
            feedViewHolder = (FeedViewHolder) convertView.getTag();
        }
        if (item != null) {
            feedViewHolder.userName.setText(item.getUserName());
            feedViewHolder.content.setText(item.getContent());
        }
        convertView.setTag(feedViewHolder);

        return convertView;
    }

    /**
     * Inner class holding view references.
     *
     */
    class FeedViewHolder {
        TextView userName;
        TextView content;
    }
}
