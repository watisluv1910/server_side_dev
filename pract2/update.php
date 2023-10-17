<html lang="en">

<head>
    <title>Update student</title>
    <link rel="stylesheet" href="style.css" type="text/css" />
</head>

<body>
    <h1>Update student</h1>
    <ul class="header-nav">
        <li><a href="./create.php">Create</a></li>
        <li><a href="./index.php">Read</a></li>
        <li><a href="./delete.php">Delete</a></li>
    </ul>
    <form name="form" action="update.php" method="post">
        <input type="text" name="id" id="id" placeholder="Enter the id of a student you would like to update info about">
        <input type="text" name="first_name" id="first_name" placeholder="Enter the new first name">
        <input type="text" name="second_name" id="second_name" placeholder="Enter the new second name">
        <input type="text" name="age" id="age" placeholder="Enter the new age">
        <input type="submit" name="update" id="update" value="Update">
    </form>
    <?php
    if (isset($_POST['id'])) {
        $mysqli = new mysqli("db", "user", "password", "appDB");
        $result =
            $mysqli->query(
                "UPDATE `student` 
                SET `first_name` = '{$_POST['first_name']}', 
                    `second_name` = '{$_POST['second_name']}', 
                    `age` = '{$_POST['age']}' 
                WHERE `id` = '{$_POST['id']}';"
            );
    }
    ?>

</body>

</html>