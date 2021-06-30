package com.example.cengonline.model;

public class Post {

    private String postId;
    private String postType; //assignments or post
    private String postText;
    private String userId;
    private String postUserName;
    private String dateTime;

    public Post() {
    }

    public Post(String postId, String postType, String postText, String userId, String postUserName, String dateTime) {
        this.postId = postId;
        this.postType = postType;
        this.postText = postText;
        this.userId = userId;
        this.postUserName = postUserName;
        this.dateTime = dateTime;
    }

    public String getPostId() {
        return postId;
    }

    public String getPostType() {
        return postType;
    }

    public String getPostText() {
        return postText;
    }

    public String getUserId() {
        return userId;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public String getDateTime() {
        return dateTime;
    }
}
