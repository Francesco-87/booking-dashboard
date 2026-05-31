import {useState, useEffect} from "react"

// Form component for creating and updating user accounts
// Handles user data input with conditional password field (only shown during creation, not updates)
function UserForm({ 
    onSubmit, 
    initialData = null, 
    submitLabel = "Create User", 
    title = "Create User" 
}) {
  // Template for empty form state (used when creating new users)
  const emptyForm = {
    fullName: "",
    email: "",
    passwordHash: "",
    role: "",
  }
  
  // State for managing form field values
  const [formData, setFormData] = useState(initialData || emptyForm)

  // Effect to initialize form with existing data or reset to empty form
  useEffect(() => {
    if (initialData) {
      // Populate form with existing user data (for edit mode)
      setFormData({
        id: initialData.id,
        fullName: initialData.fullName ?? "",
        email: initialData.email ?? "",
        role: initialData.role ?? "",
        isActive: initialData.isActive,
      })
    }else {
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

  // Handle form submission; calls parent callback and resets form if creating new user
  async function handleSubmit(e) {
    e.preventDefault()
    // Call parent's onSubmit callback with form data
    await onSubmit(formData)
    // Reset form to empty state only when creating new user (not during updates)
    if (!initialData) {
      setFormData(emptyForm)
    }
  }

  return (
    <div className="user-form-wrapper">
      <h2>{title}</h2>

      <form className="user-form" onSubmit={handleSubmit}>
        {/* Full Name field */}
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

        {/* Email field */}
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

        {/* Password field - only shown when creating new user, hidden on updates */}
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

        {/* Role dropdown */}
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
            <option value="CUSTOMER">Customer</option>
          </select>
        </div>

        {/* Submit button */}
        <div className="form-actions">              
          <button type="submit" className="btn btn--primary">{submitLabel}</button>
        </div>
      </form>
    </div>
  )
}
export default UserForm