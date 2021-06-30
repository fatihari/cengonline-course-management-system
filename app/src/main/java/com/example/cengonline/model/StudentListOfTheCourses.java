package com.example.cengonline.model;

public class StudentListOfTheCourses
{
    private String studentId,studentEmail,studentName,studentPicture,courseId;

    public StudentListOfTheCourses() {
    }

    public StudentListOfTheCourses(String studentId, String studentEmail, String studentName,
                                   String studentPicture,String courseId) {
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.studentName = studentName;
        this.studentPicture = studentPicture;
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentPicture() {
        return studentPicture;
    }
}
