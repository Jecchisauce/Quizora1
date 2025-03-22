<?php
phpinfo();
// Database connection
$host = "localhost";
$user = "root";
$pass = "";
$dbname = "quizora_db";

$conn = new mysqli($host, $user, $pass, $dbname);

// Check connection
if ($conn->connect_error) {
    die(json_encode(["success" => false, "message" => "Database connection failed: " . $conn->connect_error]));
}

// Read input data
$data = json_decode(file_get_contents("php://input"), true);

// Debugging: Print received data for verification
file_put_contents("debug_log.txt", json_encode($data) . PHP_EOL, FILE_APPEND);

// Extract and sanitize inputs
$nickname = $data['nickname'] ?? '';
$email = $data['email'] ?? '';
$password = $data['password'] ?? '';
$confirmPassword = $data['confirmPassword'] ?? '';

// Input validation
if (empty($nickname) || empty($email) || empty($password) || empty($confirmPassword)) {
    echo json_encode(["success" => false, "message" => "All fields are required."]);
    exit();
}

// Check if passwords match
if ($password !== $confirmPassword) {
    echo json_encode(["success" => false, "message" => "Passwords do not match."]);
    exit();
}

// Check if nickname or email already exists
$stmt = $conn->prepare("SELECT nickname, email FROM users WHERE nickname = ? OR email = ?");
$stmt->bind_param("ss", $nickname, $email);
$stmt->execute();
$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {
    if ($row['email'] == $email) {
        echo json_encode(["success" => false, "message" => "Email already taken."]);
    } elseif ($row['nickname'] == $nickname) {
        echo json_encode(["success" => false, "message" => "Nickname already taken."]);
    }
    exit();
}
$stmt->close();

// Hash the password
$hashed_password = password_hash($password, PASSWORD_DEFAULT);

// Insert new user
$stmt = $conn->prepare("INSERT INTO users (nickname, email, password) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $nickname, $email, $hashed_password);

if ($stmt->execute()) {
    echo json_encode(["success" => true, "message" => "User registered successfully."]);
} else {
    echo json_encode(["success" => false, "message" => "Error registering user."]);
}

$stmt->close();
$conn->close();
?>
