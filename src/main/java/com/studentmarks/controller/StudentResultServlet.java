package com.studentmarks.controller;

import com.studentmarks.model.Result;
import com.studentmarks.model.Student;
import com.studentmarks.service.ResultService;
import com.studentmarks.service.ResultServiceImpl;
import com.studentmarks.service.SemesterPublicationService;
import com.studentmarks.service.SemesterPublicationServiceImpl;
import com.studentmarks.service.StudentService;
import com.studentmarks.service.StudentServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/student-result")
public class StudentResultServlet extends HttpServlet {

    private StudentService studentService;
    private ResultService resultService;
    private SemesterPublicationService semesterPublicationService;

    @Override
    public void init() {
        studentService = new StudentServiceImpl();
        resultService = new ResultServiceImpl();
        semesterPublicationService = new SemesterPublicationServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int studentId = (Integer) session.getAttribute("studentId");

        try {
            int semester = Integer.parseInt(request.getParameter("semester"));
            Student student = studentService.getStudentById(studentId);

            if (student == null) {
                throw new IllegalStateException(
                        "Your student account is no longer available.");
            }

            if (semester < 1 || semester > student.getCurrentSemester()) {
                throw new IllegalArgumentException(
                        "That semester is not available for your account.");
            }

            if (!semesterPublicationService.isSemesterReleased(semester)) {
                throw new IllegalStateException(
                        "This semester's result has not been released yet.");
            }

            Result result = resultService.getStudentResultByStudentId(
                    studentId, semester);
            request.setAttribute("result", result);
            request.getRequestDispatcher("/result.jsp").forward(request, response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            session.setAttribute("studentResultMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/student-profile");
        }
    }
}
