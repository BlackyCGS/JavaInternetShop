import { createContext, useContext, useState, useEffect } from 'react';

type CartContextType = {
    cartCount: number;
    cartItems: number[];
    updateCartCount: () => void;
    updateCartItems: () => void;
};

const CartContext = createContext<CartContextType>({
    cartCount: 0,
    cartItems: [],
    updateCartCount: () => {},
    updateCartItems: () => {},
});

export const CartProvider = ({ children }: { children: React.ReactNode }) => {
    const [cartCount, setCartCount] = useState(0);
    const [cartItems, setCartItems] = useState<number[]>([]);

    const updateCartCount = () => {
        const items = localStorage.getItem('cartItems') || '';
        const count = items.split(',').filter(Boolean).length;
        setCartCount(count);
    };

    const updateCartItems = () => {
        const items = localStorage.getItem('cartItems') || '';
        setCartItems(items.split(',').filter(Boolean).map(Number));
    }

    useEffect(() => {
        updateCartCount();
        updateCartItems();

        const handleStorageChange = () => {
            updateCartCount();
            updateCartItems();
        }
        window.addEventListener('storage', handleStorageChange);

        return () => window.removeEventListener('storage', handleStorageChange);
    }, []);

    return (
        <CartContext.Provider value={{ cartCount, updateCartCount, cartItems, updateCartItems }}> {/*NOSONAR*/}
            {children}
        </CartContext.Provider>
    );
};

export const useCart = () => {
    const context = useContext(CartContext);
    if (!context) {
        throw new Error('useCart must be used within a CartProvider');
    }
    return context;
};