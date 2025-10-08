import { createContext, useContext, useEffect, useState } from 'react';
import axios from 'axios';
import { User } from '../types/User';
import { apiLogout } from '../api/AuthApi.ts'
import {useCart} from "./useCart.tsx";
import {getCartById} from "../api/OrdersApi.ts";
import {useLocation} from "react-router-dom";

interface AuthContextType {
    user: User | null;
    loading: boolean;
    login: (userData: User) => void;
    logout: () => void;
    refreshUser: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType>({
    user: null,
    loading: true,
    login: () => {},
    logout: () => {},
    refreshUser: async () => {},
});

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(false);
    const {updateCartCount, updateCartItems} = useCart()
    const { pathname } = useLocation();

    const login = (userData: User) => {
        setUser(userData);
    };

    const logout = async () => {
        await apiLogout();
        setUser(null);
        localStorage.clear();
        updateCartCount();
        updateCartItems();
    };

    const refreshUser = async () => {
        if (loading) return
        try {
            setLoading(true);
            const response = await axios.get<User>('/api/users/profile', {
                withCredentials: true, // Убедитесь, что с запросом отправляются cookies
            });
            await getCartById();
            setUser(response.data);
        } catch (error) {
            console.log('exception caught');

            setUser(null);
            localStorage.clear();
        } finally {
            setLoading(false);
            updateCartCount();
            updateCartItems();
        }
    };

    useEffect(() => {
        if (!pathname.includes('/register') &&
            !pathname.includes('/login')) {
            refreshUser();
        } else {
            setLoading(false);
        }    }, [pathname]);

    return (
        <AuthContext.Provider value={{ user, loading, login, logout, refreshUser }}> {/*NOSONAR*/}
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
