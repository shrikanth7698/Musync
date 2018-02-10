package com.shrikanthravi.slidinguppanel;

/**
 * Created by shrikanthravi on 17/01/18.
 */

public class ImageText {
    String url;
    String text;
    String id;

    public ImageText(String url, String text, String id) {
        this.url = url;
        this.text = text;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
