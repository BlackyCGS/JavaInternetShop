import { useState, useEffect } from 'react'
import {
    Box,
    Typography,
    CircularProgress,
    Container,
    Paper, Stack
} from '@mui/material'
import ProductList from '../components/ProductList'
import {fetchGpuProducts, getTotalProducts} from '../api/ProductsApi'  // Предположим, у тебя есть отдельный метод для загрузки видеокарт
import { Product } from '../types/Product.ts'

const GpuList = () => {
    const [gpus, setGpus] = useState<Product[]>([])
    const [loading, setLoading] = useState(true)
    const [totalPages, setTotalPages] = useState(0);
    const [page, setPage] = useState(0);
    const [pageSize] = useState(10);

    const handleNextPage = () => {
        if (page + 1 < totalPages) {
            setPage(prev => prev + 1);
        }
    };

    const handlePrevPage = () => {
        if (page > 0) {
            setPage(prev => prev - 1);
        }
    };


    useEffect(() => {
        const loadGpuProducts = async () => {
            try {
                const data = await fetchGpuProducts(page, pageSize)
                setGpus(data)
                const number = await getTotalProducts("Gpu", "")
                setTotalPages(Math.ceil(number/pageSize))
            } catch (error) {
                console.error('Error loading GPU products:', error)
            } finally {
                setLoading(false)
            }
        }

        loadGpuProducts()
    }, [page])

    if (loading) {
        return (
            <Box display="flex" justifyContent="center" mt={10}>
                <CircularProgress size={60} />
            </Box>
        )
    }

    return (
        <Container maxWidth="lg" sx={{ py: 4 }}>
            <Typography
                variant="h4"
                gutterBottom
                sx={{
                    mb: 4,
                    fontWeight: 600,
                    color: 'text.primary'
                }}
            >
                Видеокарты
            </Typography>

            <Paper elevation={0} sx={{ p: 3, borderRadius: 3 }}>
                <ProductList products={gpus} />
            </Paper>
            <Stack direction="row" spacing={2}>
                <button onClick={handlePrevPage} disabled={page === 0}>Назад</button>
                <button onClick={handleNextPage} disabled={page + 1 >= totalPages}>Вперёд</button>
                <p>Страница {page + 1} из {totalPages}</p>
            </Stack>
        </Container>
    )
}

export default GpuList
