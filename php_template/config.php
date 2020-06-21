<?php
define('DB_SERVER', 'localhost');
define('DB_USERNAME', 'ubbstudent');
define('DB_PASSWORD', 'forclasspurposes');
define('DB_DATABASE', 'php_db');
$db_connection = new mysqli(DB_SERVER,DB_USERNAME,DB_PASSWORD,DB_DATABASE);
?>