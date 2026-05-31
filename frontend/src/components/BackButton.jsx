import { useNavigate } from "react-router-dom"

// Simple navigation button component that navigates back to the previous page
// Uses React Router's useNavigate hook to handle browser back button functionality
function BackButton() {
  // Get navigate function from React Router
  const navigate = useNavigate()

  // Render a back button that navigates to previous page when clicked
  return (
    <div className="back-button">
      <button
        type="button"
        className="btn btn--secondary back-button"
        onClick={() => navigate(-1)} // Navigate back one page in history
      >
        ← Back
      </button>
    </div>
  )
}
export default BackButton