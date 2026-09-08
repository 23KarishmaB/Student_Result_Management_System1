package com.studentmarks.service;

import com.studentmarks.model.Student;

import java.util.List;

public interface StudentService {

    void addStudent(Student student);

    void updateStudent(Student student);

    void promoteStudent(int studentId);

    void deleteStudent(int studentId);

    Student getStudentById(int studentId);

    Student getStudentByRollNo(String rollNo);

    Student authenticateStudent(String usn, String password);

    List<Student> getAllStudents();

    List<Student> searchStudents(String searchTerm);
}
