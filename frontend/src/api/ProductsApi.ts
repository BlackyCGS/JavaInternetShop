import axios from 'axios'
import { Product } from '../types/Product.ts'

const API_URL = 'api/products'

export const fetchProducts = async (pageNumber = 0, pageSize = 10): Promise<Product[]> => {
    const response = await axios.get(API_URL, {
        params: {
            pageNumber,
            pageSize
        }
    })
    return response.data
}

export const getTotalProducts = async (category : string, name: string ): Promise<number> => {
    const response = await axios.get('api/products/amount', {
        params: {
            category,
            name
        }
    })
    return response.data
}


export const fetchProductById = async (id: number): Promise<Product> => {
    const response = await axios.get(`/api/products/${id}`)
    return response.data
}

export const fetchGpuProducts = async (pageNumber: number, pageSize: number) => {
    const response = await axios.get('/api/products/category/gpu',
        {
            params: {
                    pageNumber,
                    pageSize

            }
        })
    return response.data
}

export const fetchMotherboardProducts = async (pageNumber: number, pageSize: number) => {
    const response = await axios.get('/api/products/category/motherboard', {
        params: {
                pageNumber,
                pageSize
        }
    })
    return response.data
}

export const updateProduct = async (product: Product): Promise<void> => {
    await axios.put('/api/products/', product, {

    })
}

export const createProduct = async (product: Product): Promise<void> => {
    await axios.post('/api/products', product, {

    })
}

export const deleteProduct = async (id: number): Promise<void> => {
    await axios.delete(`/api/products/delete/${id}`)
}

export const fetchSearchedProducts = async (pageNumber: number, pageSize: number, name: string) => {
    const response = await axios.get('/api/products/name', {
        params: {
            pageNumber,
            pageSize,
            name
        }
    })
    return response.data
}
