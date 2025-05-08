import { useState, useEffect } from 'react'
import { useParams, Link } from 'react-router-dom'
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

const ProductPage = () => {
    const { id } = useParams()
    const [product, setProduct] = useState<Product | null>(null)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const loadProduct = async () => {
            try {
                const data = await fetchProductById(Number(id))
                setProduct(data)
            } catch (error) {
                console.error('Error loading product:', error)
            } finally {
                setLoading(false)
            }
        }

        loadProduct()
    }, [id])

    if (loading) {
        return (
            <Box display="flex" justifyContent="center" mt={10}>
                <CircularProgress size={60} />
            </Box>
        )
    }

    if (!product) {
        return (
            <Container maxWidth="lg" sx={{ py: 4 }}>
                <Typography variant="h5">Товар не найден</Typography>
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
                Назад к каталогу
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
                            Характеристики видеокарты
                        </Typography>
                        <Divider sx={{ mb: 2 }} />
                        <Stack direction="row" spacing={1} sx={{ flexWrap: 'wrap', gap: 1 }}>
                            <Chip label={`Производитель: ${gpu.producer}`} />
                            <Chip label={`VRAM: ${gpu.vram}GB`} />
                            <Chip label={`Тактовая частота: ${gpu.boostClock}MHz`} />
                            <Chip label={`TDP: ${gpu.tdp}W`} />
                            <Chip label={`HDMI: ${gpu.hdmi}`} />
                            <Chip label={`DisplayPort: ${gpu.displayPort}`} />
                        </Stack>
                    </Box>
                )}

                {motherboard && (
                    <Box>
                        <Typography variant="h5" gutterBottom sx={{ fontWeight: 500 }}>
                            Характеристики материнской платы
                        </Typography>
                        <Divider sx={{ mb: 2 }} />
                        <Stack direction="row" spacing={1} sx={{ flexWrap: 'wrap', gap: 1 }}>
                            <Chip label={`Производитель: ${motherboard.producer}`} />
                            <Chip label={`Сокет: ${motherboard.socket}`} />
                            <Chip label={`Чипсет: ${motherboard.chipset}`} />
                            <Chip label={`Форм-фактор: ${motherboard.formFactor}`} />
                            <Chip label={`Слоты RAM: ${motherboard.ramSlots}`} />
                            <Chip label={`SATA: ${motherboard.sata}`} />
                        </Stack>
                    </Box>
                )}

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
                >
                    Добавить в корзину
                </Button>
            </Paper>
        </Container>
    )
}

export default ProductPage