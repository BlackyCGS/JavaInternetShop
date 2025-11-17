// src/pages/ProductPage.tsx
import { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import {
    Box,
    Typography,
    Chip,
    Button,
    CircularProgress,
    Container,
    Paper,
    Stack,
    Divider,
    Rating,
    TextField,
    IconButton,
    Snackbar,
    Alert,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit'; // Import EditIcon
import DeleteIcon from '@mui/icons-material/Delete';
import { fetchProductById, addReview, deleteReview, updateProduct } from '../api/ProductsApi';
import { Product, Review, ReviewRequest } from '../types/Product';
import { addProductToCart } from '../api/OrdersApi';
import { useAuth } from '../hooks/useAuth';
import { useCart } from '../hooks/useCart';
import UpdateProductModal from '../modals/UpdateProductModal';

const ProductPage = () => {
    const { id } = useParams();
    const [product, setProduct] = useState<Product | null>(null);
    const [reviews, setReviews] = useState<Review[]>([]);
    const [loading, setLoading] = useState(false);
    const [pageLoading, setPageLoading] = useState(true);
    const [isInCart, setIsInCart] = useState(false);
    const [reviewForm, setReviewForm] = useState<ReviewRequest>({ title: '', text: '', rating: 0 });
    const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({
        open: false,
        message: '',
        severity: 'success',
    });
    const [modalOpen, setModalOpen] = useState(false); // State for modal visibility

    const navigate = useNavigate();
    const { user } = useAuth();
    const { updateCartCount, cartItems } = useCart();

    useEffect(() => {
        const loadProduct = async () => {
            if (loading) return;
            try {
                setLoading(true);
                const productData = await fetchProductById(Number(id));
                setProduct(productData);
                setReviews(productData.reviews || []);
                setIsInCart(cartItems.includes(Number(id)));
            } catch (error: any) {
                console.error('Error loading product:', error.response?.data || error.message);
                setSnackbar({ open: true, message: 'Failed to load product', severity: 'error' });
            } finally {
                setLoading(false);
                setPageLoading(false);
            }
        };
        loadProduct();
    }, [id, cartItems]);

    const handleAddToCart = async (productId: number) => {
        try {
            await addProductToCart(productId);
            updateCartCount();
            setIsInCart(true);
            setSnackbar({ open: true, message: 'Added to cart', severity: 'success' });
        } catch (error: any) {
            console.error('Error adding to cart:', error.response?.data || error.message);
            setSnackbar({ open: true, message: 'Failed to add to cart', severity: 'error' });
        }
    };

    const handleReviewSubmit = async () => {
        if (!user || !product) {
            setSnackbar({ open: true, message: 'Please log in to submit a review', severity: 'error' });
            return;
        }
        try {
            const updatedProduct = await addReview(product.id, reviewForm);
            setProduct(updatedProduct);
            setReviews(updatedProduct.reviews || []);
            setReviewForm({ title: '', text: '', rating: 0 });
            setSnackbar({ open: true, message: 'Review added successfully', severity: 'success' });
        } catch (error: any) {
            console.error('Error adding review:', error.response?.data || error.message);
            setSnackbar({
                open: true,
                message: error.response?.data?.message || 'Failed to add review',
                severity: 'error',
            });
        }
    };

    const handleReviewDelete = async () => {
        if (!user || !product) {
            setSnackbar({ open: true, message: 'Please log in to delete a review', severity: 'error' });
            return;
        }
        try {
            await deleteReview(product.id, user.name);
            const updatedProduct = await fetchProductById(product.id);
            setProduct(updatedProduct);
            setReviews(updatedProduct.reviews || []);
            setSnackbar({ open: true, message: 'Review deleted successfully', severity: 'success' });
        } catch (error: any) {
            console.error('Error deleting review:', error.response?.data || error.message);
            setSnackbar({
                open: true,
                message: error.response?.data?.message || 'Failed to delete review',
                severity: 'error',
            });
        }
    };

    const handleUpdateProduct = async (updatedProduct: Product) => {
        try {
            await updateProduct(updatedProduct); // Call API to
            // update product
            setProduct(updatedProduct); // Update local state with the saved product
            setSnackbar({ open: true, message: 'Product updated successfully', severity: 'success' });
        } catch (error: any) {
            console.error('Error updating product:', error.response?.data || error.message);
            setSnackbar({
                open: true,
                message: error.response?.data?.message || 'Failed to update product',
                severity: 'error',
            });
        }
    };

    if (pageLoading) {
        return (
            <Box display="flex" justifyContent="center" mt={10}>
                <CircularProgress size={60} />
            </Box>
        );
    }

    if (!product) {
        return (
            <Container maxWidth="lg" sx={{ py: 4 }}>
                <Typography variant="h5">Product not found</Typography>
            </Container>
        );
    }

    const { name, price, rating, ratingAmount, gpu, motherboard, stock } = product;

    return (
        <Container maxWidth="lg" sx={{ py: 4 }}>
            <Button component={Link} to="/" variant="outlined" sx={{ mb: 3, borderRadius: 2 }}>
                Back to list
            </Button>
            <Paper elevation={0} sx={{ p: 4, borderRadius: 3 }}>
                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <Typography variant="h4" gutterBottom sx={{ fontWeight: 600 }}>
                        {name}
                    </Typography>
                    {user && ['ADMIN'].includes(user.role) && (
                        <IconButton onClick={() => setModalOpen(true)} color="primary">
                            <EditIcon />
                        </IconButton>
                    )}
                </Box>
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mb: 2 }}>
                    <Rating value={rating} readOnly precision={0.5} />
                    <Typography variant="caption" color="text.secondary">
                        ({ratingAmount} reviews)
                    </Typography>
                </Box>
                <Typography variant="h3" sx={{ mb: 2, color: 'primary.main', fontWeight: 700 }}>
                    ${price.toLocaleString()}
                </Typography>
                <Typography variant="body1" sx={{ mb: 2, color: stock > 0 ? 'text.primary' : 'error.main' }}>
                    Stock: {stock > 0 ? stock : 'Out of stock'}
                </Typography>
                {gpu && (
                    <Box sx={{ mb: 4 }}>
                        <Typography variant="h5" gutterBottom sx={{ fontWeight: 500 }}>
                            GPU specs
                        </Typography>
                        <Divider sx={{ mb: 2 }} />
                        <Stack direction="row" spacing={1} sx={{ flexWrap: 'wrap', gap: 1 }}>
                            <Chip label={`Producer: ${gpu.producer}`} />
                            <Chip label={`VRAM: ${gpu.vram}GB`} />
                            <Chip label={`Boost clock: ${gpu.boostClock}MHz`} />
                            <Chip label={`TDP: ${gpu.tdp}W`} />
                            <Chip label={`HDMI: ${gpu.hdmi}`} />
                            <Chip label={`DisplayPort: ${gpu.displayPort}`} />
                        </Stack>
                    </Box>
                )}
                {motherboard && (
                    <Box>
                        <Typography variant="h5" gutterBottom sx={{ fontWeight: 500 }}>
                            Motherboard specs
                        </Typography>
                        <Divider sx={{ mb: 2 }} />
                        <Stack direction="row" spacing={1} sx={{ flexWrap: 'wrap', gap: 1 }}>
                            <Chip label={`Producer: ${motherboard.producer}`} />
                            <Chip label={`Socket: ${motherboard.socket}`} />
                            <Chip label={`Chipset: ${motherboard.chipset}`} />
                            <Chip label={`Form factor: ${motherboard.formFactor}`} />
                            <Chip label={`RAM slots: ${motherboard.ramSlots}`} />
                            <Chip label={`SATA: ${motherboard.sata}`} />
                        </Stack>
                    </Box>
                )}
                {user ? (
                    <Button
                        variant="contained"
                        size="large"
                        fullWidth
                        sx={{ mt: 4, py: 2, borderRadius: 2, fontSize: '1.1rem' }}
                        onClick={() => handleAddToCart(product.id)}
                        disabled={isInCart || stock === 0}
                    >
                        {isInCart ? 'Already in Cart' : stock === 0 ? 'Out of Stock' : 'Add to Cart'}
                    </Button>
                ) : (
                    <Button
                        variant="contained"
                        size="large"
                        fullWidth
                        sx={{ mt: 4, py: 2, borderRadius: 2, fontSize: '1.1rem' }}
                        onClick={() => navigate('/login')}
                    >
                        Log in to add Products to cart
                    </Button>
                )}
                {user && (
                    <Box sx={{ mt: 4 }}>
                        <Typography variant="h5" gutterBottom sx={{ fontWeight: 500 }}>
                            Add a Review
                        </Typography>
                        <Divider sx={{ mb: 2 }} />
                        <Stack spacing={2}>
                            <TextField
                                label="Title"
                                fullWidth
                                value={reviewForm.title}
                                onChange={(e) => setReviewForm({ ...reviewForm, title: e.target.value })}
                            />
                            <TextField
                                label="Review"
                                fullWidth
                                multiline
                                rows={4}
                                value={reviewForm.text}
                                onChange={(e) => setReviewForm({ ...reviewForm, text: e.target.value })}
                            />
                            <Rating
                                value={reviewForm.rating}
                                onChange={(_, value) => setReviewForm({ ...reviewForm, rating: value || 0 })}
                                precision={0.5}
                            />
                            <Button
                                variant="contained"
                                onClick={handleReviewSubmit}
                                disabled={!reviewForm.title || !reviewForm.text || reviewForm.rating === 0}
                            >
                                Submit Review
                            </Button>
                        </Stack>
                    </Box>
                )}
                <Box sx={{ mt: 4 }}>
                    <Typography variant="h5" gutterBottom sx={{ fontWeight: 500 }}>
                        Reviews
                    </Typography>
                    <Divider sx={{ mb: 2 }} />
                    {reviews.length === 0 ? (
                        <Typography color="text.secondary">No reviews yet.</Typography>
                    ) : (
                        <Stack spacing={2}>
                            {reviews.map((review) => (
                                <Paper key={review.id} sx={{ p: 2, borderRadius: 2 }}>
                                    <Stack direction="row" justifyContent="space-between" alignItems="center">
                                        <Typography variant="h6">{review.title}</Typography>
                                        {(user?.name === review.username || ['ADMIN'].includes(user?.role as string)) && (
                                            <IconButton onClick={() => handleReviewDelete()}>
                                                <DeleteIcon />
                                            </IconButton>
                                        )}
                                    </Stack>
                                    <Rating value={review.rating} readOnly precision={0.5} size="small" />
                                    <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
                                        By {review.username}
                                    </Typography>
                                    <Typography variant="body1" sx={{ mt: 1 }}>
                                        {review.text}
                                    </Typography>
                                </Paper>
                            ))}
                        </Stack>
                    )}
                </Box>
            </Paper>
            <Snackbar
                open={snackbar.open}
                autoHideDuration={6000}
                onClose={() => setSnackbar({ ...snackbar, open: false })}
            >
                <Alert
                    onClose={() => setSnackbar({ ...snackbar, open: false })}
                    severity={snackbar.severity}
                    sx={{ width: '100%' }}
                >
                    {snackbar.message}
                </Alert>
            </Snackbar>
            {product && (
                <UpdateProductModal
                    open={modalOpen}
                    onClose={() => setModalOpen(false)}
                    product={product}
                    onSave={handleUpdateProduct}
                />
            )}
        </Container>
    );
};

export default ProductPage;