<html lang="en">

<head>
    <title>Add student</title>
    <link rel="stylesheet" href="style.css" type="text/css" />
</head>

<body>
    <h1>Add student</h1>
    <ul class="header-nav">
        <li><a href="./index.php">Read</a></li>
        <li><a href="./update.php">Update</a></li>
        <li><a href="./delete.php">Delete</a></li>
    </ul>
    <form name="form" action="create.php" method="post">
        <input type="text" name="first_name" id="first_name" placeholder="Enter the student's first name">
        <input type="text" name="second_name" id="second_name" placeholder="Enter the student's second name">
        <input type="text" name="age" id="age" placeholder="Enter the student's age">
        <input type="submit" name="save" id="save" value="Save">
    </form>
    <?php
    if (isset($_POST['first_name'])) {
        $mysqli = new mysqli("db", "user", "password", "appDB");
        $result =
            $mysqli->query("INSERT INTO `student` (`first_name`, `second_name`, `age`) 
                VALUES ('{$_POST['first_name']}', '{$_POST['second_name']}', '{$_POST['age']}');");
    }
    ?>

</body>

</html>