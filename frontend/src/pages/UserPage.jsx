import { useState, useEffect } from "react"
import {
  getUsers,
  createUser,
  deactivateUser,
  activateUser,
  updateUser,
} from "../services/userApi"
import UserForm from "../components/UserForm"
import "../css/UserPage.css"


function UsersPage() {
  const [users, setUsers] = useState([])
  const [selectedUser, setSelectedUser] = useState(null)

  async function loadUsers() {
    const data = await getUsers()
    setUsers(data)
  }
  useEffect(() => {
    loadUsers()
  }, [])

  async function handleUserCreate(userData) {
    await createUser(userData)
    await loadUsers()
  }

  async function handleUserChange(userData) {
    if (userData.isActive) {
      await deactivateUser(userData)
    } else {
      await activateUser(userData)
    }
    await loadUsers()
  }
  async function handleUserUpdate(userData) {
    await updateUser(userData)
    await loadUsers()
    setSelectedUser(null)
  }

  return (
    <div className="user-page">
      <div className="user-page__header">
      <h1>Manage Users</h1>
      <p>View and manage all users in the system</p>
      </div>

      <div className="user-page__create">
        <UserForm onSubmit={handleUserCreate} /> 
      </div>
      
      <div className="user-list">
        {users.map((user) => (
          <div key={user.id} className="user-card">
            <div className="user-card__header">
              <h2>{user.fullName}</h2>
              <span
                className={
                  user.isActive
                    ? "user-status user-status--active"
                    : "user-status user-status--inactive"
                }
              >
                {user.isActive ? "Active" : "Inactive"}
              </span>
            </div>
            <div className="user-card__body">
              <p>
                <strong>User Id:</strong> {user.id}
              </p>
              <p>
                <strong>Email:</strong> {user.email}
              </p>
              <p>
                <strong>Role:</strong> {user.role}
              </p>
              <p>
                <strong>Status:</strong> {user.isActive ? "Active" : "Inactive"}
              </p>
            </div>

            <div className="user-card__actions">
              <button 
                type="button"
                className="btn btn--secondary"
                onClick={() => setSelectedUser(user)}>
                Edit
              </button>
              <button
                type="button"
                className={user.isActive ? "btn btn--danger" : "btn btn--success"}
                onClick={() => handleUserChange(user)}>
                {user.isActive ? "Deactivate" : "Activate"}                
              </button>
            </div>
          </div>
        ))}
      </div>

        {selectedUser && (
            <div className="user-edit-modal" onClick={() => setSelectedUser(null)}>
                <div className="user-edit-modal__content" onClick={(e) => e.stopPropagation()}>
                  <div className="modal-header">
                   <h2>Edit User</h2>
                   <button
                    type="button"
                    className="btn btn--secondary"
                    onClick={() => setSelectedUser(null)}
                >
                    Close
                </button>
                </div>
                <UserForm 
                    onSubmit={handleUserUpdate} 
                    initialData={selectedUser}
                    submitLabel="Update User"
                    title="Edit User"
                />
                </div>
            </div>

        )}




    </div>
  )
}

export default UsersPage