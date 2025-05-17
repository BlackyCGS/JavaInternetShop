import React, { useState, useEffect } from 'react'
import {
    Box,
    Typography,
    CircularProgress,
    Container,
    Paper, Stack, Button, TextField
} from '@mui/material'
import ProductList from '../components/ProductList'
import {fetchMotherboardProducts, getTotalProducts} from '../api/ProductsApi'  // Предположим, у тебя есть отдельный метод для загрузки материнских плат
import { Product } from '../types/Product.ts'

const MotherboardList = () => {
    const [motherboards, setMotherboards] = useState<Product[]>([])
    const [loading, setLoading] = useState(false)
    const [pageLoading, setPageLoading] = useState(true)
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

    const handlePageChange = (newPage: number) => {
        if (newPage >= 0 && newPage <= totalPages) {
            setPage(newPage)
        }
    }

    useEffect(() => {
        const loadMotherboardProducts = async () => {
            if(loading) return
            try {
                setLoading(true)
                const data = await fetchMotherboardProducts(page, pageSize)
                setMotherboards(data)
                const number = await getTotalProducts("Motherboard", "")
                setTotalPages(Math.ceil(number/pageSize))
            } catch (error) {
                console.error('Error loading motherboard products:', error)
            } finally {
                setLoading(false)
                setPageLoading(false)
            }
        }

        loadMotherboardProducts()
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
                Motherboards
            </Typography>

            <Paper elevation={0} sx={{ p: 3, borderRadius: 3 }}>
                <ProductList products={motherboards} />
            </Paper>
            <Stack sx={{py: 5}} direction="row" spacing={2} alignItems="center">
                <Button
                    variant="contained"
                    onClick={handlePrevPage}
                    disabled={page === 0}
                >
                    Previous
                </Button>
                <Button
                    variant="contained"
                    onClick={handleNextPage}
                    disabled={page + 1 >= totalPages}
                >
                    Next
                </Button>
                <Typography>Page</Typography>
                <TextField
                    type="number"
                    size="small"
                    defaultValue={page + 1}
                    onKeyDown={(e: React.KeyboardEvent<HTMLInputElement>) => {
                        const target = e.target as HTMLInputElement;
                        if (e.key === 'Enter') {
                            const page = parseInt(target.value);
                            if (!isNaN(page) && page >= 1 && page <= totalPages) {
                                handlePageChange(page - 1);
                            } else {
                                target.value = (page + 1).toString();
                            }
                        }
                    }}
                    sx={{ width: '100px' }}
                    key={`page-input-${page}`}
                />
                <Typography>of {totalPages}</Typography>
            </Stack>
        </Container>
    )
}

export default MotherboardList
