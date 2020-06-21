<?php
include("config.php");
session_start();
if(isset($_SESSION['login_user'])){
    header("location: index.php");
    die();
}

$error = "";
if($_SERVER["REQUEST_METHOD"] == "POST") {
    // username and password sent from form

    $myusername = filter_var($_POST['username'],FILTER_SANITIZE_STRING);
    $mypassword = filter_var($_POST['password'],FILTER_SANITIZE_STRING);
    $mypassword_repeat = filter_var($_POST['password-repeat'],FILTER_SANITIZE_STRING);
    $mypassword_hashed = md5($mypassword);

    if($mypassword_repeat != $mypassword){
        $error = "Passwords must be the same";
    }
    else {
        $sql = "SELECT id FROM user WHERE username = ?";

        $stmt = $db_connection->prepare($sql);
        $stmt->bind_param("s", $myusername);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows == 1) {
            $error = "Username already exists";

        } else {
            $sql = "INSERT INTO user (username,password) VALUES (?,?)";
            $stmt = $db_connection->prepare($sql);
            $stmt->bind_param("ss", $myusername, $mypassword_hashed);
            $result = $stmt->execute();


            // If result matched $myusername and $mypassword, table row must be 1 row

            if ($result) {
                $_SESSION['login_user'] = $myusername;

                $sql = "SELECT user_id FROM user WHERE username = ? and password = ?";
                $stmt = $db_connection->prepare($sql);
                $stmt->bind_param("ss", $myusername, $mypassword_hashed);
                $stmt->execute();
                $result = $stmt->get_result();

                $_SESSION['login_user_id'] = $result->fetch_assoc()['user_id'];

                header("location: index.php");
            } else {
                $error = "Your Login Name or Password is invalid";
            }
        }
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
        <a href="login.php" class="btn btn-primary">Login</a>
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
            <div class="form-group">
                <label for="password-repeat">Password: </label>
                <input class="form-control" placeholder="Repeat password" id="password-repeat" type="password" name="password-repeat">
            </div>
            <button type="submit" class="btn btn-primary">Register</button>
            <div>
                <?php echo $error?>
            </div>

        </form>
    </div>
</main>
</body>
</html>
