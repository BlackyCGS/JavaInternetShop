import axios from 'axios'
import {Order} from "../types/Order.ts"
import {ProductItem} from "../types/ProductItem.ts";

export const fetchAllOrders = async (pageNumber = 0, pageSize = 10, status: string = "all"): Promise<Order[]> => {
    const response = await axios.get('api/orders/list', {
        params: {
            pageNumber,
            pageSize,
            status,
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

export const getTotalOrders = async (status: string = "all") => {
    const response = await axios.get('api/orders/amount', {
        params: {
            status
        },
    })
    return response.data
}

export const addProductToCart = async (productId: number): Promise<void> => {
    const response = await axios.post(`/api/orders/cart/${productId}`, {
        withCredentials: true
    })
    localStorage.setItem("cartItems", await localStoragePrep(response.data.products))
}

export const changeCartQuantity = async (productId: number, quantity: number): Promise<Order> => {
    const response = await axios.post(`/api/orders/cart/${productId}?quantity=${quantity}`, {
        withCredentials: true
    })
    localStorage.setItem("cartItems", await localStoragePrep(response.data.products))
    return response.data;
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

export const deleteProductFromCart = async (productId:number): Promise<Order> => {
    const response: Order = await axios.delete(`/api/orders/cart/${productId}`, {
        withCredentials: true
    })

    localStorage.setItem("cartItems", await localStoragePrep(response.products))
    return response
}

export const toOrder = async () => {
    const response: Order = await axios.put(`/api/orders/cart/toOrder`, null, {
        withCredentials: true
    })
    localStorage.removeItem("cartItems")
    return response
}

export const getUserOrders = async () => {
    const orders = await axios.get('api/orders/myOrders', {
        withCredentials: true
    })
    return orders.data
}

export const localStoragePrep: (productItems: ProductItem[]) => Promise<string> = async (productItems: ProductItem[]) => {
    const productIds: number[] = []
    for(const productItem of productItems) {
        productIds.push(productItem.product.id)
    }

    return productIds.toString()
}