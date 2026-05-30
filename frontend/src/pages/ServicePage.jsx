import { useState, useEffect } from "react"
import {
  getServices,
  createService,
  updateService,
  deactivateService,
  activateService,
} from "../services/serviceApi"
import ServiceForm from "../components/ServiceForm"
import BackButton from "../components/BackButton"
import "../css/ServicePage.css"

function ServicePage() {
  const [services, setServices] = useState([])
  const [selectedService, setSelectedService] = useState(null)

  async function loadServices() {
    const data = await getServices()
    setServices(data)
  }

  useEffect(() => {
    loadServices()
  }, [])

  async function handleServiceChange(serviceData) {
    if (serviceData.isActive) {
      await deactivateService(serviceData)
    } else {
      await activateService(serviceData)
    }

    await loadServices()
  }

  async function handleServiceCreate(serviceData) {
    await createService(serviceData)
    await loadServices()
  }

  async function handleServiceUpdate(serviceData) {
    await updateService(serviceData)
    await loadServices()
    setSelectedService(null)
  }

  return (
    <div className="service-page">
      <BackButton />
      <div className="service-page__header">
        <h1>Service Management</h1>
        <p>Create, edit, activate, and deactivate services.</p>
      </div>

      <div className="service-page__create">
        <ServiceForm onSubmit={handleServiceCreate} />
      </div>

      <div className="service-list">
        {services.map((service) => (
          <div key={service.id} className="service-card">
            <div className="service-card__header">
              <h2>{service.name}</h2>
              <span
                className={
                  service.isActive
                    ? "service-status service-status--active"
                    : "service-status service-status--inactive"
                }
              >
                {service.isActive ? "Active" : "Inactive"}
              </span>
            </div>

            <div className="service-card__body">
              <p>
                <strong>Description:</strong> {service.description || "—"}
              </p>
              <p>
                <strong>Duration:</strong> {service.durationMinutes} minutes
              </p>
              <p>
                <strong>Price:</strong> ${(service.priceCents / 100).toFixed(2)}
              </p>
            </div>

            <div className="service-card__actions">
              <button
                type="button"
                className="btn btn--secondary"
                onClick={() => setSelectedService(service)}
                >
                Edit
              </button>

              <button
                type="button"
                className={service.isActive ? "btn btn--danger" : "btn btn--success"}
                onClick={() => handleServiceChange(service)}
              >
                {service.isActive ? "Deactivate" : "Activate"}
              </button>
            </div>
          </div>
        ))}
      </div>

      {selectedService && (
        <div className="modal-overlay" onClick={() => setSelectedService(null)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h2>Edit Service</h2>
              <button
                type="button"
                className="btn btn--secondary"
                onClick={() => setSelectedService(null)}
              >
                Close
              </button>
            </div>

            <ServiceForm
              onSubmit={handleServiceUpdate}
              initialData={selectedService}
              submitLabel="Update Service"
              title="Update Service"
            />
          </div>
        </div>
      )}
    </div>
  )
}

export default ServicePage
