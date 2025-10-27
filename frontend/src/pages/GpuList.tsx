import React, { useState, useEffect } from 'react'
import {
    Typography,
    Container,
    Paper,
    Stack,
    Box,
    CircularProgress,
    Button,
    TextField,
    Grid
} from '@mui/material'
import ProductList from '../components/ProductList'
import {fetchGpuProducts, getTotalGpus} from '../api/ProductsApi'
import { Product } from '../types/Product.ts'

const GpuList = () => {
    const [gpus, setGpus] = useState<Product[]>([])
    const [loading, setLoading] = useState(false)
    const [totalPages, setTotalPages] = useState(0)
    const [pageLoading, setPageLoading] = useState(true)
    const [page, setPage] = useState(0)
    const [pageSize] = useState(20)

    // Фильтры
    const [minPrice, setMinPrice] = useState<number | "">("")
    const [maxPrice, setMaxPrice] = useState<number | "">("")
    const [minBoostClock, setMinBoostClock] = useState<number | "">("")
    const [maxBoostClock, setMaxBoostClock] = useState<number | "">("")
    const [minVram, setMinVram] = useState<number | "">("")
    const [maxVram, setMaxVram] = useState<number | "">("")
    const [minTdp, setMinTdp] = useState<number | "">("")
    const [maxTdp, setMaxTdp] = useState<number | "">("")

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
        if (newPage >= 0 && newPage < totalPages) {
            setPage(newPage)
        }
    }

    const handleFilterApply = () => {
        setPage(0) // при применении фильтров возвращаемся на первую страницу
        loadGpuProducts()
    }

    const loadGpuProducts = async () => {
        try {
            if (loading) return
            setLoading(true)

            const data = await fetchGpuProducts(
                page,
                pageSize,
                minPrice || undefined,
                maxPrice || undefined,
                minBoostClock || undefined,
                maxBoostClock || undefined,
                minVram || undefined,
                maxVram || undefined,
                minTdp || undefined,
                maxTdp || undefined
            )
            setGpus(data)
            const total = await getTotalGpus(minPrice || undefined,
                maxPrice || undefined,
                minBoostClock || undefined,
                maxBoostClock || undefined,
                minVram || undefined,
                maxVram || undefined,
                minTdp || undefined,
                maxTdp || undefined)
            setTotalPages(Math.ceil(total / pageSize))
        } catch (error) {
            console.error('Error loading GPU products:', error)
        } finally {
            setLoading(false)
            setPageLoading(false)
        }
    }

    useEffect(() => {
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
            <Typography variant="h4" gutterBottom sx={{ mb: 4, fontWeight: 600, color: 'text.primary' }}>
                Gpus
            </Typography>

            <Grid container spacing={3}>
                {/* Боковая панель фильтров */}
                <Grid size={{xs: 12, md: 3}}>
                    <Paper elevation={1} sx={{ p: 3, borderRadius: 3 }}>
                        <Typography variant="h6" gutterBottom>Filters</Typography>

                        <TextField
                            label="Min Price"
                            type="number"
                            fullWidth
                            margin="dense"
                            value={minPrice}
                            onChange={e => setMinPrice(e.target.value === "" ? "" : Number(e.target.value))}
                        />
                        <TextField
                            label="Max Price"
                            type="number"
                            fullWidth
                            margin="dense"
                            value={maxPrice}
                            onChange={e => setMaxPrice(e.target.value === "" ? "" : Number(e.target.value))}
                        />
                        <TextField
                            label="Min Boost Clock"
                            type="number"
                            fullWidth
                            margin="dense"
                            value={minBoostClock}
                            onChange={e => setMinBoostClock(e.target.value === "" ? "" : Number(e.target.value))}
                        />
                        <TextField
                            label="Max Boost Clock"
                            type="number"
                            fullWidth
                            margin="dense"
                            value={maxBoostClock}
                            onChange={e => setMaxBoostClock(e.target.value === "" ? "" : Number(e.target.value))}
                        />
                        <TextField
                            label="Min VRAM"
                            type="number"
                            fullWidth
                            margin="dense"
                            value={minVram}
                            onChange={e => setMinVram(e.target.value === "" ? "" : Number(e.target.value))}
                        />
                        <TextField
                            label="Max VRAM"
                            type="number"
                            fullWidth
                            margin="dense"
                            value={maxVram}
                            onChange={e => setMaxVram(e.target.value === "" ? "" : Number(e.target.value))}
                        />
                        <TextField
                            label="Min TDP"
                            type="number"
                            fullWidth
                            margin="dense"
                            value={minTdp}
                            onChange={e => setMinTdp(e.target.value === "" ? "" : Number(e.target.value))}
                        />
                        <TextField
                            label="Max TDP"
                            type="number"
                            fullWidth
                            margin="dense"
                            value={maxTdp}
                            onChange={e => setMaxTdp(e.target.value === "" ? "" : Number(e.target.value))}
                        />

                        <Button variant="contained" fullWidth sx={{ mt: 2 }} onClick={handleFilterApply}>
                            Apply Filters
                        </Button>
                    </Paper>
                </Grid>

                {/* Список продуктов */}
                <Grid size={{xs: 12, md: 9}} >
                    <Paper elevation={0} sx={{ p: 3, borderRadius: 3 }}>
                        <ProductList products={gpus} />
                    </Paper>

                    <Stack sx={{ py: 5 }} direction="row" spacing={2} alignItems="center">
                        <Button variant="contained" onClick={handlePrevPage} disabled={page === 0}>
                            Previous
                        </Button>
                        <Button variant="contained" onClick={handleNextPage} disabled={page + 1 >= totalPages}>
                            Next
                        </Button>
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

export default GpuList
