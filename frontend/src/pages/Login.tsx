import { useState } from 'react'
import {Link, useNavigate } from 'react-router-dom'
import {
    Box,
    Button,
    Container,
    TextField,
    Typography,
    Paper,
    Alert
} from '@mui/material'
import axios from 'axios'
import { useAuth } from '../hooks/useAuth'

const LoginPage = () => {
    const { refreshUser } = useAuth()
    const navigate = useNavigate()
    const [loading, setLoading] = useState(false)
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
        setError('')
        if (loading) return
        try {
            setLoading(true)
            await axios.post(
                '/api/auth/login',
                { email, password },
                { withCredentials: true }
            )

            await refreshUser() // обновляем авторизацию на фронте
            navigate('/') // редирект на главную
        } catch (err: any) {
            setError(err.response?.data?.message || 'Ошибка входа')
        }
        finally {
            setLoading(false)
        }
    }

    return (
        <Container
            maxWidth="sm"
            sx={{
                height: '100vh',
                display: 'grid',
                justifyContent: 'center',
                alignItems: 'center'
            }}
        >
            <Box sx={{ width: '100%', maxWidth: 400 }}>
            <Paper elevation={3} sx={{ p: 4, borderRadius: 3, width: '100%', maxWidth: 400, mx: 'auto' }}>
                    <Typography variant="h4" component="h1" gutterBottom align="center" fontWeight={600}>
                        Log in
                    </Typography>

                    {error && (
                        <Alert severity="error" sx={{ mb: 2 }}>
                            {error}
                        </Alert>
                    )}

                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 2 }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            label="Email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />

                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            label="Password"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />

                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, py: 1.5 }}
                        >
                            Log In
                        </Button>
                        <Button
                            component={Link}
                            to="/register"
                            /*variant="contained"*/
                            fullWidth
                            color="inherit"
                            sx={{mt:3,  py:1.5, alignSelf: 'center', border: 1 }
                        }>
                            Register
                        </Button>



                    </Box>
                </Paper>
            </Box>
        </Container>
    )
}

export default LoginPage
