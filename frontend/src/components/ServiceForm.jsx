import { useEffect, useState } from "react"
 

function ServiceForm({ onSubmit, initialData }) {

    const [formData, setFormData] = useState({
        name: '',
        description: '',
        durationMinutes: '',
        priceCents: ''
    });

    useEffect(() => {
        if (initialData) {
            setFormData(initialData)
        }
    }, [initialData])

   async function handleSubmit(e) {
    e.preventDefault()
    console.log(formData)
    const result = await onSubmit(formData)
    console.log(result)
    

    setFormData({
        name: '',
        description: '',
        durationMinutes: '',
        priceCents: ''
    })
}
    


  return (
    <div>
      <h2>Service Form</h2>
      <div className="form-group">
        <form onSubmit={handleSubmit}>
            <div>
                <label htmlFor="name">Name:</label>
                <input type="text" id="name" name="name" value={formData.name} onChange={(e) => setFormData({...formData, name: e.target.value})} />
            </div>
            <div>
                <label htmlFor="description">Description:</label>
                <input type="text" id="description" name="description" value={formData.description} onChange={(e) => setFormData({...formData, description: e.target.value})} />
            </div>
            <div>
                <label htmlFor="durationMinutes">Duration (minutes):</label>
                <input type="number" id="durationMinutes" name="durationMinutes" value={formData.durationMinutes} onChange={(e) => setFormData({...formData, durationMinutes: Number(e.target.value)})} />
            </div>
            <div>
                <label htmlFor="priceCents">priceCents:</label>
                <input type="number" id="priceCents" name="priceCents" value={formData.priceCents} onChange={(e) => setFormData({...formData, priceCents: Number(e.target.value)})} />
            </div>
            
            <button type="submit" >Submit</button>
        </form>
      </div>
    </div>
  )
}

export default ServiceForm 