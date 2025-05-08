import axios from 'axios'

export const apiLogout = async () => {
    axios.post('api/auth/logout', {
        withCredentials: true
    })
}