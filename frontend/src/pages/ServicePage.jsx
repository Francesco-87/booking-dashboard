import { getServices } from "../services/serviceApi"
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


  return (
    <div>
      <h1>Service Page</h1>   
       <ServiceForm onServiceCreated={loadServices} />
        {services.map(service => (
          <div key={service.id}>
            <h2>{service.name}</h2>
            <p>{service.description}</p>
          </div>
        ))}

    </div>
   
  )
}

export default ServicePage