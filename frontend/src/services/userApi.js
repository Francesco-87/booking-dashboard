async function getUsers() {
  try {
    const response = await fetch('http://localhost:8080/api/users')   
    
    return await response.json()
  } catch (error) {
    console.error('Error fetching users:', error)
  }
}

async function createUser(userData) {
  try {
    const response = await fetch('http://localhost:8080/api/users', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userData)
    })
    return await response.json()
  } catch (error) {
    console.error('Error creating user:', error)
  }
}

async function deactivateUser(userData) {
  try {
    console.log("Deactivate payload:", { ...userData, isActive: false })
console.log("Activate payload:", { ...userData, isActive: true })
    const response = await fetch(`http://localhost:8080/api/users/${userData.id}/status`, {
    
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ ...userData, isActive: false })
    })
    return await response.json()
  } catch (error) {
    console.error('Error deactivating user:', error)
  }
}

async function activateUser(userData) {
  try {
    const response = await fetch(`http://localhost:8080/api/users/${userData.id}/status`, {
    
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ ...userData, isActive: true })
    })
    return await response.json()
  } catch (error) {
    console.error('Error activating user   :', error)
  }
}

async function updateUser(userData) {
  try {
    const response = await fetch(`http://localhost:8080/api/users/${userData.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userData)
    })
    return await response.json()
  } catch (error) {
    console.error('Error updating user:', error)
  }
}

export { getUsers, createUser, deactivateUser, activateUser, updateUser }