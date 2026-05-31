const API_URL = "http://localhost:8080/api/bookings"

// Retrieve all bookings from the backend
async function getBookings() {
  const response = await fetch(API_URL)
  return await response.json()
}

// Create a new booking
async function createBooking(bookingData) {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(bookingData),
  })

  return await response.json()
}

// Update an existing booking by ID
async function updateBooking(id, bookingData) {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(bookingData),
  })

  // Surface backend validation messages to the UI
  if (!response.ok) {
    const errorData = await response.json()
    throw new Error(errorData.message)
  }

  return await response.json()
}

// Cancel a booking using the dedicated cancellation endpoint
async function cancelBooking(id) {
  const response = await fetch(`${API_URL}/${id}/cancel`, {
    method: "PATCH",
  })

  // Surface backend validation messages to the UI
  if (!response.ok) {
    const errorData = await response.json()
    throw new Error(errorData.message)
  }

  return await response.json()
}

// Permanently delete a booking
// Currently unused because the application uses cancellation instead
async function deleteBooking(id) {
  await fetch(`${API_URL}/${id}`, {
    method: "DELETE",
  })
}

export {
  getBookings,
  createBooking,
  updateBooking,
  cancelBooking,
  deleteBooking,
}