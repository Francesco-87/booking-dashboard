import { useState, useEffect } from "react"

function ServiceForm({
  onSubmit,
  initialData = null,
  submitLabel = "Create Service",
  title = "Create Service",
}) {
  const emptyForm = {
    name: "",
    description: "",
    durationMinutes: "",
    priceCents: "",
  }

  const [formData, setFormData] = useState(emptyForm)

  useEffect(() => {
    if (initialData) {
      setFormData({
        id: initialData.id,
        name: initialData.name ?? "",
        description: initialData.description ?? "",
        durationMinutes: initialData.durationMinutes ?? "",
        priceCents: initialData.priceCents ?? "",
        isActive: initialData.isActive,
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
    <div className="service-form-wrapper">
      <h2>{title}</h2>

      <form className="service-form" onSubmit={handleSubmit}>
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

        <div className="form-row">
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