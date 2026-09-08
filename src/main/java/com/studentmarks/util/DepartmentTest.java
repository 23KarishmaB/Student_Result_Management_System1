package com.studentmarks.util;

import com.studentmarks.dao.impl.DepartmentDAOImpl;
import com.studentmarks.model.Department;

import java.util.List;

public class DepartmentTest {

    public static void main(String[] args) {

        DepartmentDAOImpl departmentDAO =
                new DepartmentDAOImpl();

        List<Department> departments =
                departmentDAO.getAllDepartments();

        System.out.println(
                "Number of departments: "
                + departments.size());

        for (Department department : departments) {

            System.out.println(
                    department.getDepartmentId()
                    + " | "
                    + department.getDepartmentCode()
                    + " | "
                    + department.getDepartmentName());
        }
    }
}