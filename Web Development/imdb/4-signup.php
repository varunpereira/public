<?php
session_start();

$server = "localhost";
$username = "root";
$password = "";
$dbname = "imdb";
$con = mysqli_connect($server, $username, $password, $dbname);

function random_num($length)
{
	$text = "";
	if ($length < 5) {
		$length = 5;
	}
	$len = rand(4, $length);
	for ($i = 0; $i < $len; $i++) {
		$text .= rand(0, 9);
	}
	return $text;
}

$valid = true;
$userNameExists = false;

if ($_SERVER['REQUEST_METHOD'] == "POST") {
	//something was posted
	$user_name = $_POST['user_name'];
	$password = $_POST['password'];

	if (!empty($user_name) && !empty($password) && !is_numeric($user_name)) {

		$query = "select * from users where user_name = '$user_name'";
		$result = mysqli_query($con, $query);
		if ($result) {
			if (mysqli_num_rows($result) > 0) {
				$userNameExists = true;
				// header("Location: 1-home.php");
				// die;
			} else {
				$userNameExists = false;

				//save to database
				$user_id = random_num(20);
				$query = "insert into users (user_id,user_name,password) values ('$user_id','$user_name','$password')";
				mysqli_query($con, $query);

				if ($_SERVER['REQUEST_METHOD'] == "POST") {
					//something was posted
					$user_name = $_POST['user_name'];
					$password = $_POST['password'];

					if (!empty($user_name) && !empty($password) && !is_numeric($user_name)) {

						//read from database
						$query = "select * from users where user_name = '$user_name' limit 1";
						$result = mysqli_query($con, $query);

						if ($result) {
							if ($result && mysqli_num_rows($result) > 0) {

								$user_data = mysqli_fetch_assoc($result);

								if ($user_data['password'] === $password) {

									$_SESSION['user_id'] = $user_data['user_id'];

									header("Location: 1-home.php");
									die;
								}
							}
						}
					}
				}
			}
		}

		$valid = true;
	} else {
		$valid = false;
	}
}
?>


<!DOCTYPE html>
<html>

<!-- HEAD -->
<?php include 'reuseable/head.php'; ?>

<body class="container" style="background-color:black;">

	<!-- NAVBAR -->
    <?php include 'reuseable/navbar.php'; ?>


	<!-- SIGN IN -->
	<div id="box" style="margin-top: 7%;margin-bottom:10%">
		<form method="post">
			<div style="font-size: 20px;">Signup</div><br>

			<input id="text" type="text" name="user_name"><br><br>
			<input id="text" type="password" name="password"><br><br>

			<input id="button" type="submit" value="Signup"><br><br>

			<a href="5-login.php">Click to Login</a><br><br>
			<?php if ($valid == false) : ?>
				<div style="color:red;">Invalid username or password!
					Username and Password can not be empty and Username can't only contain numbers.</div>
			<?php elseif ($userNameExists == true) : ?>
				<div style="color:red;">Username already exists!</div>
			<?php endif; ?>
		</form>
	</div>

	<!-- FOOTER -->
	<?php include 'reuseable/footer.php'; ?>


</body>

</html>