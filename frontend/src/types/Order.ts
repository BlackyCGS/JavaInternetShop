import { ProductItem } from './ProductItem'

export interface Order {
    orderId: number
    orderStatus: number
    userId: number
    products: ProductItem[]
}
