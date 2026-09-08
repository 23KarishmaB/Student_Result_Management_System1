package com.studentmarks.util;

import com.studentmarks.model.Mark;
import com.studentmarks.model.Student;
import com.studentmarks.model.Subject;
import com.studentmarks.service.MarkService;
import com.studentmarks.service.MarkServiceImpl;
import com.studentmarks.service.StudentService;
import com.studentmarks.service.StudentServiceImpl;

import java.util.List;

public class MarkServiceTest {

    public static void main(String[] args) {

        StudentService studentService =
                new StudentServiceImpl();

        MarkService markService =
                new MarkServiceImpl();

        try {

            // -----------------------------------------
            // 1. CREATE TEST STUDENT
            // -----------------------------------------

            Student student = new Student();

            student.setRollNo("MARKTEST001");
            student.setFirstName("Mark");
            student.setLastName("Tester");
            student.setDepartmentId(1);       // CS
            student.setCurrentSemester(3);

            studentService.addStudent(student);

            System.out.println(
                    "1. Test student created successfully.");

            // -----------------------------------------
            // 2. GET CREATED STUDENT
            // -----------------------------------------

            Student savedStudent =
                    studentService.getStudentByRollNo(
                            "MARKTEST001");

            System.out.println(
                    "2. Student ID: "
                    + savedStudent.getStudentId());

            // -----------------------------------------
            // 3. GET SEMESTER 3 CURRICULUM
            // -----------------------------------------

            List<Subject> subjects =
                    new com.studentmarks.dao.impl.CurriculumDAOImpl()
                            .getSubjectsByDepartmentAndSemester(
                                    1, 3);

            System.out.println(
                    "3. Semester 3 subjects: "
                    + subjects.size());

            for (Subject subject : subjects) {

                System.out.println(
                        "   "
                        + subject.getSubjectCode()
                        + " - "
                        + subject.getSubjectName());
            }

            // -----------------------------------------
            // 4. ADD MARKS
            // -----------------------------------------

            double[] scores = {
                    85, 91, 76, 88, 72
            };

            for (int i = 0; i < subjects.size(); i++) {

                Mark mark = new Mark();

                mark.setStudentId(
                        savedStudent.getStudentId());

                mark.setSemester(3);

                mark.setSubjectCode(
                        subjects.get(i).getSubjectCode());

                mark.setScore(scores[i]);

                markService.addMark(mark);
            }

            System.out.println(
                    "4. Five marks added successfully.");

            // -----------------------------------------
            // 5. RETRIEVE MARKS
            // -----------------------------------------

            List<Mark> marks =
                    markService.getMarksByStudentAndSemester(
                            savedStudent.getStudentId(), 3);

            System.out.println(
                    "5. Marks retrieved: "
                    + marks.size());

            // -----------------------------------------
            // 6. DISPLAY GRADES + CALCULATE TOTAL
            // -----------------------------------------

            double total = 0;

            for (Mark mark : marks) {

                System.out.println(
                        "   "
                        + mark.getSubjectCode()
                        + " → "
                        + mark.getScore()
                        + " → "
                        + mark.getGrade());

                total += mark.getScore();
            }

            double percentage = total / 5;

            System.out.println(
                    "6. Total Marks: "
                    + total + " / 500");

            System.out.println(
                    "   Percentage: "
                    + percentage + "%");

            // -----------------------------------------
            // 7. DELETE TEST STUDENT
            // -----------------------------------------

            studentService.deleteStudent(
                    savedStudent.getStudentId());

            System.out.println(
                    "7. Test student deleted successfully.");

        } catch (Exception e) {

            System.out.println(
                    "Test failed!");

            e.printStackTrace();
        }
    }
}
