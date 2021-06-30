package com.example.cengonline.model;

public class Announcement
{
    private String assignmentId;
    private String announceStableText;
    private String postId;
    private String courseId;
    private String courseName;
    private boolean isPost;


    public Announcement(String userId, String announceStableText, String postId, boolean isPost,String courseId,String courseName) {
        this.assignmentId = userId;
        this.announceStableText = announceStableText;
        this.postId = postId;
        this.isPost = isPost;
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Announcement() {
    }

    public String getAnnounceStableText() {
        return announceStableText;
    }

    public String getPostId() {
        return postId;
    }

    public boolean isPost() {
        return isPost;
    }
}
