package com.studentmarks.dao.impl;

import com.studentmarks.dao.SubjectDAO;
import com.studentmarks.model.Subject;
import com.studentmarks.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAOImpl implements SubjectDAO {

    @Override
    public Subject getSubjectByCode(String subjectCode) {

        String sql = """
                SELECT subject_code, subject_name
                FROM SUBJECT
                WHERE subject_code = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, subjectCode);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Subject subject = new Subject();

                    subject.setSubjectCode(
                            resultSet.getString("subject_code"));

                    subject.setSubjectName(
                            resultSet.getString("subject_name"));

                    return subject;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Subject> getAllSubjects() {

        List<Subject> subjects = new ArrayList<>();

        String sql = """
                SELECT subject_code, subject_name
                FROM SUBJECT
                ORDER BY subject_code
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Subject subject = new Subject();

                subject.setSubjectCode(
                        resultSet.getString("subject_code"));

                subject.setSubjectName(
                        resultSet.getString("subject_name"));

                subjects.add(subject);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return subjects;
    }
}