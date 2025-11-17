// src/pages/Cart.tsx
import React, { useEffect, useState } from 'react';
import { Button, Typography, Box, Snackbar, Alert } from '@mui/material';
import { getCartById, toOrder, deleteProductFromCart, changeCartQuantity } from '../api/OrdersApi';
import CartCard from '../components/CartCard';
import { useNavigate } from 'react-router-dom';
import { Order } from '../types/Order';
import { useCart } from '../hooks/useCart';
import { ProductItem } from '../types/ProductItem';

const Cart = () => {
    const [cartItems, setCartItems] = useState<ProductItem[]>([]);
    const [loading, setLoading] = useState(false);
    const [snackbar, setSnackbar] = useState<{
        open: boolean;
        message: string;
        severity: 'success' | 'error';
    }>({ open: false, message: '', severity: 'success' });
    const totalPrice = cartItems.reduce((acc, item) => acc + item.product.price * item.quantity, 0);
    const navigate = useNavigate();
    const { updateCartCount } = useCart();

    // Check if any item has insufficient stock
    const hasInsufficientStock = cartItems.some((item) => item.quantity > item.product.stock);

    const handleToOrder = async () => {
        // Check if any item's quantity exceeds stock
        const outOfStockItems = cartItems.filter((item) => item.quantity > item.product.stock);
        if (outOfStockItems.length > 0) {
            const message = outOfStockItems
                .map((item) => `${item.product.name} (Requested: ${item.quantity}, In stock: ${item.product.stock})`)
                .join(', ');
            setSnackbar({
                open: true,
                message: `Cannot place order. Insufficient stock for: ${message}`,
                severity: 'error',
            });
            return;
        }

        try {
            await toOrder();
            setCartItems([]);
            updateCartCount();
            navigate('/orders');
            setSnackbar({ open: true, message: 'Order placed successfully', severity: 'success' });
        } catch (error: any) {
            console.error('Error placing order:', error);
            setSnackbar({
                open: true,
                message: error.response?.data?.message || 'Failed to place order',
                severity: 'error',
            });
        }
    };

    const handleQuantityChange = async (id: number, newQuantity: number) => {
        try {
            const data: Order = await changeCartQuantity(id, newQuantity);
            setCartItems(data.products);
        } catch (error: any) {
            console.error('Error changing quantity:', error);
            setSnackbar({
                open: true,
                message: error.response?.data?.message || 'Failed to change quantity',
                severity: 'error',
            });
        }
    };

    const loadProducts = async () => {
        try {
            if (loading) return;
            setLoading(true);
            const data: Order = await getCartById();
            setCartItems(data.products);
        } catch (error) {
            console.error('Error loading products:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleDeleteProduct = async (productId: number) => {
        try {
            await deleteProductFromCart(productId);
            setCartItems((prevItems) => prevItems.filter((item) => item.product.id !== productId));
            updateCartCount();
        } catch (error) {
            console.error('Error deleting product:', error);
            setSnackbar({
                open: true,
                message: 'Failed to delete product',
                severity: 'error',
            });
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
                    {cartItems.map((item) => (
                        <CartCard
                            key={item.product.id}
                            product={item}
                            onDelete={handleDeleteProduct}
                            onQuantityChange={handleQuantityChange}
                            // Pass flag to indicate insufficient stock
                            hasInsufficientStock={item.quantity > item.product.stock}
                        />
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
                        onClick={handleToOrder}
                        size="large"
                        disabled={hasInsufficientStock} // Disable if any item has insufficient stock
                    >
                        Place an order
                    </Button>
                </div>
            )}
            <Snackbar
                open={snackbar.open}
                autoHideDuration={6000}
                onClose={() => setSnackbar({ ...snackbar, open: false })}
            >
                <Alert
                    onClose={() => setSnackbar({ ...snackbar, open: false })}
                    severity={snackbar.severity}
                    sx={{ width: '100%' }}
                >
                    {snackbar.message}
                </Alert>
            </Snackbar>
        </div>
    );
};

export default Cart;