package com.sharanshade.hfetcher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

//This is a singleton class
public class Ero2sokuhou {

    private static Ero2sokuhou _instance;
    private static ArrayList<VideoEntry> Ero2videos;
    private static URL url;

    public static String xmlForm;

    public static Ero2sokuhou getInstance() throws IOException {
        if(_instance==null)
        {
            _instance = new Ero2sokuhou();
            Ero2videos = new ArrayList<VideoEntry>(20);
            url = new URL("http://ero2sokuhou.blog.fc2.com/?xml");
        }
        return _instance;
    }

    public static ArrayList<VideoEntry> getVideosEntry()
    {
        return Ero2videos;
    }

    public static URL getUrl(){ return url;}

    private Ero2sokuhou() throws IOException {
        Ero2videos = new ArrayList<VideoEntry>();
        //dummyList();
    }

    private void dummyList() throws IOException {
        for(int i=0;i<30;i++)
        {
            Ero2videos.add(i,new VideoEntry("",Integer.toString(i),"","","",null));
        }

      //  PageFetch.GrabXMLForm(url);
        /* Test */
        Ero2videos.add(30,new VideoEntry(null,Ero2sokuhou.xmlForm,"","","",null));
    }


}
