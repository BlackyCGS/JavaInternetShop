import { useState } from 'react'
import {Link, useNavigate} from 'react-router-dom'
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

const RegisterPage = () => {
    const navigate = useNavigate()
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [name, setName] = useState('')
    const [errors, setErrors] = useState({
        email: '',
        password: '',
        name: '',
        form: ''
    })
    const [loading, setLoading] = useState(false)
    const validateEmail = (email: string) => {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$///NOSONAR
        return re.test(email)
    }

    const validateForm = () => {
        let valid = true
        const newErrors = {
            email: '',
            password: '',
            name: '',
            form: ''
        }

        if (!email) {
            newErrors.email = 'Email is required'
            valid = false
        } else if (!validateEmail(email)) {
            newErrors.email = 'Enter correct email address'
            valid = false
        }

        if (!name) {
            newErrors.name = 'Name is required'
            valid = false
        } else if (name.length < 3) {
            newErrors.name = 'Name must have 3 characters or more'
            valid = false
        }

        if (!password) {
            newErrors.password = 'Password is required'//NOSONAR
            valid = false
        } else if (password.length < 6) {
            newErrors.password = 'Password must have 6 characters or more'//NOSONAR
            valid = false
        }

        setErrors(newErrors)
        return valid
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()

        if (!validateForm()) return
        if (loading) return
        try {
            setLoading(true)
            await axios.post(
                '/api/auth/signup',
                { email, name, password },
                { withCredentials: true }
            )

            navigate('/login')
        } catch (err: any) {
            setErrors(prev => ({
                ...prev,
                form: err.response?.data?.message || 'Ошибка регистрации'
            }))
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
                <Paper elevation={3} sx={{ p: 4, borderRadius: 3 }}>
                    <Typography
                        variant="h4"
                        component="h1"
                        gutterBottom
                        align="center"
                        fontWeight={600}
                    >
                        Register
                    </Typography>

                    {errors.form && (
                        <Alert severity="error" sx={{ mb: 2 }}>
                            {errors.form}
                        </Alert>
                    )}

                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 2 }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            label="Email"
                            autoComplete="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            error={!!errors.email}
                            helperText={errors.email}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            label="User name"
                            autoComplete="username"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            error={!!errors.name}
                            helperText={errors.name}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            label="Password"
                            type="password"
                            autoComplete="new-password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            error={!!errors.password}
                            helperText={errors.password}
                        />

                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, py: 1.5 }}
                            disabled={!email || !name || !password}
                        >
                            Register
                        </Button>

                        <Box sx={{ textAlign: 'center' }}>
                            <Button
                                component={Link}
                                to="/login"
                                /*variant="contained"*/
                                fullWidth
                                color="inherit"
                                sx={{mt:3,  py:1.5, alignSelf: 'center', border: 1 }
                                }>
                                Already have an account? Log in
                            </Button>
                        </Box>
                    </Box>
                </Paper>
            </Box>
        </Container>
    )
}

export default RegisterPage