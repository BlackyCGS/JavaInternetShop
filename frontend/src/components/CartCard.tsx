import {Box, Typography, Chip, Link, Button, TextField} from '@mui/material';
import { useTheme } from '@mui/material/styles';
import {ProductItem} from "../types/ProductItem.ts";
import {useState} from "react";

interface CartCardProps {
    product: ProductItem;
    onDelete: (productId: number) => void;
    onQuantityChange: (productId: number, newQuantity: number) => void;
}

const CartCard = ({ product, onDelete, onQuantityChange }: CartCardProps) => {
    const theme = useTheme();
    const { id, name, gpu, motherboard } = product.product;
    let {price} = product.product;
    const [quantity, setQuantity] = useState(product.quantity);

    const handleDeletion = async () => {
        onDelete(id);
    };

    const handleQuantityChange = async (newQuantity: number) => {
        onQuantityChange(id, newQuantity);
    }



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
                    transform: 'translateY(-2px)'
                }
            }}
        >
            <Box sx={{ flex: 1 }}>
                <Link
                    href={`/products/${id}`}
                    underline="none"
                    color="text.primary"
                    sx={{
                        fontSize: '1.1rem',
                        fontWeight: 500,
                        '&:hover': {
                            color: theme.palette.primary.main
                        }
                    }}
                >
                    {name}
                </Link>
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
                <Typography
                    variant="h6"
                    sx={{
                        fontWeight: 600,
                        color: theme.palette.primary.main
                    }}
                >
                    ${(price*quantity).toLocaleString()}
                </Typography>
                <TextField
                    type="number"
                    size="small"
                    defaultValue={quantity}
                    onKeyDown={(e: React.KeyboardEvent<HTMLInputElement>) => {
                        const target = e.target as HTMLInputElement;
                        if (e.key === 'Enter') {
                            const newQuantity = parseInt(target.value);
                            if (!isNaN(newQuantity) && newQuantity >= 1 && newQuantity != quantity) {
                                setQuantity(newQuantity);
                                handleQuantityChange(newQuantity);
                            } else {
                                target.value = (quantity).toString();
                            }
                        }
                    }}
                    onBlur={(e) => {
                        const target = e.target as HTMLInputElement;
                            const newQuantity = parseInt(target.value);
                            if (!isNaN(newQuantity) && newQuantity >= 1 && newQuantity != quantity) {
                                setQuantity(newQuantity);
                                handleQuantityChange(newQuantity);
                            } else {
                                target.value = (quantity).toString();
                            }
                    }}
                    sx={{ width: '100px' }}
                    key={`page-input-${quantity}`}
                />
                <Button
                    variant="contained"
                    size="small"
                    sx={{
                        textTransform: 'none',
                        borderRadius: 2
                    }}
                    onClick={handleDeletion}
                >
                    Delete
                </Button>
            </Box>
        </Box>
    );
};

export default CartCard;
