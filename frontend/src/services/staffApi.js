async function getStaff() {
  try {
    const response = await fetch('http://localhost:8080/api/staff-profiles')

   
    return await response.json()
    
  } catch (error) {
    console.error('Error fetching staff:', error)
  }
}

async function createStaff(staffData) {
  try {
    const response = await fetch('http://localhost:8080/api/staff-profiles', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(staffData)
    })
    return await response.json()
  } catch (error) {
    console.error('Error creating staff:', error)
  }
}

async function deactivateStaff(staffData) {
  try {
    const response = await fetch(`http://localhost:8080/api/staff-profiles/${staffData.id}`, {

      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ ...staffData, isActive: false })
    })
    return await response.json()
  } catch (error) {
    console.error('Error deactivating staff:', error)
  } 
}

async function activateStaff(staffData) {
  try {
    const response = await fetch(`http://localhost:8080/api/staff-profiles/${staffData.id}`, {
    
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ ...staffData, isActive: true })
    })
    return await response.json()
  } catch (error) {
    console.error('Error activating staff:', error)
  }
}

async function updateStaff(staffData) {
  try {
    const response = await fetch(`http://localhost:8080/api/staff-profiles/${staffData.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(staffData)
    })
    return await response.json()
  } catch (error) {
    console.error('Error updating staff:', error)
  }
}

async function addServiceToStaff(staffId, serviceId) {
  try {
    const response = await fetch(
      `http://localhost:8080/api/staff-profiles/${staffId}/services/${serviceId}`,
      {
        method: 'POST'
      }
    )

    if (!response.ok) {
      throw new Error("Failed to add service to staff")
    }

    return true
  } catch (error) {
    console.error('Error adding service to staff:', error)
  }
}

export { getStaff, createStaff, deactivateStaff, activateStaff, updateStaff }