package com.shrikanthravi.slidinguppanel;

/**
 * Created by shrikanthravi on 17/01/18.
 */


public class ImageContent {
    String url;
    String id;
    String caption;

    public ImageContent(String url, String id, String caption) {
        this.url = url;
        this.id = id;
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}