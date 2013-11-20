package com.sharanshade.hfetcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class VideoPageFragment extends Fragment {
    public String url;
    private WebView videoWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videopagefraglayout,container);
        videoWebView = (WebView) v.findViewById(R.id.webView);
        return null;
    }

    public void setUrl(String link)
    {
        Log.i("setURL","url changed.");
        url=link;
    }

    public void updateWebView()
    {
        Log.i("updateWebView","trying to reload new url");
        videoWebView.loadUrl(url);
    }
}