import { useEffect, useState } from 'react'
import { Order } from '../types/Order'
import { getUserOrders } from "../api/OrdersApi.ts";
import {
    Button,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    CircularProgress,
    Box
} from "@mui/material"
import OrderDetailsModal from "../modals/OrderDetailsModal.tsx"

const Orders = () => {
    const [orders, setOrders] = useState<Order[] | null>(null)
    const [modalOpen, setModalOpen] = useState(false)
    const [selectedOrder, setSelectedOrder] = useState<Order | null>(null)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const orders: Order[] = await getUserOrders()
                setOrders(orders)
                setError(null)
            } catch (err) {
                setError('Failed to fetch orders')
                console.error(err)
            } finally {
                setLoading(false)
            }
        }

        fetchOrders()
    }, [])

    const handleOpenModal = (order: Order) => {
        setSelectedOrder(order)
        setModalOpen(true)
    }

    const handleCloseModal = () => {
        setModalOpen(false)
    }

    if (loading) {
        return (
            <Box display="flex" justifyContent="center" p={4}>
                <CircularProgress />
            </Box>
        )
    }

    if (error) {
        return <p>{error}</p>
    }

    if (!orders || orders.length === 0) {
        return <p>No orders found</p>
    }

    return (
        <>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Order Id</TableCell>
                            <TableCell>Order Status</TableCell>
                            <TableCell>Order Details</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {orders.map((order) => (
                            <TableRow key={order.orderId}>
                                <TableCell>{order.orderId}</TableCell>
                                <TableCell>{order.orderStatus}</TableCell>
                                <TableCell>
                                    <Button
                                        fullWidth
                                        variant="outlined"
                                        size="small"
                                        onClick={() => handleOpenModal(order)}
                                    >
                                        Show Details
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {selectedOrder && (
                <OrderDetailsModal
                    open={modalOpen}
                    onClose={handleCloseModal}
                    order={selectedOrder}
                />
            )}
        </>
    )
}

export default Orders