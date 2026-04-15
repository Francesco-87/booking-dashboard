import {useState, useEffect} from "react"

function UserForm({ 
    onSubmit, 
    initialData = null, 
    submitLabel = "Create User", 
    title = "Create User" 
}) {
  const emptyForm = {
    fullName: "",
    email: "",
    passwordHash: "",
    role: "",
  }
  const [formData, setFormData] = useState(initialData || emptyForm)

  useEffect(() => {
    if (initialData) {
      setFormData({
        id: initialData.id,
        fullName: initialData.fullName ?? "",
        email: initialData.email ?? "",
        role: initialData.role ?? "",
        isActive: initialData.isActive,
      })
    }else {
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

  return (<div>
      <h2>{title}</h2>

        <form className="user-form" onSubmit={handleSubmit}>
            <div className="form-field">
                <label htmlFor="fullName">Full Name</label>
                <input 
                    type="text" 
                    id="fullName" 
                    name="fullName" 
                    value={formData.fullName} 
                    onChange={handleChange} 
                    required
                    maxLength={100}
                    placeholder="Full Name" 
                />
            </div>
            <div className="form-field">
                <label htmlFor="email">Email</label>
                <input
                    type="email"
                    id="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                    placeholder="Email"
                />
            </div>
            {!initialData && (
            <div className="form-field">
                <label htmlFor="passwordHash">Password</label>
                <input 
                type="password" 
                id="passwordHash" 
                name="passwordHash"
                value={formData.passwordHash}
                onChange={handleChange}
                required
                placeholder="Password"
                />
            </div>
            )}
            <div className="form-field">
                <label htmlFor="role">Role</label>
                <select
                    id="role"
                    name="role"
                    value={formData.role}
                    onChange={handleChange}
                    required
                >
                    <option value="">Select Role</option>
                    <option value="ADMIN">Admin</option>
                    <option value="STAFF">Staff</option>
                </select>
            </div>
            <div className="form-actions">              
            <button type="submit" className="btn btn--primary">{submitLabel}</button>
            </div>
        </form>
      </div>
)
}
export default UserForm