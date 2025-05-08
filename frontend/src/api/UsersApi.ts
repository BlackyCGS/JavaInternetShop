import axios from "axios";
import { User } from "../types/User.ts";

export const fetchAllUsers = async (pageNumber: number, pageSize:number): Promise<User[]> => {
    const response = await axios.get('api/users/list', {
            params: {
                pageNumber,
                pageSize
            },
        withCredentials: true
        })
    return response.data
}

export const changeUserRole = async (userId: number, role: string) => {
    await axios.put(`api/users/role/${userId}/${role}`,{
        withCredentials: true
    })
}

export const getTotalUsers = async () => {
    const response = await axios.get('api/users/amount', {})
    return response.data
}
