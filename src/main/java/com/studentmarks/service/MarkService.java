package com.studentmarks.service;

import com.studentmarks.model.Mark;

import java.util.List;

public interface MarkService {

    void addMark(Mark mark);

    void updateMark(Mark mark);

    void deleteMark(int studentId, int semester, String subjectCode);

    List<Mark> getMarksByStudent(int studentId);

    List<Mark> getMarksByStudentAndSemester(
            int studentId, int semester);
}
