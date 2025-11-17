// src/components/ProductCard.tsx
import { Box, Typography, Chip, Link, Button, Rating } from '@mui/material';
import { Product } from '../types/Product';
import { useTheme } from '@mui/material/styles';
import { addProductToCart } from '../api/OrdersApi';
import { useAuth } from '../hooks/useAuth';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../hooks/useCart';
import { useEffect, useState } from 'react';

const ProductCard = ({ product }: { product: Product }) => {
    const theme = useTheme();
    const { id, name, price, rating, ratingAmount, gpu, motherboard, cpu, hdd, pcCase, psu, ram, ssd, stock } = product;
    const { user } = useAuth();
    const navigate = useNavigate();
    const { updateCartCount, updateCartItems, cartItems } = useCart();
    const [isInCart, setIsInCart] = useState(false);

    const handleAddToCart = async (productId: number) => {
        await addProductToCart(productId);
        updateCartCount();
        updateCartItems();
        setIsInCart(true);
    };

    useEffect(() => {
        if (cartItems.includes(id)) {
            setIsInCart(true);
        }
    }, [cartItems, id]);

    return (
        <Box
            sx={{
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                p: 3,
                mb: 2,
                borderRadius: 2,
                transition: 'all 0.2s',
                '&:hover': {
                    backgroundColor: theme.palette.action.hover,
                    transform: 'translateY(-2px)',
                },
            }}
        >
            <Box sx={{ flex: 1 }}>
                <Link
                    href={`/products/${id}`}
                    underline="none"
                    color="text.primary"
                    sx={{ fontSize: '1.1rem', fontWeight: 500, '&:hover': { color: theme.palette.primary.main } }}
                >
                    {name}
                </Link>
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mt: 1 }}>
                    <Rating value={rating} readOnly precision={0.5} size="small" />
                    <Typography variant="caption" color="text.secondary">
                        ({ratingAmount} reviews)
                    </Typography>
                </Box>
                {/* Display stock */}
                <Typography
                    variant="body2"
                    sx={{ mt: 1, color: stock > 0 ? 'text.primary' : 'error.main' }}
                >
                    Stock: {stock > 0 ? stock : 'Out of stock'}
                </Typography>
                <Box sx={{ display: 'flex', gap: 1, mt: 1.5, flexWrap: 'wrap' }}>
                    {gpu && (
                        <>
                            <Chip label={gpu.producer} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                            <Chip label={`${gpu.vram}GB VRAM`} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                        </>
                    )}
                    {motherboard && (
                        <>
                            <Chip label={motherboard.socket} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                            <Chip label={motherboard.chipset} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                        </>
                    )}
                    {cpu && (
                        <>
                            <Chip label={cpu.socket} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                            <Chip label={`${cpu.cores} cores`} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                        </>
                    )}
                    {hdd && (
                        <>
                            <Chip label={`${hdd.size}TB`} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                        </>
                    )}
                    {pcCase && (
                        <>
                            <Chip label={pcCase.motherboard} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                            <Chip label={pcCase.powerSupply} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                        </>
                    )}
                    {psu && (
                        <>
                            <Chip label={`${psu.watt}W`} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                            <Chip label={psu.size} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                            <Chip label={psu.efficiencyRating} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                        </>
                    )}
                    {ram && (
                        <>
                            <Chip label={`${ram.size}GB`} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                            <Chip label={ram.ramType} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                        </>
                    )}
                    {ssd && (
                        <>
                            <Chip label={`${ssd.size}GB`} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                            <Chip label={ssd.formFactor} size="small" sx={{ backgroundColor: theme.palette.grey[200] }} />
                        </>
                    )}
                </Box>
            </Box>
            <Box sx={{ display: 'flex', alignItems: 'center', gap: 3 }}>
                <Typography variant="h6" sx={{ fontWeight: 600, color: theme.palette.primary.main }}>
                    ${price.toLocaleString()}
                </Typography>
                {user ? (
                    <Button
                        variant="contained"
                        size="small"
                        sx={{ textTransform: 'none', borderRadius: 2 }}
                        onClick={() => handleAddToCart(product.id)}
                        disabled={isInCart || stock === 0} // Disable if in cart or out of stock
                    >
                        {isInCart ? 'Already in Cart' : stock === 0 ? 'Out of Stock' : 'Add to Cart'}
                    </Button>
                ) : (
                    <Button
                        variant="contained"
                        size="small"
                        sx={{ textTransform: 'none', borderRadius: 2 }}
                        onClick={() => navigate('/login')}
                    >
                        Log in to add Products to cart
                    </Button>
                )}
            </Box>
        </Box>
    );
};

export default ProductCard;