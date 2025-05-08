import { Box } from '@mui/material'
import ProductCard from './ProductCard'
import { Product } from '../types/Product.ts'

interface ProductListProps {
    products: Product[]
}

const ProductList = ({ products }: ProductListProps) => {
    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            {products.map((product) => (
                <ProductCard key={product.id} product={product} />
            ))}
        </Box>
    )
}

export default ProductList