<?php
require_once PROJECT_ROOT_PATH . "/Model/Database.php";
class UserModel extends Database
{
    public function getUsers($limit)
    {
        return $this->select("SELECT * FROM users ORDER BY user_id ASC LIMIT ?", ["i", $limit]);
    }

    public function insertUser($data)
    {
        // Define the SQL query to insert a new user
        $query = "INSERT INTO `users` (`username`, `user_email`, `user_status`) VALUES (?, ?, ?)";

        // Extract user data from the input array
        $username = $data['username'];
        $user_email = $data['user_email'];
        $user_status = $data['user_status'];

        // Bind parameters and execute the query
        $params = ["sss", $username, $user_email, $user_status];
        $this->executeStatement($query, $params);
    }

    public function updateUser($data)
    {
        // Define the SQL query to update an existing user
        $query = "UPDATE `users` SET `username` = ?, `user_email` = ?, `user_status` = ? WHERE `user_id` = ?";

        // Extract user data from the input array
        $user_id = $data['user_id'];
        $username = $data['username'];
        $user_email = $data['user_email'];
        $user_status = $data['user_status'];

        // Bind parameters and execute the query
        $params = ["ssii", $username, $user_email, $user_status, $user_id];
        $this->executeStatement($query, $params);
    }

    public function deleteUser($user_id)
    {
        // Define the SQL query to delete a user by user_id
        $query = "DELETE FROM `users` WHERE `user_id` = ?";

        // Bind the user_id parameter and execute the query
        $params = ["i", $user_id];
        $this->executeStatement($query, $params);
    }
}
