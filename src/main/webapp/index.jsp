<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

    <title>Student Marks Management System</title>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            color: #222;
        }

        .header {
            background: #1f2937;
            color: white;
            padding: 22px;
            text-align: center;
        }

        .header h1 {
            margin: 0;
            font-size: 30px;
        }

        .header p {
            margin: 8px 0 0;
            color: #d1d5db;
        }

        .container {
            width: 850px;
            max-width: 90%;
            margin: 70px auto;
            text-align: center;
        }

        .container h2 {
            margin-bottom: 10px;
        }

        .subtitle {
            color: #666;
            margin-bottom: 40px;
        }

        .options {
            display: flex;
            justify-content: center;
            gap: 30px;
        }

        .card {
            width: 330px;
            padding: 35px 30px;
            background: white;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
        }

        .card h2 {
            margin-top: 0;
            margin-bottom: 15px;
        }

        .card p {
            color: #666;
            min-height: 45px;
            line-height: 1.5;
        }

        .button {
            display: inline-block;
            padding: 12px 28px;
            margin-top: 15px;
            background: #1f2937;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
        }

        .button:hover {
            background: #374151;
        }

        .footer {
            margin-top: 60px;
            color: #888;
            font-size: 14px;
        }

        body { background: #f6f8fb; color: #1f2937; }
        .header { background: #1e3a5f; padding: 26px; }
        .container { margin: 60px auto; }
        .subtitle { color: #64748b; }
        .card { border-color: #e2e8f0; box-shadow: 0 10px 24px rgba(15, 23, 42, .07); }
        .card h2 { color: #1e3a5f; }
        .button { background: #2563eb; border-radius: 5px; }
        .button:hover { background: #1d4ed8; }

    </style>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

</head>

<body>

    <div class="header">

        <h1><span class="site-mark">Student Marks Management</span></h1>

        <p>Manage and view student academic results</p>

    </div>


    <div class="container">

        <h2>Academic results, clearly organised.</h2>

        <p class="subtitle">
            Please select your role to continue.
        </p>


        <div class="options">

            <!-- ADMIN -->

            <div class="card">

                <h2>Admin</h2>

                <p>
                    Manage students, marks and academic results.
                </p>

                <a class="button"
                   href="<%= request.getContextPath() %>/admin-login">
                    Admin Login
                </a>

            </div>


            <!-- STUDENT -->

            <div class="card">

                <h2>Student</h2>

                <p>
                    Sign in to view your student profile.
                </p>

                <a class="button"
                   href="<%= request.getContextPath() %>/student-login">
                    Student Login
                </a>

            </div>

        </div>


        <div class="footer">

            Student Result Management System

        </div>

    </div>

</body>

</html>
