import { useState, useEffect } from 'react'
import {
    Typography,
    Container,
    Paper, Stack, Box, CircularProgress
} from '@mui/material'
import ProductList from '../components/ProductList'
import {fetchGpuProducts, getTotalProducts} from '../api/ProductsApi'  // Предположим, у тебя есть отдельный метод для загрузки видеокарт
import { Product } from '../types/Product.ts'

const GpuList = () => {
    const [gpus, setGpus] = useState<Product[]>([])
    const [loading, setLoading] = useState(false)
    const [totalPages, setTotalPages] = useState(0);
    const [pageLoading, setPageLoading] = useState(true)
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
                if(loading) return
                setLoading(true)

                const data = await fetchGpuProducts(page, pageSize)
                setGpus(data)
                const number = await getTotalProducts("Gpu", "")
                setTotalPages(Math.ceil(number/pageSize))
            } catch (error) {
                console.error('Error loading GPU products:', error)
            } finally {
                setLoading(false)
                setPageLoading(false)
            }
        }

        loadGpuProducts()
    }, [page])

    if (pageLoading) {
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
                Gpus
            </Typography>

            <Paper elevation={0} sx={{ p: 3, borderRadius: 3 }}>
                <ProductList products={gpus} />
            </Paper>
            <Stack direction="row" spacing={2}>
                <button onClick={handlePrevPage} disabled={page === 0}>Previous</button>
                <button onClick={handleNextPage} disabled={page + 1 >= totalPages}>Next</button>
                <p>Page {page + 1} of {totalPages}</p>
            </Stack>
        </Container>
    )
}

export default GpuList
