package com.snowy.demo.zrxjava;

/**
 * Created by zx on 17-3-9.
 */

public class Course {
    public String courseName;
    public float score;

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
