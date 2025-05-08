import axios from 'axios'
import { Order } from "../types/Order.ts"

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
    await axios.post(`api/orders/cart/${productId}`, {
        withCredentials: true
    })
}

export const getCartById = async (): Promise<Order> => {
    const response = await axios.get(`api/orders/cart`, {
        withCredentials: true
    })
    return response.data
}

export const deleteProductFromCart = async (productId:number): Promise<void> => {
    const response = await axios.delete(`api/orders/cart/${productId}`, {
        withCredentials: true
    })


    return response.data
}

export const toOrder = async () => {
    const response = await axios.put(`api/orders/cart/toOrder`, null, {
        withCredentials: true
    })
    return response.data
}

export const getUserOrders = async () => {
    const response = await axios.get('api/orders/myOrders', {
        withCredentials: true
    })
    const orders: Order[] = response.data
    return orders
}