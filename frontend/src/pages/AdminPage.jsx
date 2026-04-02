import { useNavigate } from "react-router-dom"

function AdminPage() {
  const navigate = useNavigate()

  return (
    <div>
      <h1>Admin Dashboard</h1>
      <p>Manage your system</p>

      <div>
        <button onClick={() => navigate("/admin/services")}>
          Manage Services
        </button>
      </div>

      <div>
        <button disabled>
          Manage Staff (coming soon)
        </button>
      </div>

      <div>
        <button disabled>
          Manage Bookings (coming soon)
        </button>
      </div>
    </div>
  )
}

export default AdminPage