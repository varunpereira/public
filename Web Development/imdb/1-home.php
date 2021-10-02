<?php include 'reuseable/userdata.php'; ?>


<!DOCTYPE html>
<html lang="en">

<!-- HEAD -->
<?php include 'reuseable/head.php'; ?>

<body class="container" style="background-color:black;">

    <!-- NAVBAR -->
    <?php include 'reuseable/navbar.php'; ?>

    <!-- CAROUSEL -->
    <div id="carouselExampleControls" class="carousel slide carousel-fade" data-ride="carousel">
        <div class="carousel-inner">
            <div class="carousel-item active" data-interval="3000">
                <a href="https://www.youtube.com/watch?v=EXeTwQWrcwY" target="_blank">
                    <img class="d-block w-100" src="img/img1.jpg" alt="First slide">
                </a>
            </div>
            <div class="carousel-item" data-interval="3000">
                <a href="https://www.youtube.com/watch?v=6ZfuNTqbHE8" target="_blank">
                    <img class="d-block w-100" src="img/img2.jpg" alt="Second slide">
                </a>
            </div>
            <div class="carousel-item" data-interval="3000">
                <a href="https://www.youtube.com/watch?v=Q8Fmjr1okK4" target="_blank">
                    <img class="d-block w-100" src="img/img3.jpg" alt="Third slide">
                </a>
            </div>
            <div class="carousel-item" data-interval="3000">
                <a href="https://www.youtube.com/watch?v=mNgwNXKBEW0" target="_blank">
                    <img class="d-block w-100" src="img/img4.jpg" alt="Fourth slide">
                </a>
            </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

    <!-- 3 COLUMNS CONTENT -->
    <div class="container" style="background-color:white">
        <div class="row">
            <div class="col-md">
                One of three columns One of three columns One of three columnsO
            </div>
            <div class="col-md">
                One of three columns One of three columnsOne of three columnsOne of three columnsOne of three columnsOne of three columnsOne of three columns
            </div>
            <div class="col-md">
                One of three columnsOne of three columnsOne of three columnsOne of three columnsOne of three columns
            </div>
        </div>
    </div>

    <!-- FOOTER -->
    <?php include 'reuseable/footer.php'; ?>

    <!-- JS (for CAROUSEL, end only) -->
    <script src="~/lib/jquery/dist/jquery.min.js"></script>
    <script src="~/lib/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <script src="~/js/site.js" asp-append-version="true"></script>

</body>

</html>