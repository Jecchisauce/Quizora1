<?php
require 'db_connection.php'; // Ensure this connects to your database

header("Content-Type: application/json");

// Check if all required fields are provided
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['email'], $_POST['current_password'], $_POST['new_password'])) {

    // Sanitize inputs
    $email = filter_var($_POST['email'], FILTER_SANITIZE_EMAIL);
    $current_password = trim($_POST['current_password']);
    $new_password = trim($_POST['new_password']);

    // Validate sanitized email
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        echo json_encode(["success" => false, "message" => "Invalid email format"]);
        exit;
    }

    // Check if user exists by email
    $stmt = $conn->prepare("SELECT password FROM users WHERE email = ?");
    if (!$stmt) {
        echo json_encode(["success" => false, "message" => "Database error: " . $conn->error]);
        exit;
    }

    $stmt->bind_param("s", $email);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        $stmt->bind_result($stored_password);
        $stmt->fetch();

        if (password_verify($current_password, $stored_password)) {
            $hashed_password = password_hash($new_password, PASSWORD_DEFAULT);

            $update_stmt = $conn->prepare("UPDATE users SET password = ? WHERE email = ?");
            if (!$update_stmt) {
                echo json_encode(["success" => false, "message" => "Database error: " . $conn->error]);
                exit;
            }

            $update_stmt->bind_param("ss", $hashed_password, $email);

            if ($update_stmt->execute()) {
                echo json_encode(["success" => true, "message" => "Password updated successfully"]);
            } else {
                echo json_encode(["success" => false, "message" => "Failed to update password"]);
            }

            $update_stmt->close();
        } else {
            echo json_encode(["success" => false, "message" => "Current password is incorrect"]);
        }
    } else {
        echo json_encode(["success" => false, "message" => "User not found"]);
    }

    $stmt->close();
    $conn->close();
} else {
    echo json_encode(["success" => false, "message" => "Invalid request or missing parameters"]);
}
?>
