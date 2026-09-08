<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="com.studentmarks.model.Student" %>
<%@ page import="com.studentmarks.model.Department" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <title>Edit Student</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f6f8fb; color: #1f2937; }
        h1 { color: #1e3a5f; }
        form { width: 400px; background: #fff; padding: 24px; border: 1px solid #e2e8f0; border-radius: 8px; box-shadow: 0 8px 20px rgba(15, 23, 42, .05); }
        label { font-weight: 600; }
        input, select { width: 100%; box-sizing: border-box; padding: 9px; border: 1px solid #cbd5e1; border-radius: 5px; }
        input:focus, select:focus { outline: 2px solid #bfdbfe; border-color: #2563eb; }
        button { margin-top: 8px; padding: 10px 18px; border: 0; border-radius: 5px; background: #2563eb; color: #fff; font-weight: 600; }
        a { color: #2563eb; font-weight: 600; }
    </style>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>

<h1>Edit Student</h1>

<%
    Student student =
            (Student) request.getAttribute("student");

    List<Department> departments =
            (List<Department>) request.getAttribute("departments");
%>

<form method="post"
      action="<%= request.getContextPath() %>/students">

    <input type="hidden"
           name="action"
           value="updateStudent">

    <input type="hidden"
           name="studentId"
           value="<%= student.getStudentId() %>">

    <p>
        <label>Roll Number:</label><br>
        <input type="text"
               name="rollNo"
               value="<%= student.getRollNo() %>"
               required>
    </p>

    <p>
        <label>First Name:</label><br>
        <input type="text"
               name="firstName"
               value="<%= student.getFirstName() %>"
               required>
    </p>

    <p>
        <label>Last Name:</label><br>
        <input type="text"
               name="lastName"
               value="<%= student.getLastName() == null ? "" : student.getLastName() %>">
    </p>

    <p>
        <label>Department:</label><br>

        <select name="departmentId" required>

            <%
                for (Department department : departments) {
            %>

                <option value="<%= department.getDepartmentId() %>"
                    <%= department.getDepartmentId()
                            == student.getDepartmentId()
                            ? "selected"
                            : "" %>>

                    <%= department.getDepartmentName() %>

                </option>

            <%
                }
            %>

        </select>
    </p>

    <p>
        <label>Semester:</label><br>

        <select name="semester" required>

            <%
                for (int semester = 1; semester <= 8; semester++) {
            %>

                <option value="<%= semester %>"
                    <%= semester == student.getCurrentSemester()
                            ? "selected"
                            : "" %>>

                    Semester <%= semester %>

                </option>

            <%
                }
            %>

        </select>
    </p>

    <button type="submit">
        Update Student
    </button>

</form>

<br>

<a href="<%= request.getContextPath() %>/students">
    Back to Students
</a>

</body>
</html>
