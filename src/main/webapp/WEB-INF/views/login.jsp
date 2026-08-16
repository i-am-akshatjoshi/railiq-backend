<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RailIQ - Login</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<header>
    <div class="header-inner">
        <a href="/" class="brand">RailIQ</a>
        <nav>
            <a href="/">Home</a>
            <a href="/pnr-status">My Bookings</a>
            <a href="/help">Help</a>
        </nav>
    </div>
</header>
<main class="page auth-shell">
    <h2>Log in to RailIQ</h2>
    <p>Use your account to book and track trains.</p>

    <div id="login-error" class="form-error"></div>

    <form id="login-form">
        <div class="field">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required autocomplete="username">
        </div>
        <div class="field">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required autocomplete="current-password">
        </div>
        <button type="submit" class="btn-primary">Log In</button>
    </form>

    <p class="auth-switch">Don't have an account? <a href="/register">Register here</a></p>
</main>
<footer>
    <p>RailIQ &mdash; built on real Indian Railways running data.</p>
</footer>
<script src="/js/app.js"></script>
</body>
</html>
