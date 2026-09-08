package com.studentmarks.controller;

import com.studentmarks.dao.DepartmentDAO;
import com.studentmarks.dao.impl.DepartmentDAOImpl;
import com.studentmarks.service.StudentService;
import com.studentmarks.service.StudentServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin-dashboard")
public class AdminDashboardServlet extends HttpServlet {

	private StudentService studentService;
	private DepartmentDAO departmentDAO;

	@Override
	public void init() {
		studentService = new StudentServiceImpl();
		departmentDAO = new DepartmentDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || !Boolean.TRUE.equals(session.getAttribute("adminLoggedIn"))) {

			response.sendRedirect(request.getContextPath() + "/admin-login");

			return;
		}

		request.setAttribute("totalStudents", studentService.getAllStudents().size());
		request.setAttribute("totalDepartments", departmentDAO.getAllDepartments().size());

		request.getRequestDispatcher("/admin-dashboard.jsp").forward(request, response);
	}
}