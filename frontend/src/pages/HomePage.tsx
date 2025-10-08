import React, { useState, useEffect } from 'react'
import { Box, Typography, CircularProgress, Container, Paper, Stack, Button, TextField } from '@mui/material'
import ProductList from '../components/ProductList'
import { fetchSearchedProducts, getTotalProducts } from '../api/ProductsApi'
import { Product } from '../types/Product.ts'
import {useLocation, useNavigate} from 'react-router-dom'

const HomePage = () => {
    const [products, setProducts] = useState<Product[]>([])
    const [loading, setLoading] = useState(false)
    const [pageLoading, setPageLoading] = useState(true)
    const [totalPages, setTotalPages] = useState(0)
    const [page, setPage] = useState(0)
    const [error, setError] = useState(false)
    const navigate = useNavigate()
    const [pageSize] = useState(10)
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const searchQuery = searchParams.get('search') ?? '';



    const loadProducts = async (search: string) => {
        try {
            if(loading) return
            console.log(searchParams.toString())
            setPageLoading(true)
            setLoading(true)

            const data = await fetchSearchedProducts(page, pageSize, search).catch(
                error => {
                    setError(true)
                    console.log(error);
                }
            )
            console.log(data)
            setProducts(data)
            console.log("set data")
            const number = await getTotalProducts("All", search)
            setTotalPages(Math.ceil(number / pageSize))
        } catch (error) {
            console.error('Error loading products:', error)
        } finally {
            setLoading(false)
            setPageLoading(false)

        }
    }


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

    const handlePageChange = (newPage: number) => {
        if (newPage >= 0 && newPage <= totalPages) {
            setPage(newPage)
        }
    }

    const handleSearchReset = () => {
        setPage(0)
        navigate(location.pathname, { replace: true });
        setPageLoading(true)
        setError(false)
    }


    useEffect(() => {
        console.log("yoyo");
        if (!error) {
            console.log("yo");
            loadProducts(searchQuery)
        }
    }, [page, searchQuery])

    if (pageLoading) {
        return (
            <Box display="flex" justifyContent="center" mt={10}>
                <CircularProgress size={60} />
            </Box>
        )
    }

    if(error) {
        return (
            <Box >
                <Box display="flex" justifyContent="center" mt={10}>
                <Typography variant="h2" >
                No Products found with name "{searchQuery}"
                </Typography>
                </Box>
                <Box display="flex" justifyContent="center" mt={10}>
                <Button size="large"
                    variant="contained"
                    onClick={handleSearchReset}
                >
                    Back to Main Page
                </Button>
                </Box>
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

export default HomePage
