import { useState, useEffect } from "react"

// Form component for creating and updating service offerings
// Handles service details including name, description, duration, and pricing
function ServiceForm({
  onSubmit,
  initialData = null,
  submitLabel = "Create Service",
  title = "Create Service",
}) {
  // Template for empty form state (used when creating new services)
  const emptyForm = {
    name: "",
    description: "",
    durationMinutes: "",
    priceCents: "",
  }

  // State for managing form field values
  const [formData, setFormData] = useState(emptyForm)

  // Effect to initialize form with existing data or reset to empty form
  useEffect(() => {
    if (initialData) {
      // Populate form with existing service data (for edit mode)
      setFormData({
        id: initialData.id,
        name: initialData.name ?? "",
        description: initialData.description ?? "",
        durationMinutes: initialData.durationMinutes ?? "",
        priceCents: initialData.priceCents ?? "",
        isActive: initialData.isActive,
      })
    } else {
      // Reset to empty form when no initial data
      setFormData(emptyForm)
    }
  }, [initialData])

  // Handle form field changes; converts number inputs to Number type
  function handleChange(e) {
    const { name, value, type } = e.target

    setFormData((prev) => ({
      ...prev,
      [name]: type === "number" ? (value === "" ? "" : Number(value)) : value,
    }))
  }

  // Handle form submission; calls parent callback and resets form if creating new service
  async function handleSubmit(e) {
    e.preventDefault()
    // Call parent's onSubmit callback with form data
    await onSubmit(formData)

    // Reset form to empty state only when creating new service
    if (!initialData) {
      setFormData(emptyForm)
    }
  }

  return (
    <div className="service-form-wrapper">
      <h2>{title}</h2>

      <form className="service-form" onSubmit={handleSubmit}>
        {/* Service Name field */}
        <div className="form-field">
          <label htmlFor="name">Service Name</label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
            maxLength={100}
            placeholder="e.g. Consultation"
          />
        </div>

        {/* Service Description field */}
        <div className="form-field">
          <label htmlFor="description">Description</label>
          <textarea
            id="description"
            name="description"
            value={formData.description}
            onChange={handleChange}
            rows="3"
            placeholder="Short description of the service"
          />
        </div>

        {/* Duration and Price fields in a row layout */}
        <div className="form-row">
          {/* Duration field - minutes required */}
          <div className="form-field">
            <label htmlFor="durationMinutes">Duration (minutes)</label>
            <input
              type="number"
              id="durationMinutes"
              name="durationMinutes"
              value={formData.durationMinutes}
              onChange={handleChange}
              required
              min="1"
              step="1"
              placeholder="60"
            />
          </div>

          {/* Price field - in cents to avoid floating point issues */}
          <div className="form-field">
            <label htmlFor="priceCents">Price (cents)</label>
            <input
              type="number"
              id="priceCents"
              name="priceCents"
              value={formData.priceCents}
              onChange={handleChange}
              required
              min="0"
              step="1"
              placeholder="5000"
            />
          </div>
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

export default ServiceForm