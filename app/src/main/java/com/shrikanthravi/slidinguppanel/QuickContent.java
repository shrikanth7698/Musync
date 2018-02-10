package com.shrikanthravi.slidinguppanel;

/**
 * Created by shrikanthravi on 17/01/18.
 */

public class QuickContent {
    String body;
    String id;

    public QuickContent(String body, String id) {
        this.body = body;
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
