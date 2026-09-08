package com.studentmarks.controller;

import com.studentmarks.dao.DepartmentDAO;
import com.studentmarks.dao.impl.DepartmentDAOImpl;
import com.studentmarks.model.Department;
import com.studentmarks.model.Student;
import com.studentmarks.service.StudentService;
import com.studentmarks.service.StudentServiceImpl;
import com.studentmarks.service.SemesterPublicationService;
import com.studentmarks.service.SemesterPublicationServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/student-profile")
public class StudentProfileServlet extends HttpServlet {

    private StudentService studentService;
    private DepartmentDAO departmentDAO;
    private SemesterPublicationService semesterPublicationService;

    @Override
    public void init() {
        studentService = new StudentServiceImpl();
        departmentDAO = new DepartmentDAOImpl();
        semesterPublicationService = new SemesterPublicationServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int studentId = (Integer) session.getAttribute("studentId");

        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/student-login");
            return;
        }

        Department department = departmentDAO.getDepartmentById(
                student.getDepartmentId());

        List<Integer> releasedSemesters = new ArrayList<>();
        for (int semester = 1;
             semester <= student.getCurrentSemester();
             semester++) {
            if (semesterPublicationService.isSemesterReleased(semester)) {
                releasedSemesters.add(semester);
            }
        }

        request.setAttribute("student", student);
        request.setAttribute("department", department);
        request.setAttribute("releasedSemesters", releasedSemesters);
        request.setAttribute("resultMessage",
                session.getAttribute("studentResultMessage"));
        session.removeAttribute("studentResultMessage");
        request.getRequestDispatcher("/student-profile.jsp")
                .forward(request, response);
    }
}
