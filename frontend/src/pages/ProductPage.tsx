import { useState, useEffect } from 'react'
import {useParams, Link, useNavigate} from 'react-router-dom'
import {
    Box,
    Typography,
    Chip,
    Button,
    CircularProgress,
    Container,
    Paper,
    Stack,
    Divider
} from '@mui/material'
import { fetchProductById } from '../api/ProductsApi'
import { Product } from '../types/Product.ts'
import {addProductToCart} from "../api/OrdersApi.ts";
import {useAuth} from "../hooks/useAuth.tsx";
import {useCart} from "../hooks/useCart.tsx";

const ProductPage = () => {
    const { id } = useParams()
    const [product, setProduct] = useState<Product | null>(null)
    const [loading, setLoading] = useState(false)
    const [pageLoading, setPageLoading] = useState(true)
    const navigate = useNavigate()
    const { user } = useAuth()
    const {updateCartCount, cartItems} = useCart()

    const isInCart = cartItems.includes(product?.id as number);


    useEffect(() => {
        const loadProduct = async () => {
            if(loading) return
            try {
                setLoading(true)
                const data = await fetchProductById(Number(id))
                setProduct(data)
            } catch (error) {
                console.error('Error loading product:', error)
            } finally {
                setLoading(false)
                setPageLoading(false)
            }
        }

        loadProduct()
    }, [id])

    const handleAddToCart = async (productId: number) => {
        await addProductToCart(productId)
        updateCartCount()
    }

    if (pageLoading) {
        return (
            <Box display="flex" justifyContent="center" mt={10}>
                <CircularProgress size={60} />
            </Box>
        )
    }

    if (!product) {
        return (
            <Container maxWidth="lg" sx={{ py: 4 }}>
                <Typography variant="h5">Product not found</Typography>
            </Container>
        )
    }

    const { name, price, gpu, motherboard } = product

    return (
        <Container maxWidth="lg" sx={{ py: 4 }}>
            <Button
                component={Link}
                to="/"
                variant="outlined"
                sx={{
                    mb: 3,
                    borderRadius: 2
                }}
            >
                Back to list
            </Button>

            <Paper elevation={0} sx={{ p: 4, borderRadius: 3 }}>
                <Typography variant="h4" gutterBottom sx={{ fontWeight: 600 }}>
                    {name}
                </Typography>

                <Typography
                    variant="h3"
                    sx={{
                        mb: 4,
                        color: 'primary.main',
                        fontWeight: 700
                    }}
                >
                    ${price.toLocaleString()}
                </Typography>

                {gpu && (
                    <Box sx={{ mb: 4 }}>
                        <Typography variant="h5" gutterBottom sx={{ fontWeight: 500 }}>
                            GPU specs
                        </Typography>
                        <Divider sx={{ mb: 2 }} />
                        <Stack direction="row" spacing={1} sx={{ flexWrap: 'wrap', gap: 1 }}>
                            <Chip label={`Producer: ${gpu.producer}`} />
                            <Chip label={`VRAM: ${gpu.vram}GB`} />
                            <Chip label={`Boost clock: ${gpu.boostClock}MHz`} />
                            <Chip label={`TDP: ${gpu.tdp}W`} />
                            <Chip label={`HDMI: ${gpu.hdmi}`} />
                            <Chip label={`DisplayPort: ${gpu.displayPort}`} />
                        </Stack>
                    </Box>
                )}

                {motherboard && (
                    <Box>
                        <Typography variant="h5" gutterBottom sx={{ fontWeight: 500 }}>
                            Motherboard specs
                        </Typography>
                        <Divider sx={{ mb: 2 }} />
                        <Stack direction="row" spacing={1} sx={{ flexWrap: 'wrap', gap: 1 }}>
                            <Chip label={`Producer: ${motherboard.producer}`} />
                            <Chip label={`Socket: ${motherboard.socket}`} />
                            <Chip label={`Chipset: ${motherboard.chipset}`} />
                            <Chip label={`Form factor: ${motherboard.formFactor}`} />
                            <Chip label={`RAM slots: ${motherboard.ramSlots}`} />
                            <Chip label={`SATA: ${motherboard.sata}`} />
                        </Stack>
                    </Box>
                )}

                {user ? (
                    <Button
                        variant="contained"
                        size="large"
                        fullWidth
                        sx={{
                            mt: 4,
                            py: 2,
                            borderRadius: 2,
                            fontSize: '1.1rem'
                        }}
                        onClick={() => handleAddToCart(product.id)}
                        disabled={isInCart}
                    >
                        {isInCart ? 'Already in Cart' : 'Add to Cart'}
                    </Button>
                ) : (
                    <Button
                        variant="contained"
                        size="large"
                        fullWidth
                        sx={{
                            mt: 4,
                            py: 2,
                            borderRadius: 2,
                            fontSize: '1.1rem'
                        }}
                        onClick={() => navigate("/login")}
                    >
                        Log in to add Products to cart
                    </Button>
                )}
            </Paper>
        </Container>
    )
}

export default ProductPage