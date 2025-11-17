// src/pages/CpuList.tsx
import React, { useState, useEffect } from 'react'
import { Typography, Container, Paper, Stack, Box, CircularProgress, Button, TextField, Grid } from '@mui/material'
import ProductList from '../components/ProductList'
import { fetchProductsByCategory, getTotalProductsByCategory } from '../api/ProductsApi'
import { Product } from '../types/Product'
import { ProductCategory } from '../types/ProductCategory'

const CpuList = () => {
    const [products, setProducts] = useState<Product[]>([])
    const [loading, setLoading] = useState(false)
    const [pageLoading, setPageLoading] = useState(true)
    const [totalPages, setTotalPages] = useState(0)
    const [page, setPage] = useState(0)
    const [pageSize] = useState(20)

    // Filters
    const [minPrice, setMinPrice] = useState<number | "">("")
    const [maxPrice, setMaxPrice] = useState<number | "">("")
    const [minCores, setMinCores] = useState<number | "">("")
    const [maxCores, setMaxCores] = useState<number | "">("")
    const [minThreads, setMinThreads] = useState<number | "">("")
    const [maxThreads, setMaxThreads] = useState<number | "">("")
    const [minTdp, setMinTdp] = useState<number | "">("")
    const [maxTdp, setMaxTdp] = useState<number | "">("")
    const [socket, setSocket] = useState<string>("")

    const loadProducts = async () => {
        if (loading) return
        setLoading(true)
        try {
            const data = await fetchProductsByCategory(ProductCategory.CPU, page, pageSize, {
                minPrice: minPrice ?? undefined,
                maxPrice: maxPrice ?? undefined,
                minCores: minCores ?? undefined,
                maxCores: maxCores ?? undefined,
                minThreads: minThreads ?? undefined,
                maxThreads: maxThreads ?? undefined,
                minTdp: minTdp ?? undefined,
                maxTdp: maxTdp ?? undefined,
                socket: socket || undefined
            })
            setProducts(data)
            const total = await getTotalProductsByCategory(ProductCategory.CPU, {
                minPrice: minPrice ?? undefined,
                maxPrice: maxPrice ?? undefined,
                minCores: minCores ?? undefined,
                maxCores: maxCores ?? undefined,
                minThreads: minThreads ?? undefined,
                maxThreads: maxThreads ?? undefined,
                minTdp: minTdp ?? undefined,
                maxTdp: maxTdp ?? undefined,
                socket: socket || undefined
            })
            setTotalPages(Math.ceil(total / pageSize))
        } catch (err) {
            console.error('Error loading CPU products:', err)
        } finally {
            setLoading(false)
            setPageLoading(false)
        }
    }

    useEffect(() => { loadProducts() }, [page])

    const handleFilterApply = () => { setPage(0); loadProducts() }
    const handleNextPage = () => page + 1 < totalPages && setPage(prev => prev + 1)
    const handlePrevPage = () => page > 0 && setPage(prev => prev - 1)
    const handlePageChange = (newPage: number) => {
        if (newPage >= 0 && newPage < totalPages) setPage(newPage)
    }

    if (pageLoading) {
        return (
            <Box display="flex" justifyContent="center" mt={10}>
                <CircularProgress size={60} />
            </Box>
        )
    }

    return (
        <Container maxWidth="lg" sx={{ py: 4 }}>
            <Typography variant="h4" gutterBottom sx={{ mb: 4, fontWeight: 600, color: 'text.primary' }}>
                CPUs
            </Typography>
            <Grid container spacing={3}>
                <Grid size={{ xs: 12, md: 3 }}>
                    <Paper elevation={1} sx={{ p: 3, borderRadius: 3 }}>
                        <Typography variant="h6" gutterBottom>Filters</Typography>
                        <TextField label="Min Price" type="number" fullWidth margin="dense" value={minPrice} onChange={e => setMinPrice(e.target.value === "" ? "" : Number(e.target.value))} />
                        <TextField label="Max Price" type="number" fullWidth margin="dense" value={maxPrice} onChange={e => setMaxPrice(e.target.value === "" ? "" : Number(e.target.value))} />
                        <TextField label="Min Cores" type="number" fullWidth margin="dense" value={minCores} onChange={e => setMinCores(e.target.value === "" ? "" : Number(e.target.value))} />
                        <TextField label="Max Cores" type="number" fullWidth margin="dense" value={maxCores} onChange={e => setMaxCores(e.target.value === "" ? "" : Number(e.target.value))} />
                        <TextField label="Min Threads" type="number" fullWidth margin="dense" value={minThreads} onChange={e => setMinThreads(e.target.value === "" ? "" : Number(e.target.value))} />
                        <TextField label="Max Threads" type="number" fullWidth margin="dense" value={maxThreads} onChange={e => setMaxThreads(e.target.value === "" ? "" : Number(e.target.value))} />
                        <TextField label="Min TDP" type="number" fullWidth margin="dense" value={minTdp} onChange={e => setMinTdp(e.target.value === "" ? "" : Number(e.target.value))} />
                        <TextField label="Max TDP" type="number" fullWidth margin="dense" value={maxTdp} onChange={e => setMaxTdp(e.target.value === "" ? "" : Number(e.target.value))} />
                        <TextField label="Socket" fullWidth margin="dense" value={socket} onChange={e => setSocket(e.target.value)} />
                        <Button variant="contained" fullWidth sx={{ mt: 2 }} onClick={handleFilterApply}>Apply Filters</Button>
                    </Paper>
                </Grid>
                <Grid size={{ xs: 12, md: 9 }}>
                    <Paper elevation={0} sx={{ p: 3, borderRadius: 3 }}>
                        <ProductList products={products} />
                    </Paper>
                    <Stack sx={{ py: 5 }} direction="row" spacing={2} alignItems="center">
                        <Button variant="contained" onClick={handlePrevPage} disabled={page === 0}>Previous</Button>
                        <Button variant="contained" onClick={handleNextPage} disabled={page + 1 >= totalPages}>Next</Button>
                        <Typography>Page</Typography>
                        <TextField
                            type="number"
                            size="small"
                            defaultValue={page + 1}
                            onKeyDown={(e: React.KeyboardEvent<HTMLInputElement>) => {
                                const target = e.target as HTMLInputElement
                                if (e.key === 'Enter') {
                                    const newPage = parseInt(target.value)
                                    if (!isNaN(newPage) && newPage >= 1 && newPage <= totalPages) {
                                        handlePageChange(newPage - 1)
                                    } else {
                                        target.value = (page + 1).toString()
                                    }
                                }
                            }}
                            sx={{ width: '100px' }}
                            key={`page-input-${page}`}
                        />
                        <Typography>of {totalPages}</Typography>
                    </Stack>
                </Grid>
            </Grid>
        </Container>
    )
}

export default CpuList