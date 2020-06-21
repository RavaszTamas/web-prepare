<?php

if($_SERVER["REQUEST_METHOD"] == "POST") {
    include('config.php');
    $sql_insert = "INSERT INTO `assets`(`user_id`, `name`, `description`, `value`) VALUES ";
    foreach ($_POST["entries"] as $index=>$entry){
        $sql_insert .= '('.$_POST['user_id'].',\''.$entry['name'].'\',\''.$entry['description'].'\','.$entry['value'].'),';
    }
    $sql_insert = substr_replace($sql_insert,"",-1);
    echo $sql_insert;
    if ($db_connection->query($sql_insert) === TRUE) {
        echo "New record created successfully";
    } else {
        echo "Error: " . $sql_insert . "<br>" . $db_connection->error;
    }
    $db_connection->close();


}
?>