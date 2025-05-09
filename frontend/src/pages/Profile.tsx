import { useEffect, useState } from 'react'
import axios from 'axios'
import { User } from '../types/User'
import {
    Box,
    Container,
    Typography,
    Paper,
    CircularProgress
} from '@mui/material'

const Profile = () => {
    const [user, setUser] = useState<User | null>(null)
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        if(loading) return
        try{
            setLoading(true)
        axios.get('/api/users/profile', { withCredentials: true })
            .then(res => setUser(res.data))
            .catch(err => console.error(err))
        }
        catch(err){
        console.error(err)
        }
        finally {
            setLoading(false)
        }
    }, [])

    if (!user) {
        return (
            <Box display="flex" justifyContent="center" alignItems="center" minHeight="50vh">
                <CircularProgress size={60} />
            </Box>
        )
    }

    return (
        <Container maxWidth="sm" sx={{ py: 6 }}>
            <Typography
                variant="h4"
                gutterBottom
                sx={{ fontWeight: 600, textAlign: 'center', mb: 4 }}
            >
                Profile
            </Typography>

            <Paper elevation={3} sx={{ p: 4, borderRadius: 3 }}>
                <Typography variant="h6" gutterBottom>
                    User name:
                </Typography>
                <Typography variant="body1" sx={{ mb: 2 }}>
                    {user.name}
                </Typography>

                <Typography variant="h6" gutterBottom>
                    Email:
                </Typography>
                <Typography variant="body1">
                    {user.email}
                </Typography>
            </Paper>
        </Container>
    )
}

export default Profile
