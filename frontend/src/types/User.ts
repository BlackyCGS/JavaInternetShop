import {Order} from "./Order.ts";

export interface User {
    id: number
    name: string
    email: string
    role: string
    orders: Order[]
}