import { useEffect, useState } from 'react'
import {
    Tab,
    Tabs,
    Box,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Button,
    Grid,
    MenuItem,
    Select,
    FormControl,
    Stack,
    Typography
} from '@mui/material'
import CreateProductModal from '../modals/CreateProductModal'
import EditProductModal from '../modals/UpdateProductModal'
import OrderDetailsModal from '../modals/OrderDetailsModal'
import {useNavigate} from "react-router-dom";

import {
    fetchProducts,
    createProduct,
    updateProduct,
    deleteProduct,
    getTotalProducts
} from '../api/ProductsApi'
import { fetchAllUsers, changeUserRole, getTotalUsers } from '../api/UsersApi'
import { fetchAllOrders, changeOrderStatus, getTotalOrders } from '../api/OrdersApi'
import { Product } from '../types/Product'
import { User } from '../types/User'
import { Order } from '../types/Order'
import {useAuth} from "../hooks/useAuth.tsx";

const AdminTab = () => {
    const [tabIndex, setTabIndex] = useState(0)
    const [products, setProducts] = useState<Product[]>([])
    const [users, setUsers] = useState<User[]>([])
    const [orders, setOrders] = useState<Order[]>([])

    const [openCreateProduct, setOpenCreateProduct] = useState(false)
    const [openEditProduct, setOpenEditProduct] = useState(false)
    const [selectedProduct, setSelectedProduct] = useState<Product | null>(null)
    const userRoles = ['USER', 'ADMIN', 'MERCHANT', 'DELIVERY']
    const orderStatuses = ['CREATED', 'CONFIRMED', 'PROCESSED', 'DELIVERED', 'CANCELLED']
    const [modalOpen, setModalOpen] = useState(false);
    const [selectedOrder, setSelectedOrder] = useState<Order | null>(null);
    const [productTotalPages, setProductTotalPages] = useState(0);
    const [productPage, setProductPage] = useState(0);
    const [orderTotalPages, setOrderTotalPages] = useState(0);
    const [orderPage, setOrderPage] = useState(0);
    const [userTotalPages, setUserTotalPages] = useState(0);
    const [userPage, setUserPage] = useState(0);
    const [pageSize] = useState(100);
    const { user } = useAuth();
    const navigate = useNavigate();

    const handleNextProductPage = () => {
        if (productPage + 1 < productTotalPages) {
            setProductPage(prev => prev + 1);
        }
    };

    const handlePrevProductPage = () => {
        if (productPage > 0) {
            setProductPage(prev => prev - 1);
        }
    };

    const handleNextUserPage = () => {
        if (userPage + 1 < userTotalPages) {
            setUserPage(prev => prev + 1);
        }
    };

    const handlePrevUserPage = () => {
        if (userPage > 0) {
            setUserPage(prev => prev - 1);
        }
    };

    const handleNextOrderPage = () => {
        if (orderPage + 1 < orderTotalPages) {
            setOrderPage(prev => prev + 1);
        }
    };

    const handlePrevOrderPage = () => {
        if (orderPage > 0) {
            setOrderPage(prev => prev - 1);
        }
    };

    useEffect(() => {
        if (!user || !['ADMIN', 'MERCHANT', 'DELIVERY'].includes(user.role)) {
            navigate('/'); // Перенаправляем на главную, если нет доступа
        }
        getAvailableTabs();
        loadData()
    }, [user, navigate, tabIndex, productPage, userPage, orderPage])

    const loadData = async () => {
        if (tabIndex === 1) {
            const data = await fetchProducts(productPage, pageSize) // загружаем 100
            // товаров
            setProducts(data)
            const number = await getTotalProducts("All", "")
            setProductTotalPages(Math.ceil(number/pageSize))
        } else if (tabIndex === 2) {
            const data = await fetchAllUsers(userPage, pageSize)
            setUsers(data)
            const number = await getTotalUsers()
            setUserTotalPages(Math.ceil(number/pageSize))
        } else if (tabIndex === 0) {
            const data = await fetchAllOrders(orderPage, pageSize)
            setOrders(data)
            const number = await getTotalOrders()
            setOrderTotalPages(Math.ceil(number/pageSize))
        }
    }

    const handleCreateProduct = async (product: Product) => {
        await createProduct(product)
        await loadData()
        setOpenCreateProduct(false)
    }

    const handleEditProduct = async (product: Product) => {
        await updateProduct(product)
        await loadData()
        setOpenEditProduct(false)
    }

    const handleDeleteProduct = async (id: number) => {
        await deleteProduct(id)
        await loadData()
    }

    const handleOpenEditProduct = (product: Product) => {
        setSelectedProduct(product)
        setOpenEditProduct(true)
    }

    const handleRoleChange = async (userId: number, newRole: string) => {
        await changeUserRole(userId, newRole)
        await loadData()
    }

    const handleStatusChange = async (orderId: number, newStatus: string) => {
        await changeOrderStatus(orderId, newStatus)
        await loadData()
    }

    const handleOpenModal = (order: Order) => {
        setSelectedOrder(order);
        setModalOpen(true);
    };

    const handleCloseModal = () => {
        setModalOpen(false);
    };


    const getAvailableTabs = () => {
        if (!user) return [];

        switch(user.role) {
            case 'ADMIN':
                return [
                    { label: "Продукты", value: 0 },
                    { label: "Пользователи", value: 1 },
                    { label: "Заказы", value: 2 }
                ];
            case 'MERCHANT':
                return [
                    { label: "Продукты", value: 0 },
                    { label: "Заказы", value: 2 }
                ];
            case 'DELIVERY':
                return [
                    { label: "Заказы", value: 2 }
                ];
            default:
                return [];
        }
    };

    // @ts-ignore
    return (

        <Box>
            <Tabs value={tabIndex} onChange={(_,val) => setTabIndex(val)}>
                {['ADMIN', 'MERCHANT', 'DELIVERY'].includes(user.role) && (
                    <Tab label="Заказы" />
                )}
                { user.role === 'ADMIN' && (
                <Tab label="Пользователи" />
                    )}
                { (user.role === 'ADMIN' || user.role === 'MERCHANT') && (
                    <Tab label="Продукты" />
                )}

            </Tabs>

            {!user ? (
                    <Typography variant="h6" sx={{ p: 2 }}>
                        Доступ запрещен. Пожалуйста, авторизуйтесь.
                    </Typography>
                ) : (
            <Box mt={2}>
                {tabIndex === 1 && (user.role === 'ADMIN' || user.role === 'MERCHANT') && (
                    <>
                        <Box display="flex" justifyContent="flex-end" mb={2}>
                            <Button
                                sx = {{mr:5}}
                                variant="contained" onClick={() => setOpenCreateProduct(true)}>
                                Добавить продукт
                            </Button>
                        </Box>

                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell>ID</TableCell>
                                        <TableCell>Название</TableCell>
                                        <TableCell>Цена</TableCell>
                                        <TableCell>Действия</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {products.map((product) => (
                                        <TableRow key={product.id}>
                                            <TableCell>{product.id}</TableCell>
                                            <TableCell>{product.name}</TableCell>
                                            <TableCell>${product.price}</TableCell>
                                            <TableCell>
                                                <Grid container spacing={1}>
                                                    <Grid>
                                                        <Button
                                                            variant="outlined"
                                                            size="small"
                                                            onClick={() => handleOpenEditProduct(product)}
                                                        >
                                                            Изменить
                                                        </Button>
                                                    </Grid>
                                                    <Grid >
                                                        <Button
                                                            variant="outlined"
                                                            color="error"
                                                            size="small"
                                                            onClick={() => handleDeleteProduct(product.id)}
                                                        >
                                                            Удалить
                                                        </Button>
                                                    </Grid>
                                                </Grid>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>

                        {/* Модалки */}
                        <CreateProductModal
                            open={openCreateProduct}
                            onClose={() => setOpenCreateProduct(false)}
                            onSave={handleCreateProduct}
                        />
                        {selectedProduct && (
                            <EditProductModal
                                open={openEditProduct}
                                onClose={() => setOpenEditProduct(false)}
                                onSubmit={handleEditProduct}
                                initialData={selectedProduct} product={selectedProduct}
                                onSave={function (updatedProduct: Product): void {
                                    handleCreateProduct(updatedProduct)
                                }}                            />
                        )}
                        <Stack
                            sx={{m:5}}
                            direction="row" spacing={2}>
                            <button onClick={handlePrevProductPage} disabled={productPage === 0}>Назад</button>
                            <button onClick={handleNextProductPage} disabled={productPage + 1 >= productTotalPages}>Вперёд</button>
                            <p>Страница {productPage + 1} из {productTotalPages}</p>
                        </Stack>
                    </>
                )}

                {tabIndex === 2 && user.role === 'ADMIN' && (
                    <>
                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell>ID</TableCell>
                                        <TableCell>Имя</TableCell>
                                        <TableCell>Email</TableCell>
                                        <TableCell>Роль</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {users.map((user) => (
                                        <TableRow key={user.id}>
                                            <TableCell>{user.id}</TableCell>
                                            <TableCell>{user.name}</TableCell>
                                            <TableCell>{user.email}</TableCell>
                                            <TableCell>
                                                <FormControl fullWidth size="small">
                                                    <Select
                                                        value={user.role}
                                                        onChange={(e) =>
                                                            handleRoleChange(user.id, e.target.value)
                                                        }
                                                    >
                                                        {userRoles.map((role) => (
                                                            <MenuItem key={role} value={role}>
                                                                {role}
                                                            </MenuItem>
                                                        ))}
                                                    </Select>
                                                </FormControl>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                        <Stack
                            sx={{m:5}}
                            direction="row" spacing={2}>
                            <button onClick={handlePrevUserPage} disabled={userPage === 0}>Назад</button>
                            <button onClick={handleNextUserPage} disabled={userPage + 1 >= userTotalPages}>Вперёд</button>
                            <p>Страница {userPage + 1} из {userTotalPages}</p>
                        </Stack>
                    </>
                )}

                {tabIndex === 0 && ['ADMIN', 'MERCHANT', 'DELIVERY'].includes(user.role) &&(
                    <>
                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Order Id</TableCell>
                                        <TableCell>UserId</TableCell>
                                        <TableCell>Order Status</TableCell>
                                        <TableCell>Order Details</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {orders.map((order) => (
                                        <TableRow key={order.orderId}>
                                            <TableCell>{order.orderId}</TableCell>
                                            <TableCell>{order.userId}</TableCell>
                                            <TableCell>
                                                <FormControl fullWidth size="small">
                                                    <Select
                                                        value={order.orderStatus}
                                                        onChange={(e) =>
                                                            handleStatusChange(order.orderId, e.target.value as string)
                                                        }
                                                    >
                                                        {orderStatuses.map((status) => (
                                                            <MenuItem key={status} value={status}>
                                                                {status}
                                                            </MenuItem>
                                                        ))}
                                                    </Select>
                                                </FormControl>
                                            </TableCell>
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
                        <OrderDetailsModal
                            open={modalOpen}
                            onClose={handleCloseModal}
                            order={selectedOrder}
                        />
                        <Stack
                            sx={{m:5}}
                            direction="row" spacing={2}>
                            <button onClick={handlePrevOrderPage} disabled={orderPage === 0}>Назад</button>
                            <button onClick={handleNextOrderPage} disabled={orderPage + 1 >= orderTotalPages}>Вперёд</button>
                            <p>Страница {orderPage + 1} из {orderTotalPages}</p>
                        </Stack>
                    </>
                )}
            </Box>
                )}
        </Box>
    )
}

export default AdminTab