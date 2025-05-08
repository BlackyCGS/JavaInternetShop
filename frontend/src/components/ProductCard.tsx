import { Box, Typography, Chip, Link, Button } from '@mui/material'
import { Product } from '../types/Product.ts'
import { useTheme } from '@mui/material/styles'
import { addProductToCart} from "../api/OrdersApi.ts";
import { useAuth } from '../hooks/useAuth'
import { useNavigate } from 'react-router-dom';


const ProductCard = ({ product }: { product: Product }) => {
    const theme = useTheme()
    const { id, name, price, gpu, motherboard } = product
    const { user } = useAuth()
    const navigate = useNavigate()

    const handleAddToCart = async (productId: number) => {
        await addProductToCart(productId)
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
                            <Chip
                                label={gpu.producer}
                                size="small"
                                sx={{ backgroundColor: theme.palette.grey[200] }}
                            />
                            <Chip
                                label={`${gpu.vram}GB VRAM`}
                                size="small"
                                sx={{ backgroundColor: theme.palette.grey[200] }}
                            />
                        </>
                    )}
                    {motherboard && (
                        <>
                            <Chip
                                label={motherboard.socket}
                                size="small"
                                sx={{ backgroundColor: theme.palette.grey[200] }}
                            />
                            <Chip
                                label={motherboard.chipset}
                                size="small"
                                sx={{ backgroundColor: theme.palette.grey[200] }}
                            />
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
                {user ? (
                <Button
                    variant="contained"
                    size="small"
                    sx={{
                        textTransform: 'none',
                        borderRadius: 2
                    }}
                    onClick={() => handleAddToCart(product.id)}
                >
                    Add to cart
                </Button>
                    ) : (
                        <Button
                            variant="contained"
                            size="small"
                            sx={{
                            textTransform: 'none',
                            borderRadius: 2
                        }}
                            onClick={() => navigate('/login')}
                        >
                            Log in to add Products to cart
                        </Button>
                    )}
            </Box>
        </Box>
    )
}

export default ProductCard