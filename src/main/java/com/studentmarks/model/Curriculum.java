package com.studentmarks.model;

public class Curriculum {

    private int departmentId;
    private int semester;
    private String subjectCode;

    public Curriculum() {
    }

    public Curriculum(int departmentId, int semester,
                      String subjectCode) {
        this.departmentId = departmentId;
        this.semester = semester;
        this.subjectCode = subjectCode;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
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
}