package com.studentmarks.service;

import com.studentmarks.model.Result;

public interface ResultService {

    Result getStudentResult(
            String rollNo,
            String firstName,
            String lastName,
            int departmentId,
            int semester);

    Result getStudentResultByStudentId(int studentId, int semester);
}
