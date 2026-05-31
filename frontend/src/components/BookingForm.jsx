import { useState, useEffect } from "react"

// Form component for creating and updating booking appointments
// Handles complex booking logic with customer selection, staff assignment, and datetime management
// Supports both registered customers and guest customer entry
function BookingForm({
  onSubmit,
  initialData = null,
  submitLabel = "Create Booking",
  title = "Create Booking",
  services = [], // Array of available services
  staff = [], // Array of available staff members
  users = [], // Array of all users for customer/creator selection
}) {
  // Template for empty form state (used when creating new bookings)
  const emptyForm = {
    createdByUserId: "",
    customerUserId: "",
    customerName: "",
    customerEmail: "",
    serviceId: "",
    staffProfileId: "",
    startTime: "",
    endTime: "",
    notes: "",
  }

  // State for managing form field values
  const [formData, setFormData] = useState(emptyForm)
  
  // Filter users to only show customers (exclude admin and staff)
  const filteredUsers = users.filter((user) => user.role === "CUSTOMER")
  
  // Get current time in datetime-local format for minimum start time validation
  const now = new Date().toISOString().slice(0, 16)
  

  // Effect to initialize form with existing data or reset to empty form
  useEffect(() => {
    if (initialData) {
      // Populate form with existing booking data (for edit mode)
      // Convert ISO datetime strings to datetime-local format for input element
      setFormData({
        id: initialData.id,
        createdByUserId: initialData.createdByUserId ?? "",
        customerUserId: initialData.customerUserId ?? "",
        customerName: initialData.customerName ?? "",
        customerEmail: initialData.customerEmail ?? "",
        serviceId: initialData.serviceId ?? "",
        staffProfileId: initialData.staffProfileId ?? "",
        startTime: initialData.startTime
          ? new Date(initialData.startTime).toISOString().slice(0, 16)
          : "",
        endTime: initialData.endTime
          ? new Date(initialData.endTime).toISOString().slice(0, 16)
          : "",
        notes: initialData.notes ?? "",
      })
    } else {
      // Reset to empty form when no initial data
      setFormData(emptyForm)
    }
  }, [initialData])

  // Handle form field changes
  function handleChange(e) {
    const { name, value } = e.target

    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }))
  }

  // Handle form submission; converts form data to API payload format and calls parent callback
  async function handleSubmit(e) {
    e.preventDefault()

    // Transform form data to API payload format:
    // - Convert string IDs to numbers
    // - Convert datetime-local to ISO format
    // - Set null values for empty customer fields
    const payload = {
      ...formData,
      createdByUserId: Number(formData.createdByUserId),
      serviceId: Number(formData.serviceId),
      staffProfileId: Number(formData.staffProfileId),
      customerUserId: formData.customerUserId
        ? Number(formData.customerUserId)
        : null,
      customerName: formData.customerName || null,
      customerEmail: formData.customerEmail || null,
      startTime: formData.startTime
        ? new Date(formData.startTime).toISOString()
        : null,
      endTime: formData.endTime
        ? new Date(formData.endTime).toISOString()
        : null,
    }

    // Call parent's onSubmit callback with formatted payload
    await onSubmit(payload)

    // Reset form to empty state only when creating new booking
    if (!initialData) {
      setFormData(emptyForm)
    }
  }


  
  // Render booking form with sections for staff/customer, service, and timing
  return (
    <div className="booking-form-wrapper">
      <h2>{title}</h2>

      <form className="booking-form" onSubmit={handleSubmit}>
        {/* Created By User - who is creating the booking (required) */}
        <div className="form-field">
          <label htmlFor="createdByUserId">Created By</label>
          <select
            id="createdByUserId"
            name="createdByUserId"
            value={formData.createdByUserId}
            onChange={handleChange}
            required
          >
            <option value="">Select a user</option>
            {users.map((user) => (
              <option key={user.id} value={user.id}>
                {user.fullName} (ID: {user.id})
              </option>
            ))}
          </select>
        </div>

        {/* Customer Selection - optional registered customer */}
        <div className="form-field">
          <label htmlFor="customerUserId">Customer </label>
          <select
            id="customerUserId"
            name="customerUserId"
            value={formData.customerUserId}
            onChange={handleChange}
          >
            <option value="">Choose customer</option>
            {filteredUsers.map((user) => (
              <option key={user.id} value={user.id}>
                {user.fullName}
              </option>
            ))}
          </select>
        </div>

        {/* Guest Customer Name - alternative to registered customer */}
        <div className="form-field">
          <label htmlFor="customerName">Guest Customer Name</label>
          <input
            type="text"
            id="customerName"
            name="customerName"
            value={formData.customerName}
            onChange={handleChange}
          />
        </div>

        {/* Guest Customer Email - optional email for guest customers */}
        <div className="form-field">
          <label htmlFor="customerEmail">Guest Customer Email</label>
          <input
            type="email"
            id="customerEmail"
            name="customerEmail"
            value={formData.customerEmail}
            onChange={handleChange}
          />
        </div>

        {/* Service Selection */}
        <div className="form-field">
          <label htmlFor="serviceId">Service</label>
          <select
            id="serviceId"
            name="serviceId"
            value={formData.serviceId}
            onChange={handleChange}
            required
          >
            <option value="">Select a service</option>
            {services.map((service) => (
              <option key={service.id} value={service.id}>
                {service.name}
              </option>
            ))}
          </select>
        </div>

        {/* Staff Selection - who will perform the service */}
        <div className="form-field">
          <label htmlFor="staffProfileId">Performer</label>
          <select
            id="staffProfileId"
            name="staffProfileId"
            value={formData.staffProfileId}
            onChange={handleChange}
            required
          >
            <option value="">Select a performer</option>
            {staff.map((profile) => (
              <option key={profile.id} value={profile.id}>
                {profile.displayName} (ID: {profile.id})
              </option>
            ))}
          </select>
        </div>

        {/* Start Time - minimum is current time to prevent past bookings */}
        <div className="form-field">
          <label htmlFor="startTime">Start Time</label>
          <input
            type="datetime-local"
            min={now}
            id="startTime"
            name="startTime"
            value={formData.startTime}
            onChange={handleChange}
            required
          />
        </div>

        {/* End Time - minimum is start time or current time */}
        <div className="form-field">
          <label htmlFor="endTime">End Time</label>
          <input
            type="datetime-local"
            min={formData.startTime || now}
            id="endTime"
            name="endTime"
            value={formData.endTime}
            onChange={handleChange}
            required
          />
        </div>

        {/* Notes - optional additional information */}
        <div className="form-field">
          <label htmlFor="notes">Notes</label>
          <textarea
            id="notes"
            name="notes"
            value={formData.notes}
            onChange={handleChange}
          />
        </div>

        {/* Submit button */}
        <div className="form-actions">
          <button type="submit" className="btn btn--primary">
            {submitLabel}
          </button>
        </div>
      </form>
    </div>
  )
}

export default BookingForm