package com.sharanshade.hfetcher;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Scarlet on 11/13/13.
 */
public class PageFetch {

    public static void GrabAndParse(URL url) throws IOException, XmlPullParserException {
        InputStream is = null;
        try{
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is,"UTF-8");
            //use parser
            PageFetch.XMLParser(parser);

        } finally{
            if(is!=null)
            {
                is.close();
            }
        }
    }

    //This parses entries to the ArrayList
    public static void XMLParser(XmlPullParser parser) throws IOException, XmlPullParserException {
        int eventType = parser.next();
        int index = -1;    //start from -1 to offset for the first found entry which is the site title

        while(!( eventType==XmlPullParser.END_TAG && "channel".equals(parser.getName())))  //bypass the channel tag
        {
            eventType=parser.next();
        }

        while(eventType !=XmlPullParser.END_DOCUMENT)
        {

            eventType=parser.next();

            //parse in the link
            if(eventType==XmlPullParser.START_TAG && "link".equals(parser.getName()))
            {
                  eventType=parser.next();
                if(eventType==XmlPullParser.TEXT)
                {
                    index=index+1;
                    String link = parser.getText();
                    Ero2sokuhou.getVideosEntry().add(new VideoEntry(link,null,null,null,null,null));
                }
            }   //get the title

            if(eventType ==XmlPullParser.START_TAG  && "title".equals(parser.getName()))
            {
                eventType=parser.next();
                if(eventType==XmlPullParser.TEXT)
                {
                    String title =parser.getText();
                    Ero2sokuhou.getVideosEntry().get(index).setTitle(title);
                }
            }
        }
    }
}
