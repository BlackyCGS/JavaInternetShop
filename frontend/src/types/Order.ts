import { Product } from './Product'

export interface Order {
    orderId: number
    orderStatus: number
    userId: number
    products: Product[]
}
