<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.studentmarks.model.Student" %>
<%@ page import="com.studentmarks.model.Department" %>
<%@ page import="java.util.List" %>
<%
    Student student = (Student) request.getAttribute("student");
    Department department = (Department) request.getAttribute("department");
    List<Integer> releasedSemesters =
            (List<Integer>) request.getAttribute("releasedSemesters");
    String resultMessage = (String) request.getAttribute("resultMessage");
        int currentSemester = student.getCurrentSemester();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <title>Student Profile</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .profile { width: 520px; border: 1px solid #ccc; padding: 20px; }
        .profile p { margin: 14px 0; }
        .logout { display: inline-block; margin-top: 20px; }
        .message { color: #a33; margin: 18px 0; }
        .results { margin-top: 25px; }
        .results a { display: inline-block; margin: 5px 8px 5px 0; }
        body { background: #f6f8fb; color: #1f2937; }
        h1, h2 { color: #1e3a5f; }
        .profile, .results { background: #fff; border-color: #e2e8f0; border-radius: 8px; padding: 20px; box-shadow: 0 8px 20px rgba(15, 23, 42, .05); }
        .results { width: 520px; }
        .results a, .logout { color: #2563eb; font-weight: 600; }
        .message { background: #fef2f2; border: 1px solid #fecaca; border-radius: 5px; padding: 10px; width: 520px; }
    </style>
</head>
<body>
    <div class="page-shell">
    <h1>Student Profile</h1>
    <p class="muted">Welcome back, <%= student.getFirstName() %>. Your academic overview is below.</p>
    <div class="summary-grid">
        <div class="stat-card">
            <strong>Semester <%= student.getCurrentSemester() %></strong>
            <span>Current semester</span>
        </div>
        <div class="stat-card">
            <strong><%= releasedSemesters == null ? 0 : releasedSemesters.size() %></strong>
            <span>Published results available</span>
        </div>
    </div>
    <div class="profile">
        <p><strong>Name:</strong> <%= student.getFirstName() %> <%= student.getLastName() == null ? "" : student.getLastName() %></p>
        <p><strong>USN:</strong> <%= student.getRollNo() %></p>
        <p><strong>Department:</strong> <%= department == null ? "Unknown" : department.getDepartmentName() %></p>
        <p><strong>Current Semester:</strong> <%= student.getCurrentSemester() %></p>
    </div>
    <% if (resultMessage != null) { %>
        <p class="message"><%= resultMessage %></p>
    <% } %>
    <div class="results">
        <h2>Semester Results</h2>
        <p class="muted">Choose a semester up to your current semester.</p>
        <form class="semester-picker" method="get"
              action="<%= request.getContextPath() %>/student-result">
            <label for="semester"><strong>Semester</strong></label>
            <select id="semester" name="semester" required>
                <option value="">Select semester</option>
                <% for (int semester = 1; semester <= currentSemester; semester++) {
                       boolean released = releasedSemesters != null
                               && releasedSemesters.contains(semester);
                %>
                    <option value="<%= semester %>">
                        Semester <%= semester %>
                        (<%= released ? "Published" : "Not published" %>)
                    </option>
                <% } %>
            </select>
            <button type="submit">View Result</button>
        </form>
        <p class="muted result-note">
            Results become available after all eligible students have complete marks
            for that semester and the semester is released.
        </p>
    </div>
    <a class="logout button" href="<%= request.getContextPath() %>/student-logout">Logout</a>
    </div>
</body>
</html>
