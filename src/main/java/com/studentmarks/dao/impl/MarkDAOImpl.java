package com.studentmarks.dao.impl;

import com.studentmarks.dao.MarkDAO;
import com.studentmarks.model.Mark;
import com.studentmarks.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MarkDAOImpl implements MarkDAO {

    @Override
    public void addMark(Mark mark) {

        String sql = """
                INSERT INTO MARKS
                (student_id, semester, subject_code, score, grade)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, mark.getStudentId());
            statement.setInt(2, mark.getSemester());
            statement.setString(3, mark.getSubjectCode());
            statement.setDouble(4, mark.getScore());
            statement.setString(5, mark.getGrade());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMark(Mark mark) {

        String sql = """
                UPDATE MARKS
                SET score = ?,
                    grade = ?
                WHERE student_id = ?
                AND semester = ?
                AND subject_code = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, mark.getScore());
            statement.setString(2, mark.getGrade());
            statement.setInt(3, mark.getStudentId());
            statement.setInt(4, mark.getSemester());
            statement.setString(5, mark.getSubjectCode());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMark(int studentId, int semester, String subjectCode) {

        String sql = """
                DELETE FROM MARKS
                WHERE student_id = ?
                AND semester = ?
                AND subject_code = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, semester);
            statement.setString(3, subjectCode);

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Mark> getMarksByStudent(int studentId) {

        List<Mark> marks = new ArrayList<>();

        String sql = """
                SELECT student_id, semester, subject_code, score, grade
                FROM MARKS
                WHERE student_id = ?
                ORDER BY semester, subject_code
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    marks.add(mapMark(resultSet));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return marks;
    }

    @Override
    public List<Mark> getMarksByStudentAndSemester(
            int studentId, int semester) {

        List<Mark> marks = new ArrayList<>();

        String sql = """
                SELECT student_id, semester, subject_code, score, grade
                FROM MARKS
                WHERE student_id = ?
                AND semester = ?
                ORDER BY subject_code
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, semester);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    marks.add(mapMark(resultSet));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return marks;
    }

    private Mark mapMark(ResultSet resultSet) throws Exception {

        Mark mark = new Mark();

        mark.setStudentId(
                resultSet.getInt("student_id"));

        mark.setSemester(
                resultSet.getInt("semester"));

        mark.setSubjectCode(
                resultSet.getString("subject_code"));

        mark.setScore(
                resultSet.getDouble("score"));

        mark.setGrade(
                resultSet.getString("grade"));

        return mark;
    }
}
