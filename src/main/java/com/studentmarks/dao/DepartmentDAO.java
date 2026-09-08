package com.studentmarks.dao;

import com.studentmarks.model.Department;

import java.util.List;

public interface DepartmentDAO {

    Department getDepartmentById(int departmentId);

    Department getDepartmentByCode(String departmentCode);

    List<Department> getAllDepartments();
}