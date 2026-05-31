import { useState, useEffect } from "react"
import {
  getBookings,
  createBooking,
  updateBooking,
  cancelBooking,
} from "../services/bookingApi"
import { getServices } from "../services/serviceApi"
import { getStaff } from "../services/staffApi"
import { getUsers } from "../services/userApi"
import BackButton from "../components/BackButton"
import BookingForm from "../components/BookingForm"
import "../css/BookingPage.css"

function BookingPage() {
  const [bookings, setBookings] = useState([])
  const [selectedBooking, setSelectedBooking] = useState(null)

  // Dropdown data used by the booking form
  const [users, setUsers] = useState([])
  const [services, setServices] = useState([])
  const [staff, setStaff] = useState([])

  // Load all bookings from the backend
  async function loadBookings() {
    const data = await getBookings()
    setBookings(data || [])
  }

  // Load bookings and dropdown data when the page opens
  useEffect(() => {
    loadBookings()
    loadDropdownData()
  }, [])

  // Create a new booking and refresh the booking list
  async function handleBookingCreate(bookingData) {
    await createBooking(bookingData)
    await loadBookings()
  }

  // Update an existing booking and close the edit modal
  async function handleBookingUpdate(bookingData) {
    try {
      await updateBooking(bookingData.id, bookingData)
      await loadBookings()
      setSelectedBooking(null)
    } catch (error) {
      alert(error.message)
    }
  }

  // Cancel a booking through the dedicated cancellation endpoint
  async function handleBookingCancel(id) {
    try {
      await cancelBooking(id)
      await loadBookings()
    } catch (error) {
      alert(error.message)
    }
  }

  // Load lookup data used by booking dropdowns and display names
  async function loadDropdownData() {
    const servicesData = await getServices()
    const staffData = await getStaff()
    const usersData = await getUsers()

    setServices(servicesData)
    setStaff(staffData)
    setUsers(usersData)
  }

  // Convert service IDs into readable service names
  function getServiceName(serviceId) {
    const service = services.find((s) => s.id === serviceId)
    return service ? service.name : `Service #${serviceId}`
  }

  // Convert staff profile IDs into performer names
  function getStaffName(staffId) {
    const staffMember = staff.find((s) => s.id === staffId)
    return staffMember ? staffMember.displayName : `Staff #${staffId}`
  }

  // Convert user IDs into readable user names
  function getUserName(userId) {
    const user = users.find((u) => u.id === userId)
    return user ? user.fullName : `User #${userId}`
  }

  return (
    <div className="booking-page">
      {/* Reusable navigation button */}
      <BackButton />

      {/* Page header */}
      <div className="booking-page__header">
        <h1>Booking Management</h1>
        <p>View and manage bookings.</p>
      </div>

      {/* Booking creation form */}
      <BookingForm
        services={services}
        staff={staff}
        users={users}
        onSubmit={handleBookingCreate}
        submitLabel="Create Booking"
        title="Create Booking"
      />

      {/* Booking list */}
      <div className="booking-list">
        {bookings.length === 0 && (
          <p>No bookings found.</p>
        )}

        {bookings.map((booking) => (
          <div key={booking.id} className="booking-card">
            <div className="booking-card__header">
              <h2>
                {/* Display registered customer name, guest name, or fallback text */}
                {booking.customerUserId
                  ? getUserName(booking.customerUserId)
                  : booking.customerName || "Guest Customer"}
              </h2>
            </div>

            <div className="booking-card__body">
              <p>Service: {getServiceName(booking.serviceId)}</p>
              <p>Performer: {getStaffName(booking.staffProfileId)}</p>
              <p>Created by: {getUserName(booking.createdByUserId)}</p>

              {/* Guest bookings may contain an email address */}
              {booking.customerEmail && (
                <p>Email: {booking.customerEmail}</p>
              )}

              <p>Start: {new Date(booking.startTime).toLocaleString()}</p>
              <p>End: {new Date(booking.endTime).toLocaleString()}</p>
              <p>Status: {booking.status}</p>

              {/* Optional notes attached to the booking */}
              {booking.notes && (
                <p>Notes: {booking.notes}</p>
              )}
            </div>

            {/* Booking actions */}
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

      {/* Edit booking modal */}
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

            {/* Reuse the same form component for booking updates */}
            <BookingForm
              initialData={selectedBooking}
              services={services}
              staff={staff}
              users={users}
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