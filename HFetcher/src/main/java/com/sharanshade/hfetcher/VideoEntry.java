package com.sharanshade.hfetcher;

import java.util.Date;

//Single entry of video model

public class VideoEntry {

    private String link;
    private String title;
    private String description;
    private String tag;
    private String imagelink;
    private Date date;

    public VideoEntry(String link, String title, String description, String tag, String imagelink, Date date)
    {
        this.link=link;
        this.title=title;
        this.description=description;
        this.tag = tag;
        this.imagelink=imagelink;
        this.date=date;
    }

    public String getLink() { return link;}
    public String getTitle(){ return title;}
    public String getDescription(){ return description;}
    public String tag(){ return tag;}
    public String getImagelink(){return imagelink;}
    public Date getDate(){return date;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
