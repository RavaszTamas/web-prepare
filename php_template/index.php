<?php

include('session.php');
include('config.php');
?>

<!DOCTYPE html>
<html>

<head>
    <title>Welcome page</title>
    <script src="js/jquery-3.4.1.min.js"></script>
    <script src="js/template.js"></script>
    <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet">

</head>
<body>
    <header>
        <div class="d-flex justify-content-end container">
            <h2><a href = "logout.php">Sign Out</a></h2>
        </div>
    </header>
    <main class="container">
        <h1 class="h1">Welcome
            <?php
            echo $_SESSION['login_user'];
            ?>

        </h1>
        <table class="table" id="asset-table">
            <tr><th>Name</th><th>Description</th><th>Value</th></tr>
            <?php
                $sql_query = "SELECT * FROM assets where user_id = ?";
                $stmt = $db_connection->prepare($sql_query);
                $stmt->bind_param("i",$_SESSION['login_user_id']);
                $stmt->execute();
                $result = $stmt->get_result();
                while ($row = $result->fetch_assoc()) {
                    $str_to_display = "<tr>";
                    if($row['value'] > 10){
                        $str_to_display = "<tr class='bg-danger'>";
                    }
                    $str_to_display .=  '<td>'.$row['name'].'</td> <td>'.$row['description'].'</td><td>'.$row['value'].'</td></tr>';

                    echo $str_to_display;
                }

            ?>
        </table>
        <div class="container">
            <p id="result">

            </p>
            <h2>Add new assets</h2>
            <button onclick="addNewAssetInput()">Add row</button>
            <button onclick="addEntities(<?php echo $_SESSION['login_user_id'] ?>)" class="btn btn-primary">Submit</button>
            <form id="add-asset-form" method="post" >
                <div class="form-group" style="border: 5px solid black">
                    <div class="form-group">
                        <label for="name-0">Username: </label>
                        <input class="form-control" placeholder="Enter name" id="name-0" type="text" name="name-0">
                    </div>
                    <div class="form-group">
                        <label for="description-0">Description: </label>
                        <input class="form-control" placeholder="Enter description" id="description-0" type="text" name="description-0">
                    </div>
                    <div class="form-group">
                        <label for="value-0">Value: </label>
                        <input class="form-control" placeholder="Enter value" id="value-0" type="number" name="value-0">
                    </div>
                </div>
            </form>
        </div>
    </main>
</body>

</html>
