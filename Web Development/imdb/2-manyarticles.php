<?php include 'reuseable/userdata.php'; ?>


<!DOCTYPE html>
<html lang="en">

<!-- HEAD -->
<?php include 'reuseable/head.php'; ?>

<body class="container" style="background-color:white;">

    <!-- NAVBAR -->
    <?php include 'reuseable/navbar.php'; ?>


    <!-- ARTICLES WITH SEARCH WORD -->
    <h1>Search Page</h1>

    <div class="article-container">

        <?php
        if (isset($_POST['submit-search'])) {
            $search = mysqli_real_escape_string($con, $_POST['search']);



            // CHANGE ARTICLE TABLE, START
            $sql = "SELECT * FROM shows WHERE show_title LIKE '%$search%' OR show_releasedate LIKE '%$search%' OR
    show_cast LIKE '%$search%' OR show_crew LIKE '%$search%' OR show_characters LIKE '%$search%' ";
            $result = mysqli_query($con, $sql);
            $queryResult = mysqli_num_rows($result);

            echo "There are " . $queryResult . " results!";

            // can't be 2 results with same title and date combined, otherwise it won't know which oen to pick (PK)
            if ($queryResult > 0) {
                while ($row = mysqli_fetch_assoc($result)) {
                    echo "<a href='3-onearticle.php?title=" . $row['show_title'] . "&date=" . $row['show_releasedate'] . "'> <div class='article-box'>"
                        . "<h3>" . $row['show_title'] . "</h3>"
                        .  "<p>cast:" . $row['show_cast'] . "</p>"
                        .  "<p>crew:" . $row['show_crew'] . "</p>"
                        .  "<p>characters:" . $row['show_characters'] . "</p>"
                        . "</div></a><hr>";
                }
            }
            // CHANGE ARTICLE TABLE, END



            else {
                echo "There are no results matching your search!";
            }
        }
        ?>

    </div>

    <!-- FOOTER -->
    <?php include 'reuseable/footer.php'; ?>



</body>

</html>