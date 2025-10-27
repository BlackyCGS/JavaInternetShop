import React, { useState, useEffect } from 'react'
import {
    Box,
    Typography,
    CircularProgress,
    Container,
    Paper,
    Stack,
    Button,
    TextField,
    Grid
} from '@mui/material'
import ProductList from '../components/ProductList'
import {
    fetchMotherboardProducts,
    getTotalMotherboards,
} from '../api/ProductsApi'
import { Product } from '../types/Product.ts'

const MotherboardList = () => {
    const [motherboards, setMotherboards] = useState<Product[]>([])
    const [minPrice, setMinPrice] = useState<number | "">("")
    const [maxPrice, setMaxPrice] = useState<number | "">("")
    const [socket, setSocket] = useState<string>("")
    const [chipset, setChipset] = useState<string>("")
    const [formFactor, setFormFactor] = useState<string>("")
    const [memoryType, setMemoryType] = useState<string>("")
    const [loading, setLoading] = useState(false)
    const [pageLoading, setPageLoading] = useState(true)
    const [totalPages, setTotalPages] = useState(0)
    const [page, setPage] = useState(0)
    const [pageSize] = useState(20)

    const handleNextPage = () => {
        if (page + 1 < totalPages) setPage(prev => prev + 1)
    }

    const handlePrevPage = () => {
        if (page > 0) setPage(prev => prev - 1)
    }

    const handlePageChange = (newPage: number) => {
        if (newPage >= 0 && newPage < totalPages) setPage(newPage)
    }

    const loadMotherboards = async () => {
        if (loading) return
        try {
            setLoading(true)
            const data = await fetchMotherboardProducts(
                page,
                pageSize,
                minPrice || undefined,
                maxPrice || undefined,
                socket || undefined,
                chipset || undefined,
                formFactor || undefined,
                memoryType || undefined
            )
            setMotherboards(data)

            const total = await getTotalMotherboards(
                minPrice || undefined,
                maxPrice || undefined,
                socket || undefined,
                chipset || undefined,
                formFactor || undefined,
                memoryType || undefined
            )
            setTotalPages(Math.ceil(total / pageSize))
        } catch (error) {
            console.error('Error loading motherboard products:', error)
        } finally {
            setLoading(false)
            setPageLoading(false)
        }
    }

    useEffect(() => {
        loadMotherboards()
    }, [page])

    const handleFilterApply = () => {
        setPage(0)
        loadMotherboards()
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
                Motherboards
            </Typography>

            <Grid container spacing={3}>
                {/* Фильтры */}
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
                            label="Socket"
                            fullWidth
                            margin="dense"
                            value={socket}
                            onChange={e => setSocket(e.target.value)}
                        />
                        <TextField
                            label="Chipset"
                            fullWidth
                            margin="dense"
                            value={chipset}
                            onChange={e => setChipset(e.target.value)}
                        />
                        <TextField
                            label="Form Factor"
                            fullWidth
                            margin="dense"
                            value={formFactor}
                            onChange={e => setFormFactor(e.target.value)}
                        />
                        <TextField
                            label="Memory Type"
                            fullWidth
                            margin="dense"
                            value={memoryType}
                            onChange={e => setMemoryType(e.target.value)}
                        />

                        <Button variant="contained" fullWidth sx={{ mt: 2 }} onClick={handleFilterApply}>
                            Apply Filters
                        </Button>
                    </Paper>
                </Grid>

                {/* Список материнских плат */}
                <Grid size={{xs: 12, md: 9}}>
                    <Paper elevation={0} sx={{ p: 3, borderRadius: 3 }}>
                        <ProductList products={motherboards} />
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

export default MotherboardList
