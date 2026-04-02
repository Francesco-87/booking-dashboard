import { getServices } from "../services/serviceApi"
import { deactivateService } from "../services/serviceApi"
import  ServiceForm  from "../components/ServiceForm"
import { useState, useEffect } from "react"

function ServicePage() {
    const [services, setServices] = useState([])
 
    async function loadServices() {
    const data = await getServices()
    setServices(data)
    console.log(data)
    }
    useEffect(() => {
        loadServices()
    }, [])

    async function handleDeactivate(serviceData) {
        await deactivateService(serviceData)
        loadServices()
    }
   

  return (
    <div>
      <h1>Service Page</h1>   
       <ServiceForm onServiceCreated={loadServices} />
        {services.map(service => (
          <div key={service.id}>
            <h2>{service.name}</h2>
            <p>Description: {service.description}</p>
            <p>Duration: {service.durationMinutes} minutes</p>
            <p>Price: ${ (service.priceCents / 100).toFixed(2) }</p>
            <button onClick={() => handleDeactivate(service)}>Deactivate</button>
          </div>
        ))}

    </div>
   
  )
}

export default ServicePage