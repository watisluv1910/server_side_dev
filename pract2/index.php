<html lang="en">

<head>
    <title>Read page</title>
    <link rel="stylesheet" href="style.css" type="text/css" />
</head>

<body>
    <h1>Students</h1>
    <ul class="header-nav">
        <li><a href="./create.php">Create</a></li>
        <li><a href="./update.php">Update</a></li>
        <li><a href="./delete.php">Delete</a></li>
    </ul>
    <table>
        <tr>
            <th>Id</th>
            <th>First name</th>
            <th>Second name</th>
            <th>Age</th>
        </tr>
        <?php
        $mysqli = new mysqli("db", "user", "password", "appDB");
        $result = $mysqli->query("SELECT * FROM student");
        foreach ($result as $row) {
            echo
            "<tr>
                <td>{$row['id']}</td>
                <td>{$row['first_name']}</td>
                <td>{$row['second_name']}</td>
                <td>{$row['age']}</td>
            </tr>";
        }
        ?>
    </table>
</body>

</html>