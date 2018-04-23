package com.forum;

import java.io.Serializable;

public class Post implements Serializable {
    private final String author;
    private String message;

    public Post(String author,String message){
        this.author=author;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
