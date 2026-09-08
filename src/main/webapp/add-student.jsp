<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.studentmarks.model.Department" %>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

    <title>Add Student</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 40px;
        }

        h1 {
            margin-bottom: 25px;
        }

        form {
            width: 400px;
        }

        label {
            display: block;
            margin-top: 15px;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input,
        select {
            width: 100%;
            padding: 9px;
            box-sizing: border-box;
        }

        button {
            margin-top: 20px;
            padding: 10px 20px;
            cursor: pointer;
        }

        .error {
            color: red;
            margin-bottom: 15px;
        }

        .back {
            margin-top: 20px;
            display: inline-block;
        }

        body { background: #f6f8fb; color: #1f2937; }
        h1 { color: #1e3a5f; }
        form { background: #fff; padding: 24px; border: 1px solid #e2e8f0; border-radius: 8px; box-shadow: 0 8px 20px rgba(15, 23, 42, .05); }
        input, select { border: 1px solid #cbd5e1; border-radius: 5px; }
        input:focus, select:focus { outline: 2px solid #bfdbfe; border-color: #2563eb; }
        button { border: 0; border-radius: 5px; background: #2563eb; color: #fff; font-weight: 600; }
        .error { background: #fef2f2; border: 1px solid #fecaca; border-radius: 5px; padding: 10px; width: 400px; }
        .back { color: #2563eb; }

    </style>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

</head>

<body>

    <h1>Add Student</h1>

    <%

        String errorMessage =
                (String) request.getAttribute("errorMessage");

        if (errorMessage != null) {

    %>

        <div class="error">

            <%= errorMessage %>

        </div>

    <%

        }

    %>


    <form method="post"
          action="<%= request.getContextPath() %>/students">


        <label for="rollNo">

            Roll Number / USN

        </label>

        <input type="text"
               id="rollNo"
               name="rollNo"
               required>


        <label for="firstName">

            First Name

        </label>

        <input type="text"
               id="firstName"
               name="firstName"
               required>


        <label for="lastName">

            Last Name

        </label>

        <input type="text"
               id="lastName"
               name="lastName">


        <label for="initialPassword">

            Initial Password

        </label>

        <input type="password"
               id="initialPassword"
               name="initialPassword"
               required>


        <label for="departmentId">

            Department

        </label>

        <select id="departmentId"
                name="departmentId"
                required>

            <option value="">

                -- Select Department --

            </option>

            <%

                List<Department> departments =
                        (List<Department>)
                        request.getAttribute("departments");

                if (departments != null) {

                    for (Department department : departments) {

            %>

                <option value="<%= department.getDepartmentId() %>">

                    <%= department.getDepartmentCode() %>
                    -
                    <%= department.getDepartmentName() %>

                </option>

            <%

                    }

                }

            %>

        </select>


        <label for="semester">

            Current Semester

        </label>

        <select id="semester"
                name="semester"
                required>

            <option value="">

                -- Select Semester --

            </option>

            <%

                for (int semester = 1;
                     semester <= 8;
                     semester++) {

            %>

                <option value="<%= semester %>">

                    Semester <%= semester %>

                </option>

            <%

                }

            %>

        </select>


        <button type="submit">

            Add Student

        </button>

    </form>


    <a class="back"
       href="<%= request.getContextPath() %>/students">

        Back to Student List

    </a>


</body>

</html>
