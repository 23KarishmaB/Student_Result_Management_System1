package com.studentmarks.dao;

import com.studentmarks.model.Subject;

import java.util.List;

public interface SubjectDAO {

    Subject getSubjectByCode(String subjectCode);

    List<Subject> getAllSubjects();
}