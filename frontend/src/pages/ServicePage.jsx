import { getServices } from "../services/serviceApi"
import { deactivateService } from "../services/serviceApi"
import { activateService } from "../services/serviceApi"
import  ServiceForm  from "../components/ServiceForm"
import { useState, useEffect } from "react"

function ServicePage() {
    const [services, setServices] = useState([])
    const [selectedService, setSelectedService] = useState(null)
 
    async function loadServices() {
    const data = await getServices()
    setServices(data)
    console.log(data)
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
        loadServices()
    }
    async function handleServiceCreate(serviceData) {
        await createService(serviceData)
        await loadServices()
    }
   

  return (
    <div>
      <h1>Service Page</h1>   
       <ServiceForm onSubmit={handleServiceCreate} /> 
        {services.map(service => (
          <div key={service.id}>
            <h2>{service.name}</h2>
            <p>Description: {service.description}</p>
            <p>Duration: {service.durationMinutes} minutes</p>
            <p>Price: ${ (service.priceCents / 100).toFixed(2) }</p>
            <p>Status: {service.isActive ? "Active" : "Inactive"}</p>
            <button onClick={() => setSelectedService(service)}>Edit</button>
            <button onClick={() => handleServiceChange(service)}>
              {service.isActive ? "Deactivate" : "Activate"}
            </button>
          </div>
        ))}
        {selectedService && (
          <ServiceForm onSubmit={handleServiceCreate} initialData={selectedService}/>
        )}

    </div>
   
  )
}

export default ServicePage