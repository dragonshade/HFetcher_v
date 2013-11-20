package com.sharanshade.hfetcher;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Scarlet on 11/8/13.
 */
public class MainMenuFragment extends Fragment {
    public final static String FRAGMENT_TAG = "com.sharanshade.hfetcher.FTAG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_menu_fragment_view, container,false);

        ImageButton ero2Button = (ImageButton) v.findViewById(R.id.ero2sokuhou);
        ero2Button.setBackgroundResource(R.drawable.ero2sokuhoulogo);
        ero2Button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), GenericVideoListActivity.class);
                        intent.putExtra(FRAGMENT_TAG,"ero2");
                        getActivity().startActivity(intent);
                    }
                }
        );

        return v;
    }
}