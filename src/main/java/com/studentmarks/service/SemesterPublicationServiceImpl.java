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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SemesterPublicationServiceImpl implements SemesterPublicationService {

    private final StudentDAO studentDAO = new StudentDAOImpl();
    private final CurriculumDAO curriculumDAO = new CurriculumDAOImpl();
    private final MarkDAO markDAO = new MarkDAOImpl();

    @Override
    public boolean isSemesterReleased(int semester) {
        if (semester < 1 || semester > 8) {
            throw new IllegalArgumentException("Semester must be between 1 and 8.");
        }

        List<Student> students = studentDAO.getAllStudents();
        boolean hasEligibleStudent = false;

        for (Student student : students) {
            if (student.getCurrentSemester() < semester) {
                continue;
            }

            hasEligibleStudent = true;
            List<Subject> subjects = curriculumDAO
                    .getSubjectsByDepartmentAndSemester(
                            student.getDepartmentId(), semester);

            if (subjects.isEmpty() || !hasCompleteMarks(
                    student.getStudentId(), semester, subjects)) {
                return false;
            }
        }

        return hasEligibleStudent;
    }

    private boolean hasCompleteMarks(int studentId, int semester,
                                     List<Subject> subjects) {
        List<Mark> marks = markDAO.getMarksByStudentAndSemester(
                studentId, semester);

        if (marks.size() != subjects.size()) {
            return false;
        }

        Set<String> requiredCodes = new HashSet<>();
        for (Subject subject : subjects) {
            requiredCodes.add(subject.getSubjectCode());
        }

        for (Mark mark : marks) {
            if (!requiredCodes.remove(mark.getSubjectCode())) {
                return false;
            }
        }

        return requiredCodes.isEmpty();
    }
}
