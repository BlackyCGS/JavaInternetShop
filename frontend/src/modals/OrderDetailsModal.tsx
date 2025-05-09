import React from 'react';
import { Modal, Box, Typography, Button } from '@mui/material';

const modalStyle = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 4,
};

interface OrderDetailsModalProps {
    open: boolean;
    onClose: () => void;
    order: {
        orderId: number;
        products: Array<{
            name: string;
            price: number;
            quantity?: number;
        }>;
    } | null;
}

const OrderDetailsModal: React.FC<OrderDetailsModalProps> = ({ open, onClose, order }) => {
    return (
        <Modal
            open={open}
            onClose={onClose}
            aria-labelledby="order-details-modal"
            aria-describedby="order-products-list"
        >
            <Box sx={modalStyle}>
                <Typography variant="h6" component="h2" gutterBottom>
                    Order details #{order?.orderId}
                </Typography>

                {order?.products?.length ? (
                    <>
                        <Typography variant="subtitle1" gutterBottom>
                            Товары:
                        </Typography>
                        <Box sx={{ maxHeight: 300, overflow: 'auto' }}>
                            {order.products.map((product, index) => (
                                <Box key={index} sx={{ mb: 2, p: 1, borderBottom: '1px' + //NOSONAR
                                        ' solid #eee' }}>
                                    <Typography><strong>Name:</strong> {product.name}</Typography>
                                    <Typography><strong>Price:</strong> ${product.price}</Typography>
                                </Box>
                            ))}
                        </Box>
                        <Typography sx={{ mt: 2 }}>
                            <strong>Total:</strong> $
                            {order.products.reduce((sum, product) =>
                                sum + (product.price * (product.quantity ?? 1)), 0)}
                        </Typography>
                    </>
                ) : (
                    <Typography>No products in order</Typography>
                )}

                <Button
                    variant="contained"
                    onClick={onClose}
                    sx={{ mt: 2 }}
                >
                    Close
                </Button>
            </Box>
        </Modal>
    );
};

export default OrderDetailsModal;