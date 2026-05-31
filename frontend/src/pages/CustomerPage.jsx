import BackButton from "../components/BackButton"

function CustomerPage() {
  return (
    <div>
      {/* Reusable navigation button */}
      <BackButton />

      {/* Placeholder page for future customer-facing functionality */}
      <h1>Customer Page</h1>

      {/* Customers will be able to view and manage their own bookings here */}
      <p>
        This is where customers can access their information and manage their
        bookings.
      </p>
    </div>
  )
}

export default CustomerPage