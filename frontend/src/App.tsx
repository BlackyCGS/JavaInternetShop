import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Profile from './pages/Profile'
import Orders from './pages/Orders'
import MotherboardList from './pages/MotherboardList'
import GpuList from './pages/GpuList'
import HomePage from "./pages/HomePage.tsx";
import ProductPage from "./pages/ProductPage.tsx";
import NavBar from "./components/NavBar.tsx";
import Footer from "./components/Footer";
import Login from "./pages/Login.tsx";
import AdminTab from "./pages/AdminTab";
import {Box, CssBaseline} from "@mui/material";
import {AuthProvider} from "./hooks/useAuth.tsx";
import Cart from "./pages/Cart.tsx";
import Register from "./pages/Register.tsx";

function App() {
    return (
        <Router>
            <AuthProvider>
                <Box sx={{
                    minHeight: '100vh',
                    display: 'flex',
                    flexDirection: 'column',
                    backgroundColor: 'white'
                }}>
                    <CssBaseline />
                    <NavBar />

                    <Box component="main" sx={{
                        flex: 1,
                        pt: '64px' // Высота NavBar
                    }}>
                        <Routes>
                            <Route path="/" element={<HomePage />} />
                            <Route path="/profile" element={<Profile />} />
                            <Route path="/orders" element={<Orders />} />
                            <Route path="/motherboards" element={<MotherboardList />} />
                            <Route path="/gpus" element={<GpuList />} />
                            <Route path="/products/:id" element={<ProductPage />} />
                            <Route path="/login" element={<Login />} />
                            <Route path="/register" element={<Register/>} />
                            <Route path="/admin" element={<AdminTab />} />
                            <Route path="/cart" element={<Cart/>} />
                        </Routes>
                    </Box>

                    <Footer />
                </Box>
            </AuthProvider>
        </Router>
    );
}


export default App
