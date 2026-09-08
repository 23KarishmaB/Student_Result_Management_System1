package com.studentmarks.service;

import com.studentmarks.dao.DepartmentDAO;
import com.studentmarks.dao.StudentDAO;
import com.studentmarks.dao.CurriculumDAO;
import com.studentmarks.dao.MarkDAO;

import com.studentmarks.dao.impl.DepartmentDAOImpl;
import com.studentmarks.dao.impl.StudentDAOImpl;
import com.studentmarks.dao.impl.CurriculumDAOImpl;
import com.studentmarks.dao.impl.MarkDAOImpl;

import com.studentmarks.model.Department;
import com.studentmarks.model.Student;
import com.studentmarks.model.Subject;
import com.studentmarks.model.Mark;
import com.studentmarks.model.Result;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ResultServiceImpl implements ResultService {

    private final StudentDAO studentDAO;
    private final DepartmentDAO departmentDAO;
    private final CurriculumDAO curriculumDAO;
    private final MarkDAO markDAO;

    private final GradeCalculator gradeCalculator;

    public ResultServiceImpl() {
        this.studentDAO = new StudentDAOImpl();
        this.departmentDAO = new DepartmentDAOImpl();
        this.curriculumDAO = new CurriculumDAOImpl();
        this.markDAO = new MarkDAOImpl();

        this.gradeCalculator = new GradeCalculator();
    }

    @Override
    public Result getStudentResult(
            String rollNo,
            String firstName,
            String lastName,
            int departmentId,
            int semester) {

        // Step 1: Validate semester
        if (semester < 1 || semester > 8) {
            throw new IllegalArgumentException(
                    "Semester must be between 1 and 8.");
        }

        // Step 2: Find student by roll number
        Student student = studentDAO.getStudentByRollNo(rollNo);

        if (student == null) {
            throw new IllegalArgumentException(
                    "Student not found.");
        }

        if (semester > student.getCurrentSemester()) {
            throw new IllegalArgumentException(
                    "Semester is beyond the student's current semester.");
        }

        // Step 3: Verify student's name
        if (!student.getFirstName().equalsIgnoreCase(firstName)
                || !student.getLastName().equalsIgnoreCase(lastName)) {

            throw new IllegalArgumentException(
                    "Student name does not match the registered student.");
        }

        // Step 4: Verify department
        if (student.getDepartmentId() != departmentId) {
            throw new IllegalArgumentException(
                    "Department does not match the student.");
        }

        return buildResult(student, semester);
    }

    @Override
    public Result getStudentResultByStudentId(int studentId, int semester) {
        if (semester < 1 || semester > 8) {
            throw new IllegalArgumentException(
                    "Semester must be between 1 and 8.");
        }

        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found.");
        }

        if (semester > student.getCurrentSemester()) {
            throw new IllegalArgumentException(
                    "Semester is beyond the student's current semester.");
        }

        return buildResult(student, semester);
    }

    private Result buildResult(Student student, int semester) {

        // Step 5: Get department details
        Department department =
                departmentDAO.getDepartmentById(student.getDepartmentId());

        if (department == null) {
            throw new IllegalArgumentException(
                    "Department not found.");
        }

        // Step 6: Get curriculum subjects
        List<Subject> subjects =
                curriculumDAO.getSubjectsByDepartmentAndSemester(
                        student.getDepartmentId(), semester);

        if (subjects.isEmpty()) {
            throw new IllegalStateException(
                "No subjects are configured for this semester.");
        }

        // Step 7: Get student's marks for this semester
        List<Mark> marks =
                markDAO.getMarksByStudentAndSemester(
                        student.getStudentId(), semester);

        if (marks.size() != subjects.size()) {
            throw new IllegalStateException(
                    "Complete marks are not available for this semester.");
        }

        Map<String, String> subjectNames = new HashMap<>();
        for (Subject subject : subjects) {
            subjectNames.put(subject.getSubjectCode(), subject.getSubjectName());
        }

        // Step 8: Calculate subject grades
        double totalMarks = 0;

        for (Mark mark : marks) {

            String grade =
                    gradeCalculator.calculateGrade(mark.getScore());

            mark.setGrade(grade);

            totalMarks += mark.getScore();
        }

        // Step 9: Calculate percentage
        double percentage = totalMarks / subjects.size();

        // Step 10: Calculate overall grade
        String overallGrade =
                gradeCalculator.calculateGrade(percentage);

        // Step 11: Create Result object
        Result result = new Result();

        result.setStudent(student);
        result.setDepartment(department);
        result.setSemester(semester);
        result.setMarks(marks);
        result.setTotalMarks(totalMarks);
        result.setPercentage(percentage);
        result.setOverallGrade(overallGrade);
        result.setSubjectNames(subjectNames);

        return result;
    }
}
