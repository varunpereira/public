<?php include 'reuseable/userdata.php'; ?>

<?php
$password_updated = true;
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $user_name = $user_data['user_name'];

    //update password column, in row contains logged in username
    $new_password = $_POST["new_password"];
    $sql = "UPDATE users SET password='$new_password' WHERE user_name='$user_name' ";
    if (!empty($new_password)) {
        $result = mysqli_query($con, $sql);
    } else {
        $password_updated = false;
    }

    //update other columns (info from signup page, add more columns)

    if (!empty($new_password)) {
        header("location: 1-home.php");
        exit;
    }
}
?>


<!DOCTYPE html>
<html>

<body class="container" style="background-color:white;">

    <!-- HEAD -->
    <?php include 'reuseable/head.php'; ?>

    <!-- NAVBAR -->
    <?php include 'reuseable/navbar.php'; ?>

    <h2>Update Your Details</h2>
    <form method="post">
        NEW password: <input type="text" name="new_password">
        <br><br>
        <input type="submit" name="submit" value="Submit">
        <?php if ($password_updated == false) : ?>
            <div>Password can not be empty, try again.</div>
        <?php endif; ?>
        <br><br>
        <a href="1-home.php">Back Home</a>
    </form>

    <!-- FOOTER -->
    <?php include 'reuseable/footer.php'; ?>

</body>

</html>