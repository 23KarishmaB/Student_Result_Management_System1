<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <title>Student Login</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background: #f5f5f5; }
        .login-container { width: 350px; margin: 100px auto; padding: 30px; background: white; border: 1px solid #ddd; border-radius: 8px; }
        h1 { text-align: center; margin-bottom: 25px; }
        label { display: block; margin-top: 15px; margin-bottom: 5px; }
        input { width: 100%; box-sizing: border-box; padding: 10px; }
        button { width: 100%; padding: 11px; margin-top: 25px; cursor: pointer; }
        .error { color: red; text-align: center; margin-bottom: 15px; }
        .back { display: block; text-align: center; margin-top: 20px; }
        body { background: #f6f8fb; color: #1f2937; }
        .login-container { margin: 90px auto; border-color: #e2e8f0; box-shadow: 0 12px 28px rgba(15, 23, 42, .08); }
        h1 { color: #1e3a5f; }
        input { border: 1px solid #cbd5e1; border-radius: 5px; }
        input:focus { outline: 2px solid #bfdbfe; border-color: #2563eb; }
        button { border: 0; border-radius: 5px; background: #2563eb; color: #fff; font-weight: 600; }
        .error { background: #fef2f2; border: 1px solid #fecaca; border-radius: 5px; padding: 9px; }
        .back { color: #2563eb; }
    </style>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<div class="login-container">
    <h1>Student Login</h1>
    <p class="intro">Access your profile and published semester results.</p>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
        <div class="error"><%= errorMessage %></div>
    <% } %>
    <form method="post" action="<%= request.getContextPath() %>/student-login">
        <label for="usn">USN</label>
        <input id="usn" name="usn" type="text" required>
        <label for="password">Password</label>
        <input id="password" name="password" type="password" required>
        <button type="submit">Login</button>
    </form>
    <a class="back" href="<%= request.getContextPath() %>/">Back to Home</a>
</div>
</body>
</html>
