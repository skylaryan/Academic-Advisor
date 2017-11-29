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
    private double professorRating; // 4.9
    private double[][] gradeHidstory; // list of past semesters of grade info: each with avg gpa, percent A, AB, ...
    private String description; // This is a class where you will learn how to program.
    private String[] schedule; // MWF 2:30-3:45 & F 4-5 as {"14:30-15:45","","14:30-15:45","","14:30-15:45|16:00-17:00"}

    public Course(String course, int section, String title, int numCredits, String[] breadth, String professor,
                  double professorRating, /*double[][] gradeHistory, */String description, String[] schedule) {
        this.course = course;
        this.section = section;
        this.title = title;
        this.numCredits = numCredits;
        this.breadth = breadth;
        this.professor = professor;
        this.professorRating = professorRating;
        //this.gradeHistory = gradeHistory; TODO
        this.description = description;
        this.schedule = schedule;
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

    public double getProfessorRating() {
        return professorRating;
    }

    /*public double[][] getGradeHistory() {
        return gradeHistory;
    }*/

    public String getDescription() {
        return description;
    }

    public String[] getSchedule() {
        return schedule;
    }

}
