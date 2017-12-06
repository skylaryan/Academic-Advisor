package edu.wisc.academicadvisor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String course; // CS200
    private int section; // 001
    private String title; // Introduction to Programming
    private int numCredits; // 3
    private String[] breadth; // {"Natural Sciences"}
    private String professor; // John Doe
    private String description; // This is a class where you will learn how to program.

    public Course(String course, int section, String title, int numCredits, String[] breadth, String professor,
                  String description) {
        this.course = course;
        this.section = section;
        this.title = title;
        this.numCredits = numCredits;
        this.breadth = breadth;
        this.professor = professor;
        this.description = description;
    }

    public String getCourse() {
        return course;
    }

    public String getFullCourse() {
        return course + "-" + String.format("%03d", section);
    }

    public String getDepartment() {
        return course.split("(?<=\\D)(?=\\d)")[0];
    }

    public String getNumber() {
        return course.split("(?<=\\D)(?=\\d)")[1];
    }

    public int getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    public int getNumCredits() {
        return numCredits;
    }

    public String[] getBreadth() {
        return breadth;
    }

    public String getProfessor() {
        return professor;
    }

    public String getDescription() {
        return description;
    }

}
