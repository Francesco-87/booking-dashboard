import { useNavigate } from "react-router-dom"


function HomePage() {
    const navigate = useNavigate();

    return (
        <div>
            <h1>Welcome to the Booking Dashboard</h1>
            <p>Use the navigation menu to choose your role.</p>
            <button onClick={() => navigate("/admin")}>Admin</button>
            <button onClick={() => navigate("/customer")}>Customer</button>
        </div>
    )
}

export default HomePage