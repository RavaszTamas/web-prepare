<%--
  Created by IntelliJ IDEA.
  User: tamas
  Date: 19.06.2020
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% if (request.getSession().getAttribute("user") != null) response.sendRedirect("index.jsp"); %>
<html>
<head>
    <title>Title</title>
    <link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-3.4.1.min.js"></script>

</head>
<body>

<header class="container">
    <nav class="navbar container d-flex justify-content-start">
        <a class="btn btn-primary" href="register.jsp">Register</a>
    </nav>
</header>
<main class="container">
    <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
        <div class="form-group">
            <label for="username">Username: </label>
            <input class="form-control" type="text" id="username" name="username" placeholder="Enter username">
        </div>
        <div class="form-group">
            <label for="password">Password: </label>
            <input class="form-control" type="password" id="password" name="password" placeholder="Enter password">
        </div>
        <button type="submit" class="btn btn-primary">Login user</button>
        <%
            String error = request.getParameter("error");
            if (error != null) {
        %>
        <div>
            <span class="badge bg-danger"><% out.println(error); %></span>
        </div>
        <%
            }
        %>
    </form>
</main>


</body>
</html>
