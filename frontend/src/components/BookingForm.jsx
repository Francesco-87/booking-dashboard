import { useState, useEffect } from "react"

function BookingForm({
  onSubmit,
  initialData = null,
  submitLabel = "Create Booking",
  title = "Create Booking",
  services,
  staff,
  users,
}) {
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

  const [formData, setFormData] = useState(emptyForm)

  useEffect(() => {
    if (initialData) {
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
      setFormData(emptyForm)
    }
  }, [initialData])

  function handleChange(e) {
    const { name, value } = e.target

    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }))
  }

  async function handleSubmit(e) {
    e.preventDefault()

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

    await onSubmit(payload)

    if (!initialData) {
      setFormData(emptyForm)
    }
  }



  return (
    <div className="booking-form-wrapper">
      <h2>{title}</h2>

      <form className="booking-form" onSubmit={handleSubmit}>
        <div className="form-field">
          <label htmlFor="createdByUserId">Created By User ID</label>
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

        <div className="form-field">
          <label htmlFor="customerUserId">Customer User ID</label>
          <select
            id="customerUserId"
            name="customerUserId"
            value={formData.customerUserId}
            onChange={handleChange}
          >
            <option value="">Select a customer</option>
            {users.map((user) => (
              <option key={user.id} value={user.id}>
                {user.fullName}
              </option>
            ))}
          </select>
        </div>

        <div className="form-field">
          <label htmlFor="customerName">Customer Name</label>
          <input
            type="text"
            id="customerName"
            name="customerName"
            value={formData.customerName}
            onChange={handleChange}
          />
        </div>

        <div className="form-field">
          <label htmlFor="customerEmail">Customer Email</label>
          <input
            type="email"
            id="customerEmail"
            name="customerEmail"
            value={formData.customerEmail}
            onChange={handleChange}
          />
        </div>

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

        <div className="form-field">
          <label htmlFor="staffProfileId">Staff Profile ID</label>
          <select
            id="staffProfileId"
            name="staffProfileId"
            value={formData.staffProfileId}
            onChange={handleChange}
            required
          >
            <option value="">Select a staff profile</option>
            {staff.map((profile) => (
              <option key={profile.id} value={profile.id}>
                {profile.displayName} (ID: {profile.id})
              </option>
            ))}
          </select>
        </div>

        <div className="form-field">
          <label htmlFor="startTime">Start Time</label>
          <input
            type="datetime-local"
            id="startTime"
            name="startTime"
            value={formData.startTime}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-field">
          <label htmlFor="endTime">End Time</label>
          <input
            type="datetime-local"
            id="endTime"
            name="endTime"
            value={formData.endTime}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-field">
          <label htmlFor="notes">Notes</label>
          <textarea
            id="notes"
            name="notes"
            value={formData.notes}
            onChange={handleChange}
          />
        </div>

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