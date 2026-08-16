<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RailIQ - Help</title>
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
    <h2>Help &amp; FAQ</h2>
    <div class="card">
        <h3>How does the reliability score work?</h3>
        <p>RailIQ looks at each train's real historical running data and calculates how often it arrived on time (within 15 minutes of schedule), plus its average delay in minutes.</p>

        <h3>How do I search?</h3>
        <p>Enter the station codes for your source and destination (e.g. HYB for Hyderabad Deccan, AII for Ajmer Junction) on the home page.</p>

        <h3>Do I need an account to book?</h3>
        <p>Yes — you'll be asked to log in the first time you try to book a ticket.</p>
    </div>
</main>

<footer>
    <p>RailIQ &mdash; built on real Indian Railways running data.</p>
</footer>

</body>
</html>
