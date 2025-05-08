import { createContext, useContext, useEffect, useState } from 'react';
import axios from 'axios';
import { User } from '../types/User';
import { apiLogout } from '../api/AuthApi.ts'

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
    const [loading, setLoading] = useState(true);

    const login = (userData: User) => {
        setUser(userData);
    };

    const logout = async () => {
        await apiLogout();
        setUser(null);
    };

    const refreshUser = async () => {
        try {
            const response = await axios.get<User>('/api/users/profile', {
                withCredentials: true, // Убедитесь, что с запросом отправляются cookies
            });
            console.log('got user data', response.data);
            setUser(response.data);
        } catch (error) {
            console.log('exception caught');

            setUser(null);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (!window.location.pathname.includes('/register') &&
            !window.location.pathname.includes('/login')) {
            refreshUser();
        } else {
            setLoading(false);
        }    }, []);

    return (
        <AuthContext.Provider value={{ user, loading, login, logout, refreshUser }}> {/*NOSONAR*/}
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
