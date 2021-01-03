package com.example.retrofittest.models;

public class Post {

    private int userId;
    private int id;
    private String title;
    private String body;

    public Post(int userId, String title, String body) { // i don't select id because it is automatically created by the rest api.
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
