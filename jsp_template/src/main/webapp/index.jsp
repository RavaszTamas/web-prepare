<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: tamas
  Date: 19.06.2020
  Time: 22:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getSession().getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    User user = (User) session.getAttribute("user");
%>
<html>
<head>
    <title>Title</title>
    <link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-3.4.1.min.js"></script>
    <script>
        function isEmpty(stringtocheck) {
            return (stringtocheck.length === 0 || !stringtocheck.trim());
        };

        function populateTable(assets) {
            console.log("populate table");
            console.log(assets)
            $('#asset-table').html('');
            $("#asset-table").append(
                "<tr><th>Name</th><th>Description</th><th>Value</th></tr>"
            );

            for (let asset of assets) {
                let row = "<tr>";
                if (asset["value"] > 10) {
                    row = "<tr class='bg-danger'>";
                }
                row += "<td>" + asset["name"] + "</td>" +
                    "<td>" + asset["description"] + "</td>" +
                    "<td>" + asset["value"] + "</td>"
                $("#asset-table").append(row);
            }
        }

        $(document).ready(function () {
            console.log("getting assets")
            let newAssets = [];
            $.getJSON(
                "/AssetController",
                {action: "getAssets", userId: <% out.print(user.getId().toString()); %>},
                populateTable
            );
            $("#send-button").click(
                function () {
                    console.log("entries:",newAssets);
                    $.post(
                        "/AssetController",
                        {action: "addAssets", userId: <% out.print(user.getId().toString()); %>, entries: JSON.stringify(newAssets)},
                        function () {
                            location.reload();
                        }
                    );
                    newAssets = [];
                    $("#new-assets").val("");

                }
            )
            $("#push-button").click(function () {
                    if (
                        isEmpty($("#name").val()) ||
                        isEmpty($("#description").val()) ||
                        isEmpty($("#value").val())
                    ) {
                        return;
                    }
                    newAssets.push(
                        {
                            userId: <% out.print(user.getId().toString()); %>,
                            name: $("#name").val(),
                            description: $("#description").val(),
                            value: parseInt($("#value").val())
                        }
                    )

                    $("#name").val("");
                    $("#description").val("");
                    $("#value").val("");

                    let content = "<h4>Assets to add:<br>";
                    for (let tempAsset of newAssets) {
                        content += "<b>" + tempAsset["name"] + ",</b> "
                    }
                    content += "</h4>";
                    $("#new-assets").html(content);

                }
            );
        });
    </script>

</head>
<body>
<header class="container">
    <nav class="nav d-flex justify-content-start container">
        <form action="${pageContext.request.contextPath}/LogoutServlet" method="post">
            <button type="submit" class="btn btn-danger">Log out</button>
        </form>
    </nav>
</header>
<main class="container">
    <h1>
        Welcome <% out.print(user.getUsername()); %>
    </h1>
    <form>
        <label for="Name">Name:</label>
        <input type="text" id="name" name="name"><br><br>
        <label for="Description">Description:</label>
        <input type="text" id="description" name="description"><br><br>
        <label for="Value">Value:</label>
        <input type="number" id="value" name="value"><br><br>
        <button type="button" id="push-button">Push</button>
        <button type="button" id="send-button">Send</button>
    </form>
    <section class="container" id="new-assets">

    </section>
    <section class="container" id="all-assets">
        <table class="table" id="asset-table">

        </table>
    </section>
</main>

</body>
</html>
