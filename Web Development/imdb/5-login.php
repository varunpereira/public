<?php

session_start();

$server = "localhost";
$username = "root";
$password = "";
$dbname = "imdb";
$con = mysqli_connect($server, $username, $password, $dbname);;


$valid = true;

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
					$valid = true;

					$_SESSION['user_id'] = $user_data['user_id'];

					header("Location: 1-home.php");
					die;
				} else {
					$valid = false;
				}
			}
		}
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

	<?php include 'reuseable/navbar.php'; ?>


	<!-- LOG IN -->
	<div id="box" style="margin-top: 7%;margin-bottom:10%">
		<form method="post">
			<div style="font-size: 20px;">Login</div><br>

			<input id="text" type="text" name="user_name"><br><br>
			<input id="text" type="password" name="password"><br><br>

			<input id="button" type="submit" value="Login"><br><br>

			<a href="4-signup.php">Click to Signup</a><br><br>

			<?php if ($valid == false) : ?>
				<div style="color:red;">Wrong username or password!</div>
			<?php endif; ?>
		</form>
	</div>


	<!-- FOOTER -->
	<?php include 'reuseable/footer.php'; ?>

</body>

</html>