// src/components/CartCard.tsx
import { Box, Typography, Chip, Link, Button, TextField } from '@mui/material';
import { useTheme } from '@mui/material/styles';
import { ProductItem } from '../types/ProductItem.ts';
import { useState, useEffect } from 'react';

interface CartCardProps {
    product: ProductItem;
    onDelete: (productId: number) => void;
    onQuantityChange: (productId: number, newQuantity: number) => void;
    hasInsufficientStock?: boolean; // Indicates if quantity exceeds stock
}

const CartCard = ({ product, onDelete, onQuantityChange, hasInsufficientStock }: CartCardProps) => {
    const theme = useTheme();
    const { id, name, gpu, motherboard, price, stock } = product.product;
    const [quantity, setQuantity] = useState(product.quantity);

    // Sync local quantity state with product.quantity when it changes
    useEffect(() => {
        setQuantity(product.quantity);
    }, [product.quantity]);

    const handleDeletion = async () => {
        onDelete(id);
    };

    const handleQuantityChange = async (newQuantity: number) => {
        if (newQuantity >= 1 && newQuantity <= stock) {
            setQuantity(newQuantity);
            onQuantityChange(id, newQuantity);
        }
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value = event.target.value;
        const newQuantity = parseInt(value, 10);
        if (value === '' || isNaN(newQuantity)) {
            // Allow temporary empty input for UX
            setQuantity('' as any); // TypeScript workaround; we'll reset on blur
        } else {
            setQuantity(newQuantity);
        }
    };

    const handleInputBlur = () => {
        const newQuantity = parseInt(quantity as any, 10);
        if (isNaN(newQuantity) || newQuantity < 1) {
            setQuantity(product.quantity); // Reset to last valid quantity
        } else if (newQuantity > stock) {
            setQuantity(stock); // Cap at stock
            onQuantityChange(id, stock);
        } else if (newQuantity !== product.quantity) {
            handleQuantityChange(newQuantity);
        } else {
            setQuantity(product.quantity); // Ensure UI reflects props
        }
    };

    const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            const newQuantity = parseInt(quantity as any, 10);
            if (isNaN(newQuantity) || newQuantity < 1) {
                setQuantity(product.quantity); // Reset to last valid quantity
            } else if (newQuantity > stock) {
                setQuantity(stock); // Cap at stock
                onQuantityChange(id, stock);
            } else if (newQuantity !== product.quantity) {
                handleQuantityChange(newQuantity);
            }
        }
    };

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
                <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
                    Unit Price: ${price.toLocaleString()}
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
                    Stock: {stock}
                </Typography>
                {hasInsufficientStock && (
                    <Typography variant="body2" color="error.main" sx={{ mt: 1 }}>
                        Insufficient stock: Only {stock} available, but {quantity} requested.
                    </Typography>
                )}
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
                </Box>
            </Box>
            <Box sx={{ display: 'flex', alignItems: 'center', gap: 3 }}>
                <Typography variant="h6" sx={{ fontWeight: 600, color: theme.palette.primary.main }}>
                    ${(price * product.quantity).toLocaleString()}
                </Typography>
                <TextField
                    type="number"
                    size="small"
                    value={quantity}
                    onChange={handleInputChange}
                    onBlur={handleInputBlur}
                    onKeyDown={handleKeyDown}
                    inputProps={{ min: 1, max: stock }}
                    sx={{ width: '100px' }}
                />
                <Button
                    variant="contained"
                    size="small"
                    sx={{ textTransform: 'none', borderRadius: 2 }}
                    onClick={handleDeletion}
                >
                    Delete
                </Button>
            </Box>
        </Box>
    );
};

export default CartCard;