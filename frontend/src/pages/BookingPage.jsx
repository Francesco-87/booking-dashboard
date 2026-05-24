import { useState, useEffect } from "react"
import {
  getBookings,
  createBooking,
  updateBooking,
  cancelBooking,
} from "../services/bookingApi"
import BookingForm from "../components/BookingForm"
import "../css/BookingPage.css"

function BookingPage() {
  const [bookings, setBookings] = useState([])
  const [selectedBooking, setSelectedBooking] = useState(null)

  async function loadBookings() {
    const data = await getBookings()
    setBookings(data || [])
  }

  useEffect(() => {
    loadBookings()
  }, [])

  async function handleBookingCreate(bookingData) {
    await createBooking(bookingData)
    await loadBookings()
  }

  async function handleBookingUpdate(bookingData) {
     try {
    await updateBooking(bookingData.id, bookingData)
    await loadBookings()
    setSelectedBooking(null)
  } catch (error) {
    alert(error.message)
  }
}
async function handleBookingCancel(id) {
  try {
    await cancelBooking(id)
    await loadBookings()
  } catch (error) {
    alert(error.message)
  }
}

  return (
    <div className="booking-page">
      <div className="booking-page__header">
        <h1>Booking Management</h1>
        <p>View and manage bookings.</p>
      </div>

      <BookingForm
        onSubmit={handleBookingCreate}
        submitLabel="Create Booking"
        title="Create Booking"
      />

      <div className="booking-list">
        {bookings.length === 0 && (
          <p>No bookings found.</p>
        )}

        {bookings.map((booking) => (
          <div key={booking.id} className="booking-card">
            <div className="booking-card__header">
              <h2>
                {booking.customerName || `Customer #${booking.customerUserId}`}
              </h2>
            </div>

            <div className="booking-card__body">
              <p>Service ID: {booking.serviceId}</p>
              <p>Performer ID: {booking.staffProfileId}</p>
              <p>Created by User ID: {booking.createdByUserId}</p>

              {booking.customerEmail && (
                <p>Email: {booking.customerEmail}</p>
              )}

              <p>Start: {new Date(booking.startTime).toLocaleString()}</p>
              <p>End: {new Date(booking.endTime).toLocaleString()}</p>
              <p>Status: {booking.status}</p>

              {booking.notes && (
                <p>Notes: {booking.notes}</p>
              )}
            </div>

            <div className="booking-card__actions">
              <button
                type="button"
                className="btn btn--secondary"
                onClick={() => setSelectedBooking(booking)}
              >
                Edit
              </button>
              <button
                type="button"
                className="btn btn--danger"
                onClick={() => handleBookingCancel(booking.id)}
              >
                Cancel
              </button>
            </div>
          </div>
        ))}
      </div>

      {selectedBooking && (
        <div
          className="booking-edit-modal"
          onClick={() => setSelectedBooking(null)}
        >
          <div
            className="booking-edit-modal__content"
            onClick={(e) => e.stopPropagation()}
          >
            <div className="modal-header">
              <h2>Edit Booking</h2>
              <button
                type="button"
                className="btn btn--secondary"
                onClick={() => setSelectedBooking(null)}
              >
                Close
              </button>
            </div>

            <BookingForm
              initialData={selectedBooking}
              onSubmit={handleBookingUpdate}
              submitLabel="Update Booking"
              title="Edit Booking"
            />
          </div>
        </div>
      )}
    </div>
  )
}

export default BookingPage