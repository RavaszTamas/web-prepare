<?php
include("config.php");
session_start();
if(isset($_SESSION['login_user'])){
    header("location: index.php");
    die();
}

$error="";
if($_SERVER["REQUEST_METHOD"] == "POST") {
    // username and password sent from form
    $error = "";
    $myusername = filter_var($_POST['username'],FILTER_SANITIZE_STRING);
    $mypassword = filter_var($_POST['password'],FILTER_SANITIZE_STRING);
    $mypassword = md5($mypassword);
    $sql = "SELECT id FROM user WHERE username = ? and password = ?";

    $stmt = $db_connection->prepare($sql);
    $stmt->bind_param("ss",$myusername,$mypassword);
    $stmt->execute();
    $result = $stmt->get_result();


    // If result matched $myusername and $mypassword, table row must be 1 row

    if($result->num_rows == 1) {
        $_SESSION['login_user'] = $myusername;
        $_SESSION['login_user_id'] = $result->fetch_assoc()['id'];
        header("location: index.php");
    }else {
        $error = "Your Login Name or Password is invalid";
    }
}

?>

<!DOCTYPE html>
<html>
    <head>
        <title> Login page</title>
        <script src="js/jquery-3.4.1.min.js"></script>
        <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <header>
            <nav class="container navbar d-flex justify-content-end">
                <a href="register.php" class="btn btn-primary">Register</a>
            </nav>
        </header>
        <main class="container">
            <div>
                <form action="" method="post">
                    <div class="form-group">
                        <label for="username">Username: </label>
                        <input class="form-control" placeholder="Enter username" id="username" type="text" name="username">
                    </div>
                    <div class="form-group">
                        <label for="password">Password: </label>
                        <input class="form-control" placeholder="Enter password" id="password" type="password" name="password">
                    </div>
                    <button type="submit" class="btn btn-primary">Login</button>
                    <div>
                        <?php echo $error?>
                    </div>
                </form>
            </div>
        </main>
    </body>
</html>
