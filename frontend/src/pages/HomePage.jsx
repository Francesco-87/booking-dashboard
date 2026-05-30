import { useNavigate } from "react-router-dom"
import "../css/HomePage.css"

function HomePage() {
  const navigate = useNavigate()

  return (
    <div className="home-page">
      <div className="home-page__hero">
        <h1>Booking & Operations Dashboard</h1>
        <p>Select a role to continue.</p>
      </div>

      <div className="home-grid">
        <div className="home-card">
          <h2>Admin</h2>
          <p>
            Manage services, staff, users and bookings.
          </p>
          <button
            className="btn btn--primary"
            onClick={() => navigate("/admin")}
          >
            Enter Admin Area
          </button>
        </div>

        <div className="home-card">
          <h2>Customer</h2>
          <p>
            View and manage customer bookings.
          </p>
          <button
            className="btn btn--primary"
            onClick={() => navigate("/customer")}
          >
            Enter Customer Area
          </button>
        </div>
      </div>
    </div>
  )
}

export default HomePage