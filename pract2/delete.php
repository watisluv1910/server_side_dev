<html lang="en">

<head>
    <title>Delete student</title>
    <link rel="stylesheet" href="style.css" type="text/css" />
</head>

<body>
    <h1>Delete student</h1>
    <ul class="header-nav">
        <li><a href="./create.php">Create</a></li>
        <li><a href="./index.php">Read</a></li>
        <li><a href="./update.php">Update</a></li>
    </ul>
    <form name="form" action="" method="post">
        <input type="text" name="id" id="id" placeholder="Enter the id of a student to be deleted">
        <input type="submit" value="delete" id="delete" name="delete">
    </form>
    <?php
    if (isset($_POST['id'])) {
        $mysqli = new mysqli("db", "user", "password", "appDB");
        $result = $mysqli->query("DELETE FROM `student` WHERE `id`={$_POST['id']};");
    }
    ?>

</body>

</html>