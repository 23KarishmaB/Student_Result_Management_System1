package com.studentmarks.dao.impl;

import com.studentmarks.dao.CurriculumDAO;
import com.studentmarks.model.Subject;
import com.studentmarks.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CurriculumDAOImpl implements CurriculumDAO {

    @Override
    public List<Subject> getSubjectsByDepartmentAndSemester(
            int departmentId, int semester) {

        List<Subject> subjects = new ArrayList<>();

        String sql = """
                SELECT s.subject_code, s.subject_name
                FROM CURRICULUM c
                JOIN SUBJECT s
                    ON c.subject_code = s.subject_code
                WHERE c.department_id = ?
                AND c.semester = ?
                ORDER BY s.subject_code
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, departmentId);
            statement.setInt(2, semester);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Subject subject = new Subject();

                    subject.setSubjectCode(
                            resultSet.getString("subject_code"));

                    subject.setSubjectName(
                            resultSet.getString("subject_name"));

                    subjects.add(subject);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return subjects;
    }
}