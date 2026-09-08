package com.studentmarks.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin-login")
public class AdminLoginServlet extends HttpServlet {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/admin-login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");

        if (ADMIN_USERNAME.equals(username)
                && ADMIN_PASSWORD.equals(password)) {

            HttpSession session =
                    request.getSession();

            session.removeAttribute("studentId");

            session.setAttribute(
                    "adminLoggedIn",
                    true);

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin-dashboard");

        } else {

            request.setAttribute(
                    "errorMessage",
                    "Invalid username or password.");

            request.getRequestDispatcher(
                    "/admin-login.jsp")
                    .forward(request, response);
        }
    }
}
