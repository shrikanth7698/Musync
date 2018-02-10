package com.shrikanthravi.slidinguppanel;


import java.util.List;

/**
 * Created by shrikanthravi on 28/09/17.
 */

public class Message {
    String type;
    String time;
    String body;
    List<QuickContent> quick;
    List<ImageContent> image;
    List<ImageText> imageT;

    public Message(String type, String time, String body, List<QuickContent> quick, List<ImageContent> image, List<ImageText> imageT) {
        this.type = type;
        this.time = time;
        this.body = body;
        this.quick = quick;
        this.image = image;
        this.imageT = imageT;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<QuickContent> getQuick() {
        return quick;
    }

    public void setQuick(List<QuickContent> quick) {
        this.quick = quick;
    }

    public List<ImageContent> getImage() {
        return image;
    }

    public void setImage(List<ImageContent> image) {
        this.image = image;
    }

    public List<ImageText> getImageT() {
        return imageT;
    }

    public void setImageT(List<ImageText> imageT) {
        this.imageT = imageT;
    }
}
