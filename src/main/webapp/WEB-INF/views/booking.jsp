<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RailIQ - Book Ticket</title>
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
    <h2>Book Your Ticket</h2>
    <form class="card" id="booking-form">
        <div class="field">
            <label for="journeyDate">Journey Date</label>
            <input type="date" id="journeyDate" name="journeyDate" required>
        </div>

        <div class="field">
            <label for="travelClass">Class</label>
            <select id="travelClass" name="travelClass" required>
                <option value="SL">Sleeper (SL)</option>
                <option value="3A">AC 3 Tier (3A)</option>
                <option value="2A">AC 2 Tier (2A)</option>
                <option value="1A">AC First Class (1A)</option>
            </select>
        </div>

        <div class="field">
            <label for="quota">Quota</label>
            <select id="quota" name="quota">
                <option value="GN">General</option>
                <option value="TQ">Tatkal</option>
            </select>
        </div>

        <button type="submit" class="btn-primary">Confirm Booking</button>
    </form>
</main>

<footer>
    <p>RailIQ &mdash; built on real Indian Railways running data.</p>
</footer>

<script src="<c:url value='/js/app.js'/>"></script>
</body>
</html>
