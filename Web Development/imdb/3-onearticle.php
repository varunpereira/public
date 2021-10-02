<?php include 'reuseable/userdata.php'; ?>


<!DOCTYPE html>
<html lang="en">

<!-- HEAD -->
<?php include 'reuseable/head.php'; ?>

<body class="container" style="background-color:white;">

    <!-- NAVBAR -->
    <?php include 'reuseable/navbar.php'; ?>

    <!-- ARTICLE CHOSEN FROM ARTICLE LIST -->
    <h1>Article Page </h1>
    <div class="article-container">
        <?php
        $title = mysqli_real_escape_string($con, $_GET['title']);
        $date = mysqli_real_escape_string($con, $_GET['date']);

        // CHANGE ARTICLE TABLE, START
        $sql = "SELECT * FROM shows WHERE show_title = '$title' AND show_releasedate= '$date' ";
        $result = mysqli_query($con, $sql);
        $queryResults = mysqli_num_rows($result);
        if ($queryResults > 0) {
            while ($row = mysqli_fetch_assoc($result)) {
                echo "<div class='article-box'>
                <h3>" . $row['show_title'] . "</h3>
                 <p>Release Date: " . $row['show_releasedate'] . "</p>
                <p>Type: " . $row['show_type'] . "</p>
                <p>Length: " . $row['show_length'] . "</p>
                <p>Genre: " . $row['show_genre'] . "</p>
                <p>Cast: " . $row['show_cast'] . "</p>
                <p>Crew: " . $row['show_crew'] . "</p>
                <p>Characters: " . $row['show_characters'] . "</p>
                </div>";
                $_SESSION['firstMessage'] = $row['show_title'];
            }
        }
        // CHANGE ARTICLE TABLE, END

        ?>
    </div>

    <!-- MAKE AN ARTICLE REVIEW-->
    <?php if (isset($_SESSION['user_id'])) : ?>
        <div>
            <a href="7-reviewarticle.php">MAKE a Review</a>
        </div>
    <?php else : ?>
        <div>
            <a href="5-login.php">MAKE a Review</a>
        </div>
    <?php endif; ?>


    <!-- CHANGE REVIEW_SHOW TABLE, START -->
    <!-- VIEW EXISTING ARTICLE REVIEWS -->
    <h1>EXISTING Reviews</h1>
    <div>
        <?php
        $title = mysqli_real_escape_string($con, $_GET['title']);

        $sql = "SELECT * FROM reviews WHERE show_title = '$title'";

        $result = mysqli_query($con, $sql);
        $queryResult = mysqli_num_rows($result);
        echo "There are " . $queryResult . " article reviews! ";

        if ($queryResult > 0) {
            while ($row = mysqli_fetch_assoc($result)) {
                echo "<p>Review: " . $row['review_message'] . "<p>"
                    . "<p>Score (/10): " . $row['review_score'] . "</p>"
                    .  "<p>Submitted in: " . $row['review_submitdate'] . "</p>"
                    //.  "<p>" . $row['user_name'] . "</p>"
                    . "</div></a><hr>";
            }
        } else {
            echo "There are no reviews for this article yet!";
        }
        ?>
    </div>
    <!-- CHANGE REVIEW_SHOW TABLE, END -->


    <!-- FOOTER -->
    <?php include 'reuseable/footer.php'; ?>

</body>

</html>