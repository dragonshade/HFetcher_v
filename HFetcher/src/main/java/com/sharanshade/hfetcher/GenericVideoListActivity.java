package com.sharanshade.hfetcher;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Scarlet on 11/8/13.
 */
public class GenericVideoListActivity extends FragmentActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_video_list_view);

        Intent intent = getIntent();
        String userChoice = intent.getStringExtra(MainMenuFragment.FRAGMENT_TAG);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.generic_video_list_container);

        if(fragment==null)
        {
            fragment = new VideoListFragment();
            fm.beginTransaction().add(R.id.generic_video_list_container,fragment).commit();
        }

        Fragment fragmentBrowser = fm.findFragmentById(R.id.browser);
        if(fragmentBrowser==null)
        {
            fragmentBrowser = new VideoPageFragment();
            fm.beginTransaction().add(R.id.browser,fragmentBrowser).commit();
        }


    }

}