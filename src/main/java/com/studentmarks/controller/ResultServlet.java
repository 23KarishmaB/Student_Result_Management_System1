package com.studentmarks.controller;

import com.studentmarks.dao.DepartmentDAO;
import com.studentmarks.dao.impl.DepartmentDAOImpl;
import com.studentmarks.model.Department;
import com.studentmarks.model.Result;
import com.studentmarks.service.ResultService;
import com.studentmarks.service.ResultServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {

    private ResultService resultService;
    private DepartmentDAO departmentDAO;

    @Override
    public void init() {

        resultService = new ResultServiceImpl();
        departmentDAO = new DepartmentDAOImpl();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null
                && session.getAttribute("studentId") instanceof Integer) {
            response.sendRedirect(request.getContextPath() + "/student-profile");
            return;
        }

        List<Department> departments =
                departmentDAO.getAllDepartments();

        request.setAttribute(
                "departments",
                departments);

        request.getRequestDispatcher(
                "/result-search.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            request.setCharacterEncoding("UTF-8");

            String rollNo =
                    request.getParameter("rollNo");

            String firstName =
                    request.getParameter("firstName");

            String lastName =
                    request.getParameter("lastName");

            int departmentId =
                    Integer.parseInt(
                            request.getParameter("departmentId"));

            int semester =
                    Integer.parseInt(
                            request.getParameter("semester"));

            Result result =
                    resultService.getStudentResult(
                            rollNo,
                            firstName,
                            lastName,
                            departmentId,
                            semester);

            request.setAttribute(
                    "result",
                    result);

            request.getRequestDispatcher(
                    "/result.jsp")
                    .forward(request, response);

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            request.setAttribute(
                    "errorMessage",
                    e.getMessage());

            doGet(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "errorMessage",
                    "Unable to retrieve result.");

            doGet(request, response);
        }
    }
}
