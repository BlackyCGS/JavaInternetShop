import React, { useEffect, useState } from 'react';
import { Button, Typography, Box } from '@mui/material';
import { Product } from '../types/Product.ts';
import { getCartById, toOrder, deleteProductFromCart } from "../api/OrdersApi.ts";
import CartCard from "../components/CartCard.tsx";
import {useNavigate} from "react-router-dom";
import {Order} from "../types/Order.ts";
import {useCart} from "../hooks/useCart.tsx";

const Cart = () => {
    const [cartItems, setCartItems] = useState<Product[]>([]);
    const [loading, setLoading] = useState(false)
    const totalPrice = cartItems.reduce((acc, item) => acc + item.price, 0);
    const navigate = useNavigate();
    const {updateCartCount} = useCart()

    const handleToOrder = async () => {
        await toOrder();
        setCartItems([]);
        updateCartCount();
        navigate("/orders")
    };

    const loadProducts = async () => {
        try {
            if(loading) return
            setLoading(true)

            const data: Order = await getCartById();
            setCartItems(data.products);
        } catch (error) {
            console.error('Error loading products:', error);
        }
        finally {
            setLoading(false)
        }
    };

    const handleDeleteProduct = async (productId: number) => {
        try {
            await deleteProductFromCart(productId);
            setCartItems((prevItems) => prevItems.filter(item => item.id !== productId));
        } catch (error) {
            console.error('Error deleting product:', error);
        }
        finally {
            updateCartCount();
        }
    };

    useEffect(() => {
        loadProducts();
    }, []);

    return (
        <div style={{ padding: '20px' }}>
            <Typography variant="h4" gutterBottom>
                Cart
            </Typography>

            {cartItems.length === 0 ? (
                <Typography variant="h6">Your cart is empty.</Typography>
            ) : (
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                    {cartItems.map((product) => (
                        <CartCard key={product.id} product={product} onDelete={handleDeleteProduct} />
                    ))}
                </Box>
            )}

            {cartItems.length > 0 && (
                <div style={{ marginTop: '20px' }}>
                    <Typography variant="h6" gutterBottom>
                        Total: {totalPrice.toFixed(2)} $
                    </Typography>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={() => handleToOrder()}
                        size="large"
                    >
                        Place an order
                    </Button>
                </div>
            )}
        </div>
    );
};

export default Cart;
