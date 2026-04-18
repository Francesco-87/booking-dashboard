import { useState, useEffect } from "react"

function StaffForm({
  onSubmit,
  initialData = null,
  submitLabel = "Create Staff",
  title = "Create Staff Member",
}) {
  const emptyForm = {
    userId: "",
    displayName: "",
  }

  const [formData, setFormData] = useState(emptyForm)

  useEffect(() => {
    if (initialData) {
      setFormData({
        userId: initialData.userId ?? "",
        displayName: initialData.displayName ?? "",
      })
    } else {
      setFormData(emptyForm)
    }
  }, [initialData])

  function handleChange(e) {
    const { name, value, type } = e.target

    setFormData((prev) => ({
      ...prev,
      [name]: type === "number" ? (value === "" ? "" : Number(value)) : value,
    }))
  }

  async function handleSubmit(e) {
    e.preventDefault()
    await onSubmit(formData)

    if (!initialData) {
      setFormData(emptyForm)
    }
  }

  return (
    <div className="staff-form-wrapper">
      <h2>{title}</h2>

      <form className="staff-form" onSubmit={handleSubmit}>
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