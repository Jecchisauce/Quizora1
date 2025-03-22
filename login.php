<?php
phpinfo();
header("Content-Type: application/json");

$servername = "localhost";
$username = "root";  
$password = "";      
$dbname = "quizora_db";  

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    echo json_encode(["success" => false, "message" => "Database connection failed"]);
    exit();
}

// Read JSON input (from your app)
$data = json_decode(file_get_contents("php://input"), true);
$user = $data['username'] ?? '';
$pass = $data['password'] ?? '';

// Input validation
if (empty($user) || empty($pass)) {
    echo json_encode(["success" => false, "message" => "Username and password are required"]);
    exit();
}

// Query to retrieve hashed password
$sql = "SELECT password FROM users WHERE username = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $user);
$stmt->execute();
$result = $stmt->get_result();

// Check if user exists
if ($row = $result->fetch_assoc()) {
    $stored_hashed_password = $row['password'];

    // Verify hashed password
    if (password_verify($pass, $stored_hashed_password)) {
        echo json_encode(["success" => true, "message" => "Login successful"]);
    } else {
        echo json_encode(["success" => false, "message" => "Invalid password"]);
    }
} else {
    echo json_encode(["success" => false, "message" => "User not found"]);
}

$conn->close();
?>
