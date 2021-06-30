package com.example.cengonline.model;

public class Assignment {
    private String assignmentId, assignmentInfoText, assignmentDate, courseId, courseName;

    public Assignment() {
    }

    public Assignment(String assignmentId, String assignmentInfoText, String assignmentDate, String courseId, String courseName) {
        this.assignmentId = assignmentId;
        this.assignmentInfoText = assignmentInfoText;
        this.assignmentDate = assignmentDate;
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getAssignmentInfoText() {
        return assignmentInfoText;
    }

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }
}
