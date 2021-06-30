package com.example.cengonline.model;

public class Course {
    private String courseId, courseName, coursePeriod, courseCode, studentName,
            courseTeacherName, courseTeacherId, courseStudentsId ;

    public Course() {
    }

    public Course(String courseId, String courseName, String coursePeriod,
                  String courseCode, String studentName, String courseTeacherName, String courseTeacherId, String courseStudentsId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.coursePeriod = coursePeriod;
        this.courseCode = courseCode;
        this.studentName = studentName;
        this.courseTeacherName = courseTeacherName;
        this.courseTeacherId = courseTeacherId;
        this.courseStudentsId = courseStudentsId;
    }

    public String getCourseStudentsId() {
        return courseStudentsId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCoursePeriod() {
        return coursePeriod;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseTeacherName() {
        return courseTeacherName;
    }

    public String getCourseTeacherId() {
        return courseTeacherId;
    }
}
