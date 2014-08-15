package com.jszczygiel.twitterhashtagg.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.jszczygiel.twitterhashtagg.R;
import com.jszczygiel.twitterhashtagg.ui.fragment.MainFragment;

/**
 * Activity that holds main fragment.
 *
 * Created by jszczygiel on 15.08.14.
 */
public class MainActivity extends Activity {

    /**
     * Creation of activity and fragment, with fragment placement
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment= MainFragment.getInstance();

        getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }
}
