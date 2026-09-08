<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="com.studentmarks.model.Student" %>
<%@ page import="com.studentmarks.model.Department" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

    <title>Student Management</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 40px;
        }

        h1 {
            margin-bottom: 25px;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th,
        td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .actions a {
            margin-right: 8px;
        }

        .actions form {
            display: inline;
        }

        .actions button {
            margin-left: 8px;
        }

        .message {
            color: #a33;
            margin-bottom: 16px;
        }

        .add-student {
            display: inline-block;
            margin-bottom: 20px;
        }

        .dashboard-button {
            display: inline-block;
            margin-left: 15px;
            padding: 8px 15px;
            text-decoration: none;
            background-color: #333;
            color: white;
            border-radius: 5px;
        }

        body { background: #f6f8fb; color: #1f2937; }
        h1 { color: #1e3a5f; }
        table { background: #fff; border: 1px solid #e2e8f0; box-shadow: 0 8px 20px rgba(15, 23, 42, .05); }
        th { background: #eff6ff; color: #1e3a5f; }
        th, td { border-color: #e2e8f0; }
        .actions a { color: #2563eb; font-weight: 600; }
        .actions button { border: 0; border-radius: 5px; background: #2563eb; color: #fff; padding: 7px 10px; font-weight: 600; }
        .add-student, .dashboard-button { border-radius: 5px; color: #2563eb; font-weight: 600; }
        .dashboard-button { background: #1e3a5f; color: #fff; }
        .message { background: #fef2f2; border: 1px solid #fecaca; border-radius: 5px; padding: 10px; display: inline-block; }

    </style>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

</head>

<body>

<h1>Admin Student Management</h1>


<a class="add-student"
   href="<%= request.getContextPath() %>/students?action=add">

    Add Student

</a>


<a class="dashboard-button"
   href="<%= request.getContextPath() %>/admin-dashboard">

    Back to Admin Dashboard

</a>


<br>
<br>


<%

    List<Student> students =
            (List<Student>) request.getAttribute("students");

    List<Department> departments =
            (List<Department>) request.getAttribute("departments");

    Map<Integer, Boolean> marksStatus =
            (Map<Integer, Boolean>)
                    request.getAttribute("marksStatus");

    String studentManagementMessage =
            (String) request.getAttribute("studentManagementMessage");

    String searchTerm = (String) request.getAttribute("searchTerm");

%>

<% if (studentManagementMessage != null) { %>
    <p class="message"><%= studentManagementMessage %></p>
<% } %>

<form class="search-bar" method="get" action="<%= request.getContextPath() %>/students">
    <input type="search" name="search" value="<%= searchTerm == null ? "" : searchTerm %>"
           placeholder="Search by roll number or student name" aria-label="Search students">
    <button type="submit">Search</button>
    <a class="button clear" href="<%= request.getContextPath() %>/students">Clear</a>
</form>


<table>

    <tr>

        <th>Roll Number</th>

        <th>First Name</th>

        <th>Last Name</th>

        <th>Department</th>

        <th>Semester</th>

        <th>Actions</th>

    </tr>


<%

    if (students != null && !students.isEmpty()) {

        for (Student student : students) {

            boolean hasMarks = false;

            if (marksStatus != null
                    && marksStatus.containsKey(
                            student.getStudentId())) {

                hasMarks =
                        marksStatus.get(
                                student.getStudentId());

            }

%>


    <tr>

        <!-- Roll Number -->
        <td>

            <%= student.getRollNo() %>

        </td>


        <!-- First Name -->
        <td>

            <%= student.getFirstName() %>

        </td>


        <!-- Last Name -->
        <td>

            <%= student.getLastName() == null
                    ? ""
                    : student.getLastName() %>

        </td>


        <!-- Department -->
        <td>

<%

            String departmentCode = "";

            if (departments != null) {

                for (Department department : departments) {

                    if (department.getDepartmentId()
                            == student.getDepartmentId()) {

                        departmentCode =
                                department.getDepartmentCode();

                        break;

                    }

                }

            }

%>

            <%= departmentCode %>

        </td>


        <!-- Semester -->
        <td>

            <%= student.getCurrentSemester() %>

        </td>


        <!-- Actions -->
        <td class="actions">

<%

            if (hasMarks) {

%>

                <a href="<%= request.getContextPath() %>/students?action=marks&studentId=<%= student.getStudentId() %>">

                    Manage Marks

                </a>

<%

            } else {

%>

                <a href="<%= request.getContextPath() %>/students?action=marks&studentId=<%= student.getStudentId() %>">

                    Manage Marks

                </a>

<%

            }

            if (student.getCurrentSemester() < 8) {

%>

                <form method="post"
                      action="<%= request.getContextPath() %>/students">
                    <input type="hidden" name="action" value="promote">
                    <input type="hidden" name="studentId" value="<%= student.getStudentId() %>">
                    <button type="submit">Promote to Semester <%= student.getCurrentSemester() + 1 %></button>
                </form>

<%

            } else {

%>

                <span>Final semester</span>

<%

            }

%>

                        <form method="post"
                                    action="<%= request.getContextPath() %>/students"
                                    onsubmit="return confirm('Delete this student and all recorded marks?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="studentId" value="<%= student.getStudentId() %>">
                                <button type="submit">Delete</button>
                        </form>

<%

%>

        </td>

    </tr>


<%

        }

    } else {

%>


    <tr>

        <td colspan="6">

            <div class="empty-state">
                <strong>No students found.</strong><br>
                Try a different roll number or name.
            </div>

        </td>

    </tr>


<%

    }

%>


</table>


</body>

</html>
