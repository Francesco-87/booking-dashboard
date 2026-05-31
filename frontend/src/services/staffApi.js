const STAFF_API_URL = "http://localhost:8080/api/staff-profiles"

// Retrieve all staff profiles from the backend
async function getStaff() {
  try {
    const response = await fetch(STAFF_API_URL)
    return await response.json()
  } catch (error) {
    console.error("Error fetching staff:", error)
  }
}

// Create a new staff profile
async function createStaff(staffData) {
  try {
    const response = await fetch(STAFF_API_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(staffData),
    })

    return await response.json()
  } catch (error) {
    console.error("Error creating staff:", error)
  }
}

// Update an existing staff profile
async function updateStaff(staffData) {
  try {
    const response = await fetch(`${STAFF_API_URL}/${staffData.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(staffData),
    })

    return await response.json()
  } catch (error) {
    console.error("Error updating staff:", error)
  }
}

// Mark a staff profile as inactive so it can no longer be booked
async function deactivateStaff(staffData) {
  return updateStaff({ ...staffData, isActive: false })
}

// Restore a previously deactivated staff profile
async function activateStaff(staffData) {
  return updateStaff({ ...staffData, isActive: true })
}

export {
  getStaff,
  createStaff,
  updateStaff,
  deactivateStaff,
  activateStaff,
}