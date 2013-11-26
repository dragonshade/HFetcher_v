package com.sharanshade.hfetcher;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String VideoPageBuilder(URL url) throws IOException
    {
        String[] EmbeddedVideos = new String[5];
        String img="";

        InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
        BufferedReader br = new BufferedReader(reader);
        StringBuilder input = new StringBuilder();
        String temp;
        String videoPattern = new String("http://flashservice.xvideos.com/embedframe/[0-9a-z]*");
        String imagePattern = new String("http://blog-imgs-45-origin.fc2.com/e/r/o/ero2sokuhou/[0-9a-z]*.jpg");
        Pattern patternXvid = Pattern.compile(videoPattern);
        Pattern patternImage = Pattern.compile(imagePattern);

        while((temp =br.readLine())!=null)
            input.append(temp);

        Matcher matcherVid = patternXvid.matcher(input);
        Matcher matcherImg = patternImage.matcher(input);

        int index=0;
        while(matcherVid.find() && index<5)   //scan for multiple videos
        {
            int start = matcherVid.start();
            int end = matcherVid.end();
            EmbeddedVideos[index] = input.substring(start,end);
            index++;
        }

        if(matcherImg.find())   //only 1 image
        {
            int start = matcherVid.start();
            int end = matcherVid.end();
            img = input.substring(start,end);
        }

        //Starting to build html page
        String Head ="<html><body>";
        String Tail ="</body></html>";
        String breaks="<br>";

        StringBuilder Page = new StringBuilder();

        Page.append(Head);
        Page.append(buildImg(img));
        Page.append(breaks);
        for(String vid : EmbeddedVideos)
        {
            if(vid!=null)
            {
                Page.append(buildFrame(vid));
                Page.append(breaks);
                Page.append(breaks);
            }
        }
        Page.append(Tail);
        return Page.toString();
    }

    public static String buildImg(String img)
    {
        String Head = "<Img Src=\"";
        String Tail = "\" Border=\"0\" width=\"640\" height=\"430\">";
        StringBuilder imgBox = new StringBuilder();
        return imgBox.append(Head).append(img).append(Tail).toString();
    }

    public static String buildFrame(String embeddedVid)
    {
        String Head = "<iframe src=\"";
        String Tail = "\" frameborder=0 width=510 height=400 scrolling=no></iframe>";
        StringBuilder frame= new StringBuilder();
        return frame.append(Head).append(embeddedVid).append(Tail).toString();
    }
}
