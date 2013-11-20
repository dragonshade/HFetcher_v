package com.sharanshade.hfetcher;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Scarlet on 11/8/13.
 */
public class VideoListFragment extends ListFragment {
    private ArrayList<VideoEntry> genericVideoEntries;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Load different Videolists based on user selection (intent) in future version
        // CODE HERE//
        /////////////////////////////////////////////////////////////////////

        try {
            Ero2sokuhou.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }

         new PageFetchTask().execute();

        genericVideoEntries = Ero2sokuhou.getVideosEntry();
        VideoArrayAdapter adapter = new VideoArrayAdapter(genericVideoEntries);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Log.i("OnListItemClick","Item "+position+" is clicked");
        Log.i("OnListItemClick","URL is:"+Ero2sokuhou.getVideosEntry().get(position).getLink());
        //send url to webViewFragment
        String link = Ero2sokuhou.getVideosEntry().get(position).getLink();
        if(link!=null)  //if link is not null, spawn a webview and display it
        {
            VideoPageFragment f = (VideoPageFragment) getFragmentManager().findFragmentById(R.id.browser);
            f.setUrl(link);
            f.updateWebView();
        }

    }


    //Customize ArrayAdapter
    private class VideoArrayAdapter extends ArrayAdapter<VideoEntry>
    {
        public VideoArrayAdapter(ArrayList<VideoEntry> videoEntries)
        {
            super(getActivity(), android.R.layout.simple_list_item_1,videoEntries);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if(null==convertView)
            {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.video_array_view,null);
            }
                VideoEntry entry = (VideoEntry) getItem(position);

                TextView titleTextView = (TextView) convertView.findViewById(R.id.videotitle);
                titleTextView.setText(entry.getTitle());

                return convertView;
        }
    }


    //Background task
    private class PageFetchTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params){
            try{
                PageFetch.GrabAndParse(Ero2sokuhou.getUrl());

            }catch (IOException ioe)
            {
                Log.e("PageFetchTask", "Failed to fetch URL: ", ioe);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ListFragment lFrag = (ListFragment) getFragmentManager().findFragmentById(R.id.generic_video_list_container);
            VideoArrayAdapter adapter = (VideoArrayAdapter) lFrag.getListAdapter();
            adapter.notifyDataSetChanged();
        }

    }
}