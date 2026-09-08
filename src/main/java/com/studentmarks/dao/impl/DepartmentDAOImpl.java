package com.studentmarks.dao.impl;

import com.studentmarks.dao.DepartmentDAO;
import com.studentmarks.model.Department;
import com.studentmarks.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO {

    @Override
    public Department getDepartmentById(int departmentId) {

        String sql = """
                SELECT department_id, department_code, department_name
                FROM DEPARTMENT
                WHERE department_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, departmentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Department department = new Department();

                    department.setDepartmentId(
                            resultSet.getInt("department_id"));

                    department.setDepartmentCode(
                            resultSet.getString("department_code"));

                    department.setDepartmentName(
                            resultSet.getString("department_name"));

                    return department;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Department getDepartmentByCode(String departmentCode) {

        String sql = """
                SELECT department_id, department_code, department_name
                FROM DEPARTMENT
                WHERE department_code = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, departmentCode);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Department department = new Department();

                    department.setDepartmentId(
                            resultSet.getInt("department_id"));

                    department.setDepartmentCode(
                            resultSet.getString("department_code"));

                    department.setDepartmentName(
                            resultSet.getString("department_name"));

                    return department;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Department> getAllDepartments() {

        List<Department> departments = new ArrayList<>();

        String sql = """
                SELECT department_id, department_code, department_name
                FROM DEPARTMENT
                ORDER BY department_name
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Department department = new Department();

                department.setDepartmentId(
                        resultSet.getInt("department_id"));

                department.setDepartmentCode(
                        resultSet.getString("department_code"));

                department.setDepartmentName(
                        resultSet.getString("department_name"));

                departments.add(department);
            }

        } catch (Exception e) {

            System.out.println(
                    "ERROR - DepartmentDAOImpl.getAllDepartments()");

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        return departments;
    }
}