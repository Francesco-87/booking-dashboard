import AdminPage from "./pages/AdminPage"
import CustomerPage from "./pages/CustomerPage"
import ServicePage from "./pages/ServicePage"
import HomePage from "./pages/HomePage"
import { BrowserRouter, Routes, Route } from "react-router-dom"


function App() {
  return (
    <BrowserRouter>
      <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/admin" element={<AdminPage />} />
          <Route path="/admin/services" element={<ServicePage />} />
          <Route path="/customer" element={<CustomerPage />} />
          
      </Routes>
    </BrowserRouter>
  )
}

export default App