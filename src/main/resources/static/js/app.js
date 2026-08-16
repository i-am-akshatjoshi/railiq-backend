// app.js — wired to RailIQ's actual REST API

document.addEventListener("DOMContentLoaded", () => {

  // === SEARCH FORM (index.jsp) ===
  const searchForm = document.getElementById("search-form");
  if (searchForm) {
    searchForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const source = e.target.source.value.trim().toUpperCase();
      const destination = e.target.destination.value.trim().toUpperCase();
      const journeyDate = e.target.journeyDate.value;
      const travelClass = e.target.travelClass.value;
      const quota = e.target.quota.value;
      const wlPosition = e.target.wlPosition.value || 20;

      if (!source || !destination || !journeyDate) return;

      const today = new Date();
      const chosen = new Date(journeyDate);
      const daysBeforeJourney = Math.max(1, Math.round((chosen - today) / (1000 * 60 * 60 * 24)));

      const params = new URLSearchParams({
        source, destination,
        daysBeforeJourney,
        initialWlPosition: wlPosition,
        quota, travelClass
      });

      window.location.href = `/results?${params.toString()}`;
    });
  }

  // === RESULTS PAGE (results.jsp) ===
  if (window.location.pathname === "/results") {
    const container = document.getElementById("results-container");
    if (container) {
      const params = new URLSearchParams(window.location.search);

      fetch(`/api/predictions/recommend?${params.toString()}`)
        .then(res => {
          if (!res.ok) throw new Error(`Status ${res.status}`);
          return res.json();
        })
        .then(trains => renderRecommendations(trains, container))
        .catch(err => {
          console.error("Search error:", err);
          container.innerHTML = "<p>Something went wrong. Please try again.</p>";
        });
    }
  }

  // === BOOKING PAGE (booking.jsp) ===
  const bookingForm = document.getElementById("booking-form");
  if (bookingForm) {
    bookingForm.addEventListener("submit", (e) => {
      e.preventDefault();

      const token = localStorage.getItem("railiq_token");
      if (!token) {
        requireLogin();
        return;
      }

      const params = new URLSearchParams(window.location.search);
      const trainNo = params.get("trainNo");
      const tripNumber = params.get("tripNumber");

      if (!trainNo || !tripNumber) {
        alert("Missing train selection. Please search again.");
        window.location.href = "/";
        return;
      }

      const bookingData = {
        trainNo: trainNo,
        tripNumber: parseInt(tripNumber, 10),
        journeyDate: e.target.journeyDate.value,
        travelClass: e.target.travelClass.value,
        quota: e.target.quota ? e.target.quota.value : "GN"
      };

      fetch("/api/bookings", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer " + token
        },
        body: JSON.stringify(bookingData)
      })
        .then(res => {
          if (res.status === 401 || res.status === 403) {
            localStorage.removeItem("railiq_token");
            throw new Error("Session expired, please log in again.");
          }
          if (!res.ok) throw new Error(`Status ${res.status}`);
          return res.json();
        })
        .then(data => {
          alert(`Booking created! Status: ${data.status}, Booking ID: ${data.bookingId}`);
          window.location.href = "/pnr-status";
        })
        .catch(err => {
          console.error("Booking error:", err);
          alert(err.message || "Booking failed. Please try again.");
        });
    });
  }

  // === MY BOOKINGS PAGE (pnr-status.jsp) ===
  if (window.location.pathname === "/pnr-status") {
    const container = document.getElementById("results-container");
    if (container) {
      const token = localStorage.getItem("railiq_token");
      if (!token) {
        requireLogin();
        return;
      }
      loadMyBookings(container);
    }
  }

  // === LOGIN PAGE (login.jsp) ===
  const loginForm = document.getElementById("login-form");
  if (loginForm) {
    loginForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const errorBox = document.getElementById("login-error");
      errorBox.textContent = "";

      const username = e.target.username.value.trim();
      const password = e.target.password.value;

      fetch("/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
      })
        .then(res => {
          if (!res.ok) throw new Error("Invalid username or password");
          return res.json();
        })
        .then(data => {
          localStorage.setItem("railiq_token", data.token);
          localStorage.setItem("railiq_username", data.username);
          const redirectTo = sessionStorage.getItem("railiq_redirect_after_login") || "/";
          sessionStorage.removeItem("railiq_redirect_after_login");
          window.location.href = redirectTo;
        })
        .catch(err => {
          console.error("Login error:", err);
          errorBox.textContent = err.message || "Login failed. Please try again.";
        });
    });
  }

  // === REGISTER PAGE (register.jsp) ===
  const registerForm = document.getElementById("register-form");
  if (registerForm) {
    registerForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const errorBox = document.getElementById("register-error");
      errorBox.textContent = "";

      const username = e.target.username.value.trim();
      const email = e.target.email.value.trim();
      const password = e.target.password.value;

      fetch("/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, email, password })
      })
        .then(res => {
          if (!res.ok) throw new Error("Registration failed. Try a different username.");
          return res.json();
        })
        .then(() => {
          return fetch("/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
          });
        })
        .then(res => res.json())
        .then(data => {
          localStorage.setItem("railiq_token", data.token);
          localStorage.setItem("railiq_username", data.username);
          window.location.href = "/";
        })
        .catch(err => {
          console.error("Register error:", err);
          errorBox.textContent = err.message || "Registration failed. Please try again.";
        });
    });
  }
});

function loadMyBookings(container) {
  const token = localStorage.getItem("railiq_token");
  fetch("/api/bookings/my", {
    headers: { "Authorization": "Bearer " + token }
  })
    .then(res => {
      if (res.status === 401 || res.status === 403) {
        localStorage.removeItem("railiq_token");
        throw new Error("Session expired, please log in again.");
      }
      if (!res.ok) throw new Error(`Status ${res.status}`);
      return res.json();
    })
    .then(bookings => {
      if (bookings.length === 0) {
        container.innerHTML = "<p>You have no bookings yet.</p>";
        return;
      }
      container.innerHTML = "";
      bookings.forEach(b => {
        const card = document.createElement("div");
        card.className = "card";
        card.innerHTML = `
          <div>
            <h3>Booking #${b.bookingId} &mdash; Train ${escapeHtml(b.trainNo || "")}</h3>
            <p>Journey Date: ${b.journeyDate} &middot; Class: ${b.travelClass}</p>
            <p>Status: ${b.status}</p>
          </div>
        `;
        container.appendChild(card);
      });
    })
    .catch(err => {
      console.error("Bookings fetch error:", err);
      container.innerHTML = `<p>${escapeHtml(err.message || "Could not load your bookings.")}</p>`;
    });
}

// === Renders the combined recommendation results (results.jsp) ===
function renderRecommendations(trains, container) {
  container.innerHTML = "";

  if (trains.length === 0) {
    container.innerHTML = "<p>No running history found for this route. Try a different station pair.</p>";
    return;
  }

  trains.forEach((train, index) => {
    const card = document.createElement("div");
    card.className = "card recommendation-card";
    card.innerHTML = `
      <div>
        <div class="rec-rank">#${index + 1} Recommended</div>
        <h3>${escapeHtml(train.trainName)} (${escapeHtml(train.trainNo)})</h3>
        <div class="rec-metrics">
          <span>On-time: <strong>${Math.round(train.onTimePercentage)}%</strong></span>
          <span>Avg delay: <strong>${train.avgDelayMinutes.toFixed(1)} min</strong></span>
          <span>Confirmation odds: <strong>${train.confirmationProbabilityPercent.toFixed(1)}%</strong></span>
        </div>
      </div>
      <div class="rec-score">
        <div class="score-value">${train.combinedScore}</div>
        <div class="score-label">Match Score</div>
        <button onclick="bookTrain('${train.trainNo}', ${train.tripNumber})">Book Now</button>
      </div>
    `;
    container.appendChild(card);
  });
}

function bookTrain(trainNo, tripNumber) {
  window.location.href = `/booking?trainNo=${encodeURIComponent(trainNo)}&tripNumber=${tripNumber}`;
}

// Sends the user to a real login page, remembering where to return them afterward.
function requireLogin() {
  sessionStorage.setItem("railiq_redirect_after_login", window.location.pathname + window.location.search);
  window.location.href = "/login";
}

function escapeHtml(str) {
  const div = document.createElement("div");
  div.textContent = str ?? "";
  return div.innerHTML;
}