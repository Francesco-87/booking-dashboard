import { useNavigate } from "react-router-dom"
import BackButton from "../components/BackButton"
import "../css/AdminPage.css"

function AdminPage() {
  const navigate = useNavigate()

  return (
    <div className="admin-page">
      <BackButton />
      <div className="admin-page__header">
        <h1>Admin Dashboard</h1>
        <p>Manage your system.</p>
      </div>

      <div className="admin-grid">
        <div className="admin-card">
          <h2>Services</h2>
          <p>Create, update, activate and deactivate services.</p>
          <button
            className="btn btn--primary"
            onClick={() => navigate("/admin/services")}
          >
            Manage Services
          </button>
        </div>

        <div className="admin-card">
          <h2>Staff</h2>
          <p>Manage staff profiles and performers.</p>
          <button
            className="btn btn--primary"
            onClick={() => navigate("/admin/staff")}
          >
            Manage Staff
          </button>
        </div>

        <div className="admin-card">
          <h2>Users</h2>
          <p>Create and manage system users.</p>
          <button
            className="btn btn--primary"
            onClick={() => navigate("/admin/user")}
          >
            Manage Users
          </button>
        </div>

        <div className="admin-card">
          <h2>Bookings</h2>
          <p>View, create and manage bookings.</p>
          <button
            className="btn btn--primary"
            onClick={() => navigate("/admin/bookings")}
          >
            Manage Bookings
          </button>
        </div>
      </div>
    </div>
  )
}

export default AdminPage