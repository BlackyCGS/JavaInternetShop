import {
    AppBar, Toolbar, Typography, Button, Box, IconButton, Menu, MenuItem, Avatar, Divider, Badge, InputBase
} from '@mui/material'
import {
    Search, ShoppingCart, AccountCircle, KeyboardArrowDown
} from '@mui/icons-material'
import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth'

const NavBar = () => {
    const { user, logout } = useAuth()
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
    const open = Boolean(anchorEl)

    const [searchQuery, setSearchQuery] = useState(''); // новый стейт для поиска
    const navigate = useNavigate();

    const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget)
    }

    const handleClose = () => {
        setAnchorEl(null)
    }

    const handleLogout = () => {
        logout()
        handleClose()
    }

    const handleSearchSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        navigate(`/?search=${encodeURIComponent(searchQuery.trim())}`)
    }

    return (
        <AppBar position="absolute" color="default" elevation={1}>
            <Toolbar sx={{ justifyContent: 'space-between' }}>
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 4 }}>
                    <Typography
                        variant="h6"
                        component={Link}
                        to="/"
                        sx={{
                            textDecoration: 'none',
                            color: 'inherit',
                            fontWeight: 'bold'
                        }}
                    >
                        TechStore
                    </Typography>

                    <Box sx={{ display: { xs: 'none', md: 'flex' }, gap: 2 }}>
                        <Button
                            component={Link}
                            to="/gpus"
                            color="inherit"
                            endIcon={<KeyboardArrowDown />}
                            sx={{ textTransform: 'none' }}
                        >
                            Видеокарты
                        </Button>
                        <Button
                            component={Link}
                            to="/motherboards"
                            color="inherit"
                            endIcon={<KeyboardArrowDown />}
                            sx={{ textTransform: 'none' }}
                        >
                            Материнские платы
                        </Button>
                    </Box>
                </Box>

                <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
                    <Box
                        component="form"
                        onSubmit={handleSearchSubmit}
                        sx={{
                            display: { xs: 'none', sm: 'flex' },
                            alignItems: 'center',
                            backgroundColor: 'action.hover',
                            borderRadius: 1,
                            px: 2,
                            py: 0.5
                        }}
                    >
                        <Search fontSize="small" sx={{ mr: 1, color: 'text.secondary' }} />
                        <InputBase
                            placeholder="Поиск товаров..."
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                            sx={{ width: 200 }}
                        />
                    </Box>

                    <Link to="/cart" style={{ textDecoration: 'none', color: 'inherit' }}>
                        <IconButton color="inherit">
                            <Badge color="error">
                                <ShoppingCart />
                            </Badge>
                        </IconButton>
                    </Link>

                    <IconButton
                        onClick={handleMenu}
                        color="inherit"
                        sx={{ p: 0 }}
                    >
                        <Avatar sx={{ width: 32, height: 32 }}>
                            <AccountCircle />
                        </Avatar>
                    </IconButton>

                    <Menu
                        anchorEl={anchorEl}
                        open={open}
                        onClose={handleClose}
                    >
                        {user ? (
                            <>
                                <MenuItem component={Link} to="/profile" onClick={handleClose}>Profile</MenuItem>
                                <MenuItem component={Link} to="/orders" onClick={handleClose}>My Orders</MenuItem>
                                {['ADMIN', 'MERCHANT', 'DELIVERY'].includes(user.role) && (
                                    <MenuItem component={Link} to="/admin" onClick={handleClose}>
                                        Admin Tab
                                    </MenuItem>
                                )}
                                <Divider />
                                <MenuItem onClick={handleLogout}>Выйти</MenuItem>
                            </>
                        ) : (
                            <MenuItem component={Link} to="/login" onClick={handleClose}>Войти</MenuItem>
                        )}
                    </Menu>
                </Box>
            </Toolbar>
        </AppBar>
    )
}

export default NavBar
