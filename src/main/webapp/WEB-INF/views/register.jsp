<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RailIQ - Register</title>
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
    <h2>Create your RailIQ account</h2>
    <p>Sign up to book trains and track your bookings.</p>

    <div id="register-error" class="form-error"></div>

    <form id="register-form">
        <div class="field">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required autocomplete="username">
        </div>
        <div class="field">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required autocomplete="email">
        </div>
        <div class="field">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required autocomplete="new-password">
        </div>
        <button type="submit" class="btn-primary">Register</button>
    </form>

    <p class="auth-switch">Already have an account? <a href="/login">Log in here</a></p>
</main>
<footer>
    <p>RailIQ &mdash; built on real Indian Railways running data.</p>
</footer>
<script src="/js/app.js"></script>
</body>
</html>
