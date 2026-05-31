import { useState, useEffect } from "react"

// Form component for creating and updating staff member profiles
// Handles staff data input with display name and user ID association
function StaffForm({
  onSubmit,
  initialData = null,
  submitLabel = "Create Staff",
  title = "Create Staff Member",
}) {
  // Template for empty form state (used when creating new staff profiles)
  const emptyForm = {
    userId: "",
    displayName: "",
  }

  // State for managing form field values
  const [formData, setFormData] = useState(emptyForm)

  // Effect to initialize form with existing data or reset to empty form
  useEffect(() => {
    if (initialData) {
      // Populate form with existing staff profile data (for edit mode)
      setFormData({
        userId: initialData.userId ?? "",
        displayName: initialData.displayName ?? "",
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

  // Handle form submission; calls parent callback and resets form if creating new staff
  async function handleSubmit(e) {
    e.preventDefault()
    // Call parent's onSubmit callback with form data
    await onSubmit(formData)

    // Reset form to empty state only when creating new staff profile
    if (!initialData) {
      setFormData(emptyForm)
    }
  }

  return (
    <div className="staff-form-wrapper">
      <h2>{title}</h2>

      <form className="staff-form" onSubmit={handleSubmit}>
        {/* Display Name field */}
        <div className="form-field">
          <label htmlFor="displayName">Display Name</label>
          <input
            type="text"
            id="displayName"
            name="displayName"
            value={formData.displayName}
            onChange={handleChange}
            required
            maxLength={100}
            placeholder="e.g. Frank"
          />
        </div>

        {/* User ID field - numeric reference to user account */}
        <div className="form-field">
          <label htmlFor="userId">User ID</label>
          <input
            type="number"
            id="userId"
            name="userId"
            value={formData.userId}
            onChange={handleChange}
            required
            placeholder="e.g. 1"
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

export default StaffForm