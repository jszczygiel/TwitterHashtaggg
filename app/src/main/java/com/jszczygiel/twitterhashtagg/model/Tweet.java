package com.jszczygiel.twitterhashtagg.model;

/**
 * Tweet object used by application
 *
 * Created by jszczygiel on 15.08.14.
 */
public class Tweet {
    private String userName;
    private String content;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
