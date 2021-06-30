package com.example.cengonline.model;

public class Comment {

    private String commentText, userId;

    public Comment(String commentText, String userId) {
        this.commentText = commentText;
        this.userId = userId;
    }

    public Comment() {
    }

    public String getCommentText() {
        return commentText;
    }

    public String getUserId() {
        return userId;
    }

}
