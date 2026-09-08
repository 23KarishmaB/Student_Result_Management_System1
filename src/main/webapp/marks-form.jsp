<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="com.studentmarks.model.Student" %>
<%@ page import="com.studentmarks.model.Subject" %>
<%@ page import="com.studentmarks.model.Mark" %>
<%@ page import="java.util.List" %>

<%
    Student student = (Student) request.getAttribute("student");
    List<Subject> subjects =
            (List<Subject>) request.getAttribute("subjects");

    List<Mark> marks =
            (List<Mark>) request.getAttribute("marks");

    String errorMessage =
            (String) request.getAttribute("errorMessage");

    Integer selectedSemester =
            (Integer) request.getAttribute("selectedSemester");

    if (selectedSemester == null) {
        selectedSemester = student.getCurrentSemester();
    }

    boolean hasMarks =
            marks != null && !marks.isEmpty();
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

    <title>
        <%= hasMarks ? "Edit Marks" : "Add Marks" %>
    </title>

    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 40px;
        }

        h1 {
            margin-bottom: 25px;
        }

        .student-info {
            margin-bottom: 30px;
        }

        .student-info p {
            margin: 8px 0;
        }

        table {
            border-collapse: collapse;
            width: 700px;
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

        input[type="number"] {
            width: 100px;
            padding: 6px;
        }

        .error {
            color: red;
            margin-bottom: 20px;
        }

        .button {
            margin-top: 20px;
            padding: 10px 20px;
        }

        body { background: #f6f8fb; color: #1f2937; }
        h1 { color: #1e3a5f; }
        .student-info { background: #fff; padding: 16px 20px; border: 1px solid #e2e8f0; border-radius: 8px; width: 700px; }
        form { background: #fff; padding: 18px; border: 1px solid #e2e8f0; border-radius: 8px; width: 700px; }
        table { width: 100%; }
        th { background: #eff6ff; color: #1e3a5f; }
        th, td { border-color: #e2e8f0; }
        input[type="number"], select { border: 1px solid #cbd5e1; border-radius: 5px; }
        input:focus, select:focus { outline: 2px solid #bfdbfe; border-color: #2563eb; }
        .button { border: 0; border-radius: 5px; background: #2563eb; color: #fff; font-weight: 600; }
        .error { background: #fef2f2; border: 1px solid #fecaca; border-radius: 5px; padding: 10px; width: 700px; }
        a { color: #2563eb; }

    </style>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

</head>

<body>

<h1>
    <%= hasMarks ? "Edit Marks" : "Add Marks" %>
</h1>

<%
    if (errorMessage != null) {
%>

    <div class="error">
        <%= errorMessage %>
    </div>

<%
    }
%>

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
        <strong>Department ID:</strong>
        <%= student.getDepartmentId() %>
    </p>

    <p>
        <strong>Current Semester:</strong>
        <%= student.getCurrentSemester() %>
    </p>

</div>

<form method="get"
      action="<%= request.getContextPath() %>/students">

    <input type="hidden" name="action" value="marks">
    <input type="hidden" name="studentId" value="<%= student.getStudentId() %>">

    <label for="semester"><strong>Manage Semester:</strong></label>
    <select id="semester" name="semester" onchange="this.form.submit()">
        <% for (int i = 1; i <= student.getCurrentSemester(); i++) { %>
            <option value="<%= i %>" <%= i == selectedSemester ? "selected" : "" %>>
                Semester <%= i %>
            </option>
        <% } %>
    </select>
    <noscript><button type="submit">Load Semester</button></noscript>
</form>

<br>

<form method="post"
      action="<%= request.getContextPath() %>/students">

    <input type="hidden"
           name="action"
           value="saveMarks">

    <input type="hidden"
           name="studentId"
           value="<%= student.getStudentId() %>">

    <input type="hidden"
           name="semester"
           value="<%= selectedSemester %>">

    <table>

        <tr>
            <th>Subject Code</th>
            <th>Subject Name</th>
            <th>Marks</th>
        </tr>

<%
    for (Subject subject : subjects) {

        double existingScore = 0;
        boolean markFound = false;

        if (marks != null) {

            for (Mark mark : marks) {

                if (subject.getSubjectCode()
                        .equals(mark.getSubjectCode())) {

                    existingScore = mark.getScore();
                    markFound = true;
                    break;
                }
            }
        }
%>

        <tr>

            <td>
                <%= subject.getSubjectCode() %>
            </td>

            <td>
                <%= subject.getSubjectName() %>
            </td>

            <td>

                <input type="number"
                       name="score_<%= subject.getSubjectCode() %>"
                       min="0"
                       max="100"
                       step="0.01"
                       value="<%= markFound ? existingScore : "" %>"
                       required>

            </td>

        </tr>

<%
    }
%>

    </table>

    <br>

    <button type="submit" class="button">

        <%= hasMarks ? "Update Marks" : "Save Marks" %>

    </button>

</form>

<br>

<a href="<%= request.getContextPath() %>/students">
    Back to Student Management
</a>

</body>

</html>
