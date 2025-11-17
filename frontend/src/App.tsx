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
import {CartProvider} from "./hooks/useCart.tsx";
import CpuList from "./pages/CpuList.tsx";
import PcCaseList from './pages/PcCaseList.tsx'
import SsdList from "./pages/SsdList.tsx";
import PsuList from "./pages/PsuList.tsx";
import HddList from "./pages/HddList.tsx";
import RamList from "./pages/RamList.tsx";


function App() {
    return (
        <Router>
            <CartProvider>
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
                        pt: '70px' // Высота NavBar
                    }}>
                        <Routes>
                            <Route path="/" element={<HomePage />} />
                            <Route path="/profile" element={<Profile />} />
                            <Route path="/orders" element={<Orders />} />
                            <Route path="/motherboards" element={<MotherboardList />} />
                            <Route path="/gpus" element={<GpuList />} />
                            <Route path="/cpus" element={<CpuList />} />
                            <Route path="/hdds" element={<HddList />} />
                            <Route path="/ssds" element={<SsdList />} />
                            <Route path="/pcCases" element={<PcCaseList />} />
                            <Route path="/psus" element={<PsuList />} />
                            <Route path="/ram" element={<RamList />} />
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
            </CartProvider>
        </Router>
    );
}


export default App
