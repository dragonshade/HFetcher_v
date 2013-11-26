package com.sharanshade.hfetcher;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;




public class VideoPageFragment extends Fragment{
    public String url;
    public WebView videoWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videopagefraglayout,container);
        videoWebView = (WebView) v.findViewById(R.id.webView);
       videoWebView.getSettings().setJavaScriptEnabled(true);
        return null;
    }

    public void setUrl(String link)
    {
        Log.i("setURL","url changed.");
        url=link;
    }

    public void updateWebView() throws MalformedURLException
    {
        Log.i("updateWebView","trying to reload new url");
        pageLoadTask t = new pageLoadTask();
        t.execute(new URL(url));
    }


   public void loadPage(String page)
   {
       videoWebView.loadData(page,"text/html; charset=UTF-8",null);
   }


    //Background task
    private class pageLoadTask extends AsyncTask<URL, Void, String>
    {

        @Override
        protected String doInBackground(URL... urls) {
            try{
               return  PageFetch.VideoPageBuilder(urls[0]);
            } catch(MalformedURLException mue)
            {
                Log.e("pageLoadTask","invalid url");
            } catch (IOException ioe)
            {
                Log.e("PageLoadTask", "Failed to fetch URL: ", ioe);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
           VideoPageFragment f = (VideoPageFragment) getFragmentManager().findFragmentById(R.id.browser);
            f.loadPage(result);
        }

    }


}


