package com.studentmarks.model;

import java.util.List;
import java.util.Map;

public class Result {

    private Student student;
    private Department department;
    private int semester;
    private List<Mark> marks;

    private double totalMarks;
    private double percentage;
    private String overallGrade;
    private Map<String, String> subjectNames;

    public Result() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getOverallGrade() {
        return overallGrade;
    }

    public void setOverallGrade(String overallGrade) {
        this.overallGrade = overallGrade;
    }

    public Map<String, String> getSubjectNames() {
        return subjectNames;
    }

    public void setSubjectNames(Map<String, String> subjectNames) {
        this.subjectNames = subjectNames;
    }
}