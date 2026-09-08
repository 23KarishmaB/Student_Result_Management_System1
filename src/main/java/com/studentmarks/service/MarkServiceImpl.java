package com.studentmarks.service;

import com.studentmarks.dao.CurriculumDAO;
import com.studentmarks.dao.MarkDAO;
import com.studentmarks.dao.StudentDAO;

import com.studentmarks.dao.impl.CurriculumDAOImpl;
import com.studentmarks.dao.impl.MarkDAOImpl;
import com.studentmarks.dao.impl.StudentDAOImpl;

import com.studentmarks.model.Mark;
import com.studentmarks.model.Student;
import com.studentmarks.model.Subject;

import java.util.List;

public class MarkServiceImpl implements MarkService {

    private final MarkDAO markDAO;
    private final StudentDAO studentDAO;
    private final CurriculumDAO curriculumDAO;
    private final GradeCalculator gradeCalculator;

    public MarkServiceImpl() {

        this.markDAO = new MarkDAOImpl();
        this.studentDAO = new StudentDAOImpl();
        this.curriculumDAO = new CurriculumDAOImpl();
        this.gradeCalculator = new GradeCalculator();

    }

    @Override
    public void addMark(Mark mark) {

        // =========================================
        // VALIDATE MARK OBJECT
        // =========================================

        if (mark == null) {

            throw new IllegalArgumentException(
                    "Mark cannot be null.");
        }

        // =========================================
        // VALIDATE SCORE
        // =========================================

        validateScore(mark.getScore());

        // =========================================
        // FIND STUDENT
        // =========================================

        Student student =
                studentDAO.getStudentById(
                        mark.getStudentId());

        if (student == null) {

            throw new IllegalArgumentException(
                    "Student not found.");
        }

        // =========================================
        // VALIDATE SEMESTER
        // =========================================

        int semester = mark.getSemester();

        if (semester < 1 || semester > 8) {

            throw new IllegalArgumentException(
                    "Semester must be between 1 and 8.");
        }

        if (semester > student.getCurrentSemester()) {

            throw new IllegalArgumentException(
                    "Marks can only be managed through the student's current semester.");
        }

        // =========================================
        // VALIDATE SUBJECT AGAINST CURRICULUM
        // =========================================

        validateSubject(
                student.getDepartmentId(),
                semester,
                mark.getSubjectCode());

        // =========================================
        // CALCULATE GRADE
        // =========================================

        String grade =
                gradeCalculator.calculateGrade(
                        mark.getScore());

        mark.setGrade(grade);

        // =========================================
        // CHECK WHETHER MARK ALREADY EXISTS
        // =========================================

        List<Mark> existingMarks =
                markDAO.getMarksByStudentAndSemester(
                        mark.getStudentId(),
                        semester);

        boolean markExists = false;

        for (Mark existingMark : existingMarks) {

            if (existingMark.getSubjectCode()
                    .equalsIgnoreCase(
                            mark.getSubjectCode())) {

                markExists = true;
                break;
            }
        }

        // =========================================
        // INSERT OR UPDATE
        // =========================================

        if (markExists) {

            // Mark already exists → UPDATE
            markDAO.updateMark(mark);

        } else {

            // Mark does not exist → INSERT
            markDAO.addMark(mark);
        }
    }

    @Override
    public void updateMark(Mark mark) {

        // =========================================
        // VALIDATE MARK
        // =========================================

        if (mark == null) {

            throw new IllegalArgumentException(
                    "Mark cannot be null.");
        }

        validateScore(mark.getScore());

        // =========================================
        // FIND STUDENT
        // =========================================

        Student student =
                studentDAO.getStudentById(
                        mark.getStudentId());

        if (student == null) {

            throw new IllegalArgumentException(
                    "Student not found.");
        }

        // =========================================
        // VALIDATE SUBJECT
        // =========================================

        int semester = mark.getSemester();

        validateSemesterForStudent(student, semester);

        validateSubject(
                student.getDepartmentId(),
                semester,
                mark.getSubjectCode());

        // =========================================
        // CALCULATE GRADE
        // =========================================

        String grade =
                gradeCalculator.calculateGrade(
                        mark.getScore());

        mark.setGrade(grade);

        // =========================================
        // UPDATE MARK
        // =========================================

        markDAO.updateMark(mark);
    }

    @Override
    public void deleteMark(
            int studentId,
            int semester,
            String subjectCode) {

        Student student =
                studentDAO.getStudentById(studentId);

        if (student == null) {

            throw new IllegalArgumentException(
                    "Student not found.");
        }

        if (subjectCode == null
                || subjectCode.isBlank()) {

            throw new IllegalArgumentException(
                    "Subject code is required.");
        }

        validateSemesterForStudent(student, semester);

        markDAO.deleteMark(
                studentId,
                semester,
                subjectCode);
    }

    @Override
    public List<Mark> getMarksByStudent(
            int studentId) {

        Student student =
                studentDAO.getStudentById(studentId);

        if (student == null) {

            throw new IllegalArgumentException(
                    "Student not found.");
        }

        return markDAO.getMarksByStudent(
                studentId);
    }

    @Override
    public List<Mark> getMarksByStudentAndSemester(
            int studentId,
            int semester) {

        // =========================================
        // VALIDATE SEMESTER
        // =========================================

        if (semester < 1 || semester > 8) {

            throw new IllegalArgumentException(
                    "Semester must be between 1 and 8.");
        }

        // =========================================
        // FIND STUDENT
        // =========================================

        Student student =
                studentDAO.getStudentById(
                        studentId);

        if (student == null) {

            throw new IllegalArgumentException(
                    "Student not found.");
        }

        validateSemesterForStudent(student, semester);

        return markDAO.getMarksByStudentAndSemester(
                studentId,
                semester);
    }

    // =============================================
    // VALIDATE SCORE
    // =============================================

    private void validateScore(double score) {

        if (score < 0 || score > 100) {

            throw new IllegalArgumentException(
                    "Marks must be between 0 and 100.");
        }
    }

    private void validateSemesterForStudent(Student student, int semester) {

        if (semester < 1 || semester > 8) {
            throw new IllegalArgumentException(
                    "Semester must be between 1 and 8.");
        }

        if (semester > student.getCurrentSemester()) {
            throw new IllegalArgumentException(
                    "Semester is beyond the student's current semester.");
        }
    }

    // =============================================
    // VALIDATE SUBJECT AGAINST CURRICULUM
    // =============================================

    private void validateSubject(
            int departmentId,
            int semester,
            String subjectCode) {

        if (subjectCode == null
                || subjectCode.isBlank()) {

            throw new IllegalArgumentException(
                    "Subject code is required.");
        }

        List<Subject> subjects =
                curriculumDAO
                        .getSubjectsByDepartmentAndSemester(
                                departmentId,
                                semester);

        boolean subjectExists = false;

        for (Subject subject : subjects) {

            if (subject.getSubjectCode()
                    .equalsIgnoreCase(subjectCode)) {

                subjectExists = true;
                break;
            }
        }

        if (!subjectExists) {

            throw new IllegalArgumentException(
                    "Subject does not belong to this curriculum.");
        }
    }
}
