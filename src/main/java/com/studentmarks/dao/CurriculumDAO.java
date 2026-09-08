package com.studentmarks.dao;

import com.studentmarks.model.Subject;

import java.util.List;

public interface CurriculumDAO {

    List<Subject> getSubjectsByDepartmentAndSemester(
            int departmentId, int semester);
}