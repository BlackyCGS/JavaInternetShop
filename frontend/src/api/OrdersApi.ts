import axios from 'axios'
import { Order } from "../types/Order.ts"
import {Product} from "./SpringApi.tsx";

export const fetchAllOrders = async (pageNumber = 0, pageSize = 10): Promise<Order[]> => {
    const response= await axios.get('api/orders/list', {
        params: {
            pageNumber,
            pageSize,
        },
        withCredentials: true
    },)
    return response.data
}

export const changeOrderStatus = async (orderId: number, status: string): Promise<void> => {
    await axios.put(`api/orders/${orderId}/changeStatus`, null,
        {
        params: {
            status
        },
    })
}

export const getTotalOrders = async () => {
    const response = await axios.get('api/orders/amount', {
        withCredentials: true
    })
    return response.data
}

export const addProductToCart = async (productId: number): Promise<void> => {
    const response = await axios.post(`/api/orders/cart/${productId}`, {
        withCredentials: true
    })
    localStorage.setItem("cartItems", await localStoragePrep(response.data.products))
}

export const getCartById: () => Promise<Order> = async (): Promise<Order> => {
    const response = await axios.get(`/api/orders/cart`, {
        withCredentials: true
    })
    if(response.data.products === undefined) {
        response.data.products = [];
    }
    localStorage.setItem("cartItems", await localStoragePrep(response.data.products))
    return response.data
}

export const deleteProductFromCart = async (productId:number): Promise<void> => {
    const response = await axios.delete(`/api/orders/cart/${productId}`, {
        withCredentials: true
    })

    localStorage.setItem("cartItems", await localStoragePrep(response.data.products))
    return response.data
}

export const toOrder = async () => {
    const response = await axios.put(`/api/orders/cart/toOrder`, null, {
        withCredentials: true
    })
    localStorage.removeItem("cartItems")
    return response.data
}

export const getUserOrders = async () => {
    const response = await axios.get('api/orders/myOrders', {
        withCredentials: true
    })
    const orders: Order[] = response.data
    return orders
}

export const localStoragePrep: (products: Product[]) => Promise<string> = async (products: Product[]) => {
    const productIds: number[] = []

    for(const product of products) {
        productIds.push(product.id)
    }

    return productIds.toString()
}