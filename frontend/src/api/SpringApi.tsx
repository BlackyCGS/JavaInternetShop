import axios from 'axios';

const springApi = axios.create({
    baseURL: '/api',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    }
});

export const springClient = {
    getProducts: () => springApi.get('/products'),
    createProduct: (product: { name: string; price: number }) =>
        springApi.post('/products', product)
};

// Типы для TypeScript
export interface Product {
    id: number;
    name: string;
    price: number;
}