import { useNavigate } from "react-router-dom"

function BackButton() {
  const navigate = useNavigate()

 return (
  <div className="back-button">
     <button
        type="button"
        className="btn btn--secondary back-button"
        onClick={() => navigate(-1)}
        >
        ← Back
    </button>
    </div>
)
}
export default BackButton