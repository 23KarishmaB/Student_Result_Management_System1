<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

    <title>Admin Dashboard</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 40px;
        }

        .menu {
            margin-top: 30px;
        }

        .menu a {
            display: inline-block;
            margin: 10px;
            padding: 15px 25px;
            border: 1px solid #ccc;
            text-decoration: none;
            color: black;
        }

        body { background: #f6f8fb; color: #1f2937; }
        h1 { color: #1e3a5f; }
        .menu { background: #fff; border: 1px solid #e2e8f0; border-radius: 8px; padding: 16px; width: fit-content; box-shadow: 0 8px 20px rgba(15, 23, 42, .05); }
        .menu a { border-color: #bfdbfe; border-radius: 5px; background: #eff6ff; color: #1e3a5f; font-weight: 600; }

    </style>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

</head>

<body>

<div class="page-shell">
<h1>Admin Dashboard</h1>

<p>
    Welcome, Admin.
</p>

<div class="summary-grid">
    <div class="stat-card">
        <strong><%= request.getAttribute("totalStudents") %></strong>
        <span>Total students</span>
    </div>
    <div class="stat-card">
        <strong><%= request.getAttribute("totalDepartments") %></strong>
        <span>Departments</span>
    </div>
</div>

<div class="menu">

    <a href="<%= request.getContextPath() %>/students">
        Manage Students
    </a>

    <a href="<%= request.getContextPath() %>/result">
        View Results
    </a>

    <a href="<%= request.getContextPath() %>/admin-logout">
        Logout
    </a>

</div>

</div>

</body>

</html>
