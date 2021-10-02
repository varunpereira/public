<?php include 'reuseable/userdata.php'; ?>


<?php
// define variables and set to empty values
$score = $message = "";

$valid = true;
if ($_SERVER["REQUEST_METHOD"] == "POST") {

    if (empty($_POST["message"])) {
        $message = "";
        $valid = false;
    } else {
        $message = test_input($_POST["message"]);
        $valid = true;
    }

    if (empty($_POST["score"])) {
        $score = "";
        $valid = false;
    } else {
        $score = test_input($_POST["score"]);
        $valid = true;
    }

    // CHANGE REVIEW_SHOW TABLE, START
    if ($message != "" && $score != "") {
        //$user_name = ;
        $user_id = $user_data['user_id'];
        $show_title = $_SESSION['firstMessage'];
        $query = "insert into reviews (user_id,show_title,review_message,review_score) 
            values ('$user_id', '$show_title','$message','$score')";
        mysqli_query($con, $query);
        header("location: 1-home.php");
        exit;
    }
    // CHANGE REVIEW_SHOW TABLE, END
}

function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}
?>



<!DOCTYPE html>
<html>

<!-- HEAD -->
<?php include 'reuseable/head.php'; ?>

<body class="container" style="background-color:white;">

    <!-- NAVBAR -->
    <?php include 'reuseable/navbar.php'; ?>

    <!-- put the value attribute, as blank sends -->
    <h2>Submit Your Review</h2>
    <form method="post">
        Message: <br>
        <textarea name="message" rows="5" cols="40"></textarea>
        <br><br>
        Score (out of 10): <input type="text" name="score">
        <br><br>
        <input type="submit" name="submit" value="Submit">
        <?php if ($valid == false) : ?>
            <h1>Both fields required </h1>
        <?php endif; ?>
    </form>

    <!-- FOOTER -->
    <?php include 'reuseable/footer.php'; ?>

</body>

</html>