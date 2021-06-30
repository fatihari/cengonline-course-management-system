package com.example.cengonline.model;

public class UploadAssignment {
    private String courseId, assignmentId, assignmentInfoText,uploadAssignment,studentId;

    public UploadAssignment(String courseId, String assignmentId, String assignmentInfoText, String uploadAssignment, String studentId) {
        this.courseId = courseId;
        this.assignmentId = assignmentId;
        this.assignmentInfoText = assignmentInfoText;
        this.uploadAssignment = uploadAssignment;
        this.studentId = studentId;
    }

    public UploadAssignment() {
    }

    public String getCourseId() {
        return courseId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getAssignmentInfoText() {
        return assignmentInfoText;
    }

    public String getUploadAssignment() {
        return uploadAssignment;
    }

    public String getStudentId() {
        return studentId;
    }
}
