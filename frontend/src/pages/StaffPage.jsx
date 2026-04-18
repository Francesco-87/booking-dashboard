import { useState, useEffect } from "react"
import {
    getStaff,
    createStaff,
    deactivateStaff,
    activateStaff,
    updateStaff
} from "../services/staffApi"
import StaffForm from "../components/StaffForm"
import "../css/StaffPage.css"



function StaffPage() {
    const [staff, setStaff] = useState([])
    const [selectedStaff, setSelectedStaff] = useState(null)

    async function loadStaff() {
        const data = await getStaff()
        setStaff(data)
    }

    useEffect(() => {
        loadStaff()
    }, [])

    async function handleStaffCreate(staffData) {
      await createStaff(staffData)
      await loadStaff()
  }

      async function handleStaffChange(staffData) {
    if (staffData.isActive) {
      await deactivateStaff(staffData)
    } else {
      await activateStaff(staffData)
    }

    await loadStaff()
  }

  

  async function handleStaffUpdate(staffData) {
    await updateStaff(staffData)
    await loadStaff()
    setSelectedStaff(null)
  }


    // Component code here
    return (
    <div className="staff-page">
      <div className="staff-page__header">
        <h1>Staff Management</h1>
        <p>Create, edit, activate, and deactivate staff members.</p>
      </div>
    

    <div className="staff-page__create">
        <StaffForm onSubmit={handleStaffCreate} />
      </div>
    

       <div className="staff-list">
        {staff.map((member) => (
          <div key={member.id} className="staff-card">
            <div className="staff-card__header">
              <h2>{member.displayName}</h2>
              <span
                className={
                  member.isActive
                    ? "staff-status staff-status--active"
                    : "staff-status staff-status--inactive"
                }
              >
                {member.isActive ? "Active" : "Inactive"}
              </span>
            </div>

            <div className="staff-card__body">
              <p>
                <strong>User Id:</strong> {member.userId}
              </p>
              <p>
                <strong>Description:</strong> {member.description || "—"}
              </p>
              
              
            </div>

            <div className="staff-card__actions">
              <button
                type="button"
                className="btn btn--secondary"
                onClick={() => setSelectedStaff(member)}
              >
                Edit
              </button>

              <button
                type="button"
                className={member.isActive ? "btn btn--danger" : "btn btn--success"}
                onClick={() => handleStaffChange(member)}
              >
                {member.isActive ? "Deactivate" : "Activate"}
              </button>
            </div>
          </div>
        ))}
      </div> 

      {selectedStaff && (
        <div className="staff-edit-modal" onClick={() => setSelectedStaff(null)}>
          <div className="staff-edit-modal__content" onClick={(e) => e.stopPropagation()}>  
            <div className="modal-header">
              <h2>Edit Staff</h2>
              <button
                type="button"
                className="btn btn--secondary"
                onClick={() => setSelectedStaff(null)}
              >
                Close
              </button>
            </div>
            <StaffForm
              onSubmit={handleStaffUpdate}
              initialData={selectedStaff}
              submitLabel="Update Staff"
              title="Edit Staff"
            />

          </div>
        </div>
      )}

    </div>
  )
}

export default StaffPage 