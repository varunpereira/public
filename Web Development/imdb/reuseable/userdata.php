<?php
session_start();

function check_login($con)
{
    if (isset($_SESSION['user_id'])) {
        $id = $_SESSION['user_id'];
        $query = "select * from users where user_id = '$id' limit 1";
        $result = mysqli_query($con, $query);
        if ($result && mysqli_num_rows($result) > 0) {
            $user_data = mysqli_fetch_assoc($result);
            return $user_data;
        }
    }
}

$server = "localhost";
$username = "root";
$password = "";
$dbname = "imdb";
$con = mysqli_connect($server, $username, $password, $dbname);

$user_data = check_login($con);

?>