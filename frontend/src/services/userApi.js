const USER_API_URL = "http://localhost:8080/api/users"

// Retrieve all users from the backend
async function getUsers() {
  try {
    const response = await fetch(USER_API_URL)
    return await response.json()
  } catch (error) {
    console.error("Error fetching users:", error)
  }
}

// Create a new user account
async function createUser(userData) {
  try {
    const response = await fetch(USER_API_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userData),
    })

    return await response.json()
  } catch (error) {
    console.error("Error creating user:", error)
  }
}

// Update an existing user account
async function updateUser(userData) {
  try {
    const response = await fetch(`${USER_API_URL}/${userData.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userData),
    })

    return await response.json()
  } catch (error) {
    console.error("Error updating user:", error)
  }
}

// Disable a user account without removing it from the system
async function deactivateUser(userData) {
  return updateUser({ ...userData, isActive: false })
}

// Restore access to a previously deactivated user account
async function activateUser(userData) {
  return updateUser({ ...userData, isActive: true })
}

export {
  getUsers,
  createUser,
  updateUser,
  deactivateUser,
  activateUser,
}