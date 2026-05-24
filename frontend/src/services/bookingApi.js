const API_URL = "http://localhost:8080/api/bookings"

async function getBookings() {
  const response = await fetch(API_URL)
  return await response.json()
}

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

async function updateBooking(id, bookingData) {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(bookingData),
  })

  if (!response.ok) {
    const errorData = await response.json()
    throw new Error(errorData.message)
  }

  return await response.json()
}
async function cancelBooking(id) {
  const response = await fetch(`${API_URL}/${id}/cancel`, {
    method: "PATCH",
  })

  if (!response.ok) {
    const errorData = await response.json()
    throw new Error(errorData.message)
  }

  return await response.json()
}

async function deleteBooking(id) {
  await fetch(`${API_URL}/${id}`, {
    method: "DELETE",
  })
}

export { getBookings, createBooking, updateBooking, cancelBooking, deleteBooking }