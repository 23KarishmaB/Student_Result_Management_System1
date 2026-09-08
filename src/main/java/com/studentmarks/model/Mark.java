package com.studentmarks.model;

public class Mark {

    private int studentId;
    private int semester;
    private String subjectCode;
    private double score;
    private String grade;

    public Mark() {
    }

    public Mark(int studentId, int semester, String subjectCode, double score, String grade) {
        this.studentId = studentId;
        this.semester = semester;
        this.subjectCode = subjectCode;
        this.score = score;
        this.grade = grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
