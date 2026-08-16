<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RailIQ - My Profile</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>

<header>
    <div class="header-inner">
        <a href="<c:url value='/'/>" class="brand">RailIQ</a>
        <nav>
            <a href="<c:url value='/'/>">Home</a>
            <a href="<c:url value='/pnr-status'/>">My Bookings</a>
            <a href="<c:url value='/help'/>">Help</a>
        </nav>
    </div>
</header>

<main class="page">
    <h2>My Profile</h2>
    <div class="card">
        <p>Profile details coming soon.</p>
    </div>
</main>

<footer>
    <p>RailIQ &mdash; built on real Indian Railways running data.</p>
</footer>

</body>
</html>
