
const SERVICE_API_URL = "http://localhost:8080/api/services"

// Retrieve all services from the backend
async function getServices() {
  try {
    const response = await fetch(SERVICE_API_URL)
    return await response.json()
  } catch (error) {
    console.error("Error fetching services:", error)
  }
}

// Create a new service
async function createService(serviceData) {
  try {
    const response = await fetch(SERVICE_API_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(serviceData),
    })

    return await response.json()
  } catch (error) {
    console.error("Error creating service:", error)
  }
}

// Soft deactivate a service while keeping it in the system
async function deactivateService(serviceData) {
  try {
    const response = await fetch(`${SERVICE_API_URL}/${serviceData.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ ...serviceData, isActive: false }),
    })

    return await response.json()
  } catch (error) {
    console.error("Error deactivating service:", error)
  }
}

// Reactivate a previously deactivated service
async function activateService(serviceData) {
  try {
    const response = await fetch(`${SERVICE_API_URL}/${serviceData.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ ...serviceData, isActive: true }),
    })

    return await response.json()
  } catch (error) {
    console.error("Error activating service:", error)
  }
}

// Update an existing service
async function updateService(serviceData) {
  try {
    const response = await fetch(`${SERVICE_API_URL}/${serviceData.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(serviceData),
    })

    return await response.json()
  } catch (error) {
    console.error("Error updating service:", error)
  }
}

export {
  getServices,
  createService,
  deactivateService,
  activateService,
  updateService,
}