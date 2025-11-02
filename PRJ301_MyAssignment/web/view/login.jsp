<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }

        /* Thanh trên cùng */
        header {
            background-color: #007bff;
            color: white;
            padding: 15px 30px;
            text-align: center;
            font-size: 24px;
            font-weight: bold;
        }

        /* Hộp đăng nhập */
        .login-container {
            background-color: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 450px;
            margin: 50px auto;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
        }

        .input-group {
            position: relative;
            margin-bottom: 30px;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 15px 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 16px;
            box-sizing: border-box;
        }

        label {
            position: absolute;
            left: 10px;
            top: 15px;
            font-size: 16px;
            transition: 0.2s ease all;
            color: #888;
        }

        input:focus + label, input:not(:placeholder-shown) + label {
            top: -12px;
            font-size: 12px;
            color: #007bff;
        }

        button {
            width: 100%;
            padding: 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        button i {
            margin-right: 8px; /* Cách icon mũi tên với chữ */
            font-size: 18px;
        }

        button:hover {
            background-color: #0056b3;
        }

        .forgot-password {
            text-align: center;
            margin-top: 12px;
        }

        .forgot-password a {
            color: #007bff;
            text-decoration: none;
        }

        .forgot-password a:hover {
            text-decoration: underline;
        }

        .error {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>

    <!-- Thanh trên cùng -->
    <header>
        LeaveBoard
    </header>

    <!-- Hộp đăng nhập -->
    <div class="login-container">
        <h2>Sign in to LeaveBoard</h2>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <!-- Email or Username Input -->
            <div class="input-group">
                <input type="text" id="login" name="login" required placeholder=" ">
                <label for="login">Email or Username</label>
            </div>

            <!-- Password Input -->
            <div class="input-group">
                <input type="password" id="password" name="password" required placeholder=" ">
                <label for="password">Password</label>
            </div>

            <!-- Login Button -->
            <button type="submit">
                <i class="fas fa-sign-in-alt"></i> Login
            </button>

            <!-- Error message -->
            <div class="error">${error}</div>
        </form>

        <!-- Forgot password link -->
        <div class="forgot-password">
            <a href="#">Forgot password?</a>
        </div>
    </div>

    <!-- Font Awesome CDN for icons -->
    <script src="https://kit.fontawesome.com/a076d05399.js"></script>

</body>
</html>
