package com.studentmarks.util;

import com.studentmarks.model.Student;
import com.studentmarks.service.StudentService;
import com.studentmarks.service.StudentServiceImpl;

public class StudentServiceTest {

    public static void main(String[] args) {

        StudentService studentService =
                new StudentServiceImpl();

        try {
            // 1. CREATE
            Student student = new Student();

            student.setRollNo("TEST001");
            student.setFirstName("Test");
            student.setLastName("Student");
            student.setDepartmentId(1);       // CS
            student.setCurrentSemester(1);

            studentService.addStudent(student);

            System.out.println("1. Student added successfully.");

            // 2. READ
            Student savedStudent =
                    studentService.getStudentByRollNo("TEST001");

            if (savedStudent == null) {
                System.out.println("Student could not be found.");
                return;
            }

            System.out.println(
                    "2. Student found: "
                    + savedStudent.getFirstName()
                    + " "
                    + savedStudent.getLastName());

            System.out.println(
                    "   Student ID: "
                    + savedStudent.getStudentId());

            // 3. UPDATE
            savedStudent.setFirstName("UpdatedTest");

            studentService.updateStudent(savedStudent);

            System.out.println("3. Student updated successfully.");

            // 4. READ AGAIN
            Student updatedStudent =
                    studentService.getStudentByRollNo("TEST001");

            System.out.println(
                    "4. Updated name: "
                    + updatedStudent.getFirstName());

            // 5. DELETE
            studentService.deleteStudent(
                    updatedStudent.getStudentId());

            System.out.println("5. Student deleted successfully.");

            // 6. CONFIRM DELETE
            Student deletedStudent =
                    studentService.getStudentByRollNo("TEST001");

            if (deletedStudent == null) {
                System.out.println(
                        "6. Delete confirmed. Student no longer exists.");
            }

        } catch (Exception e) {

            System.out.println("Test failed!");
            e.printStackTrace();
        }
    }
}