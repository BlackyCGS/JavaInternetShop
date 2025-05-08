import { Box, Typography, Chip, Link, Button } from '@mui/material';
import { Product } from '../types/Product.ts';
import { useTheme } from '@mui/material/styles';

interface CartCardProps {
    product: Product;
    onDelete: (productId: number) => void;
}

const CartCard = ({ product, onDelete }: CartCardProps) => {
    const theme = useTheme();
    const { id, name, price, gpu, motherboard } = product;

    const handleDeletion = async () => {
        onDelete(id);
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
                    ${price.toLocaleString()}
                </Typography>
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
