<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark" style="background-color:black;padding-top:15px;padding-bottom:25px;">
    <!-- <a class="navbar-brand" href="#">Navbar</a> -->
    <a href="1-home.php" class="navbar-brand" style="padding-top:15px;padding-left:2px;">
        <div style="background-color: #f3ce13;color: black;padding-left: 10px;padding-right: 10px;
              border-radius:3px;font-size:20px;font-weight: 1000;padding-top:5px;padding-bottom:5px;">IMDb</div>
    </a>

    <button style="margin-top:10px;" class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent" style="margin-top:10px;">

        <form action="2-manyarticles.php" method="POST" class="input-group mr-auto form-inline my-2 my-lg-0" style="padding-right:10px;">
            <input type="text" name="search" placeholder="search..." class="form-control" style="margin-right:0px;">
            <div class="input-group-append" style=" padding-left:0px;margin-bottom:0px;">
                <button type="submit" name="submit-search" class="btn btn-secondary btn-sm" style="width:100px;height:40px;">Search</button>
            </div>
        </form>

        <?php if (isset($_SESSION['user_id'])) : ?>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <?php echo $user_data['user_name']; ?>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="">
                        <a class="dropdown-item" href="6-logout.php">Logout</a>
                        <a class="dropdown-item" href="8-updateuser.php">Update Details</a>
                    </div>
                </li>
            </ul>
        <?php else : ?>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="5-login.php">LOGIN</a>
                </li>
            </ul>
        <?php endif; ?>

    </div>
</nav>