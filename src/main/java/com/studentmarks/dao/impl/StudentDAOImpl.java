package com.studentmarks.dao.impl;

import com.studentmarks.dao.StudentDAO;
import com.studentmarks.model.Student;
import com.studentmarks.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public void addStudent(Student student) {

        String sql = """
                INSERT INTO STUDENT
                (roll_no, first_name, last_name, department_id, current_semester, password_hash)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getRollNo());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setInt(4, student.getDepartmentId());
            statement.setInt(5, student.getCurrentSemester());
            statement.setString(6, student.getPasswordHash());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStudent(Student student) {

        String sql = """
                UPDATE STUDENT
                SET roll_no = ?,
                    first_name = ?,
                    last_name = ?,
                    department_id = ?,
                    current_semester = ?
                WHERE student_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getRollNo());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setInt(4, student.getDepartmentId());
            statement.setInt(5, student.getCurrentSemester());
            statement.setInt(6, student.getStudentId());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStudent(int studentId) {

        String sql = """
                DELETE FROM STUDENT
                WHERE student_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getStudentById(int studentId) {

        String sql = """
                SELECT student_id, roll_no, first_name, last_name,
                       department_id, current_semester
                       , password_hash
                FROM STUDENT
                WHERE student_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapStudent(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Student getStudentByRollNo(String rollNo) {

        String sql = """
                SELECT student_id, roll_no, first_name, last_name,
                       department_id, current_semester
                       , password_hash
                FROM STUDENT
                WHERE roll_no = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, rollNo);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapStudent(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void promoteStudent(int studentId) {

        String sql = """
                UPDATE STUDENT
                SET current_semester = current_semester + 1
                WHERE student_id = ?
                AND current_semester < 8
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getStudentByUsn(String usn) {
        return getStudentByRollNo(usn);
    }

    @Override
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql = """
                SELECT student_id, roll_no, first_name, last_name,
                       department_id, current_semester
                       , password_hash
                FROM STUDENT
                ORDER BY student_id
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                students.add(mapStudent(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    @Override
    public List<Student> searchStudents(String searchTerm) {

        List<Student> students = new ArrayList<>();

        String sql = """
                SELECT student_id, roll_no, first_name, last_name,
                       department_id, current_semester, password_hash
                FROM STUDENT
                WHERE UPPER(roll_no) LIKE UPPER(?)
                   OR UPPER(first_name || ' ' || NVL(last_name, '')) LIKE UPPER(?)
                ORDER BY student_id
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String pattern = "%" + searchTerm + "%";
            statement.setString(1, pattern);
            statement.setString(2, pattern);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(mapStudent(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    private Student mapStudent(ResultSet resultSet) throws Exception {

        Student student = new Student();

        student.setStudentId(
                resultSet.getInt("student_id"));

        student.setRollNo(
                resultSet.getString("roll_no"));

        student.setFirstName(
                resultSet.getString("first_name"));

        student.setLastName(
                resultSet.getString("last_name"));

        student.setDepartmentId(
                resultSet.getInt("department_id"));

        student.setCurrentSemester(
                resultSet.getInt("current_semester"));

        student.setPasswordHash(
                resultSet.getString("password_hash"));

        return student;
    }
}
