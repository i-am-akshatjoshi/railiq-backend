<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RailIQ - Find the Most Reliable Train</title>
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

<section class="hero">
    <p class="eyebrow">Real running-history &middot; Not guesswork</p>
    <h1>Choose your train by how it actually runs.</h1>
    <p class="hero-sub">RailIQ ranks trains on your route by on-time performance AND seat confirmation odds.</p>

    <div class="track-divider" aria-hidden="true"></div>

    <form class="card search-card" id="search-form">
        <div class="field-row">
            <div class="field">
                <label for="source">From</label>
                <input type="text" id="source" name="source" placeholder="e.g. HYB" required maxlength="10">
            </div>
            <div class="field">
                <label for="destination">To</label>
                <input type="text" id="destination" name="destination" placeholder="e.g. AII" required maxlength="10">
            </div>
        </div>

        <div class="field-row">
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
                </select>
            </div>
        </div>

        <div class="field-row">
            <div class="field">
                <label for="quota">Quota</label>
                <select id="quota" name="quota">
                    <option value="GN">General</option>
                    <option value="TQ">Tatkal</option>
                </select>
            </div>
            <div class="field">
                <label for="wlPosition">Expected Waitlist Position</label>
                <input type="number" id="wlPosition" name="wlPosition" value="20" min="1" max="500">
            </div>
        </div>

        <button type="submit" class="btn-primary">Find the Best Train</button>
        <p class="hint">Use station codes for now (e.g. <strong>HYB</strong>, <strong>AII</strong>). Not sure your waitlist position? 20 is a reasonable general-quota estimate.</p>
    </form>
</section>

<footer>
    <p>RailIQ &mdash; built on real Indian Railways running data.</p>
</footer>

<script src="<c:url value='/js/app.js'/>"></script>
</body>
</html>