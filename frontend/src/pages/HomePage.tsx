import { useState, useEffect } from 'react'
import { Box, Typography, CircularProgress, Container, Paper, Stack } from '@mui/material'
import ProductList from '../components/ProductList'
import { fetchSearchedProducts, getTotalProducts } from '../api/ProductsApi'
import { Product } from '../types/Product.ts'
import { useLocation } from 'react-router-dom'

const HomePage = () => {
    const [products, setProducts] = useState<Product[]>([])
    const [loading, setLoading] = useState(false)
    const [pageLoading, setPageLoading] = useState(true)
    const [totalPages, setTotalPages] = useState(0)
    const [page, setPage] = useState(0)
    const [pageSize] = useState(10)

    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const searchQuery = searchParams.get('search') ?? '';

    const handleNextPage = () => {
        if (page + 1 < totalPages) {
            setPage(prev => prev + 1)
        }
    }

    const handlePrevPage = () => {
        if (page > 0) {
            setPage(prev => prev - 1)
        }
    }

    useEffect(() => {
        const loadProducts = async () => {
            try {
                if(loading) return
                setLoading(true)
                const data = await fetchSearchedProducts(page, pageSize, searchQuery)
                setProducts(data)
                const number = await getTotalProducts("All", searchQuery)
                setTotalPages(Math.ceil(number / pageSize))
            } catch (error) {
                console.error('Error loading products:', error)
            } finally {
                setLoading(false)
                setPageLoading(false)
            }
        }

        loadProducts()
    }, [page, searchQuery])

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
                sx={{ mb: 4, fontWeight: 600, color: 'text.primary' }}
            >
                {searchQuery ? `Search results "${searchQuery}"` : 'Products'}
            </Typography>

            <Paper elevation={0} sx={{ p: 3, borderRadius: 3 }}>
                <ProductList products={products} />
            </Paper>

            <Stack direction="row" spacing={2}>
                <button onClick={handlePrevPage} disabled={page === 0}>Previous</button>
                <button onClick={handleNextPage} disabled={page + 1 >= totalPages}>Next</button>
                <p>Page {page + 1} of {totalPages}</p>
            </Stack>
        </Container>
    )
}

export default HomePage
