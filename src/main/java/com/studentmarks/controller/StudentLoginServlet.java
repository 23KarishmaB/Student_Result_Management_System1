package com.studentmarks.controller;

import com.studentmarks.model.Student;
import com.studentmarks.service.StudentService;
import com.studentmarks.service.StudentServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/student-login")
public class StudentLoginServlet extends HttpServlet {

    private StudentService studentService;

    @Override
    public void init() {
        studentService = new StudentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/student-login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Student student = studentService.authenticateStudent(
                request.getParameter("usn"),
                request.getParameter("password"));

        if (student == null) {
            request.setAttribute("errorMessage", "Invalid USN or password.");
            request.getRequestDispatcher("/student-login.jsp")
                    .forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        request.changeSessionId();
        session.removeAttribute("adminLoggedIn");
        session.setAttribute("studentId", student.getStudentId());

        response.sendRedirect(request.getContextPath() + "/student-profile");
    }
}
