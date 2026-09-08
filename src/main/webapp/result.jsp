<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="com.studentmarks.model.Result" %>
<%@ page import="com.studentmarks.model.Student" %>
<%@ page import="com.studentmarks.model.Department" %>
<%@ page import="com.studentmarks.model.Mark" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

    <title>Student Result</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 40px;
        }

        h1 {
            margin-bottom: 25px;
        }

        .student-info {
            margin-bottom: 25px;
            padding: 15px;
            border: 1px solid #ccc;
            width: 600px;
        }

        .student-info p {
            margin: 8px 0;
        }

        table {
            border-collapse: collapse;
            width: 800px;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 10px;
        }

        th {
            background-color: #f2f2f2;
        }

        .summary {
            margin-top: 25px;
            width: 400px;
        }

        .summary p {
            font-size: 18px;
        }

        .back {
            display: inline-block;
            margin-top: 25px;
        }

        body { background: #f6f8fb; color: #1f2937; }
        h1 { color: #1e3a5f; }
        .student-info, .summary { background: #fff; border-color: #e2e8f0; border-radius: 8px; box-shadow: 0 8px 20px rgba(15, 23, 42, .05); }
        table { background: #fff; box-shadow: 0 8px 20px rgba(15, 23, 42, .05); }
        th { background: #eff6ff; color: #1e3a5f; }
        th, td { border-color: #e2e8f0; }
        .back, a { color: #2563eb; font-weight: 600; }

    </style>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

</head>

<body>

    <%
        Result result =
                (Result) request.getAttribute("result");

        Integer studentId =
                (Integer) session.getAttribute("studentId");

        if (result == null) {
    %>

        <h1>Result Not Available</h1>

        <p>
            The requested result could not be found.
        </p>

        <a href="<%= request.getContextPath() %>/result">
            Search Again
        </a>

    <%
            return;
        }

        Student student =
                result.getStudent();

        Department department =
                result.getDepartment();

    %>

    <div class="result-sheet">
    <h1>Student Result</h1>
    <p class="muted">Student Marks Management System</p>


    <!-- =========================================
         STUDENT INFORMATION
         ========================================= -->

    <div class="student-info">

        <p>
            <strong>Roll Number:</strong>
            <%= student.getRollNo() %>
        </p>

        <p>
            <strong>Student Name:</strong>
            <%= student.getFirstName() %>
            <%= student.getLastName() == null
                    ? ""
                    : student.getLastName() %>
        </p>

        <p>
            <strong>Department:</strong>
            <%= department.getDepartmentName() %>
        </p>

        <p>
            <strong>Semester:</strong>
            <%= result.getSemester() %>
        </p>

    </div>


    <!-- =========================================
         SUBJECT-WISE MARKS
         ========================================= -->

    <table>

        <tr>
            <th>Subject Code</th>
            <th>Course / Subject</th>
            <th>Marks</th>
            <th>Grade</th>
        </tr>


        <%
            for (Mark mark : result.getMarks()) {

        %>

        <tr>

            <td>
                <%= mark.getSubjectCode() %>
            </td>

            <td>
                <%= result.getSubjectNames().getOrDefault(
                    mark.getSubjectCode(), "Unknown Subject") %>
            </td>

            <td>
                <%= mark.getScore() %>
            </td>

            <td>
                <%= mark.getGrade() %>
            </td>

        </tr>

        <%
            }
        %>

    </table>


    <!-- =========================================
         RESULT SUMMARY
         ========================================= -->

    <div class="summary">

        <p>
            <strong>Total Marks:</strong>
            <%= result.getTotalMarks() %> / <%= result.getMarks().size() * 100 %>
        </p>

        <p>
            <strong>Percentage:</strong>
            <%= result.getPercentage() %>%
        </p>

        <p>
            <strong>Overall Grade:</strong>
            <%= result.getOverallGrade() %>
        </p>

    </div>

    <div class="print-actions">
        <button type="button" onclick="window.print()">Print Result</button>
        <% if (studentId != null) { %>
            <a class="button" href="<%= request.getContextPath() %>/student-logout">Logout</a>
        <% } %>
    </div>


    <a class="back"
       href="<%= studentId != null
               ? request.getContextPath() + "/student-profile"
               : request.getContextPath() + "/result" %>">

        Back

    </a>
    <%
    Boolean adminLoggedIn = (Boolean) session.getAttribute("adminLoggedIn");

    if (Boolean.TRUE.equals(adminLoggedIn)) {
%>
<br/>
    <a href="<%= request.getContextPath() %>/students">
        Back to Student Management
    </a>

<%
    }
    if (studentId != null) {
%>
<br/>
    <a href="<%= request.getContextPath() %>/student-profile">
        Back to Profile
    </a>
<%
    }
%>

    </div>

</body>

</html>
