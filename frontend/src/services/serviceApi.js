
const SERVICE_API_URL = "http://localhost:8080/api/services"


async function getServices() {
  try {
    const response = await fetch(SERVICE_API_URL)
    return await response.json()
  } catch (error) {
    console.error('Error fetching services:', error)
  }
}

async function createService(serviceData) {
  try {
    const response = await fetch(SERVICE_API_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(serviceData)
    })
    return await response.json()
  } catch (error) {
    console.error('Error creating service:', error)
  }
}

async function deactivateService(serviceData) {
  try {
    const response = await fetch(`${SERVICE_API_URL}/${serviceData.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ ...serviceData, isActive: false })
    })
    return await response.json()
  } catch (error) {
    console.error('Error deactivating service:', error)
  }
}

async function activateService(serviceData) {
  try {
    const response = await fetch(`${SERVICE_API_URL}/${serviceData.id}`, {
    
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ ...serviceData, isActive: true })
    })
    return await response.json()
  } catch (error) {
    console.error('Error activating service:', error)
  }
}

async function updateService(serviceData) {
  try {
    const response = await fetch(`${SERVICE_API_URL}/${serviceData.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(serviceData)
    })
    return await response.json()
  } catch (error) {
    console.error('Error updating service:', error)
  }
}

export { getServices, createService, deactivateService, activateService, updateService }