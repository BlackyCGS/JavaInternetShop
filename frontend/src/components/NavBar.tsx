import {
    AppBar, Toolbar, Button, Box, IconButton, Menu, MenuItem, Avatar, Divider, Badge, InputBase
} from '@mui/material'
import {
    Search, ShoppingCart, AccountCircle
} from '@mui/icons-material'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth'
import {useState} from "react";
import {useCart} from "../hooks/useCart.tsx";

const NavBar = () => {
    const { user, logout } = useAuth()
    const { cartCount } = useCart();
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
        navigate("/")
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
                    {/*<Typography
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
                    </Typography>*/}
                    <Link to="/" style={{ textDecoration: 'none', color: 'inherit' }}>
                        <IconButton

                            color="inherit"   sx={{
                            borderRadius: 2,       // Прямоугольная кнопка
                            overflow: 'hidden',
                        }} TouchRippleProps={{center: false}} >
                            <Badge color="error">
                                <img src="/NavBarIcon.png" alt="Icon" style={{height: "60px"}}/>
                            </Badge>
                        </IconButton>
                    </Link>

                    <Box sx={{ display: { xs: 'none', md: 'flex' }, gap: 2 }}>
                        <Button
                            component={Link}
                            to="/gpus"
                            color="inherit"
                            sx={{ textTransform: 'none' }}
                        >
                            Gpus
                        </Button>
                        <Button
                            component={Link}
                            to="/motherboards"
                            color="inherit"
                            sx={{ textTransform: 'none' }}
                        >
                            Motherboards
                        </Button>
                        <Button
                            component={Link}
                            to="/cpus"
                            color="inherit"
                            sx={{ textTransform: 'none' }}
                        >
                            Cpus
                        </Button>
                        <Button
                            component={Link}
                            to="/psus"
                            color="inherit"
                            sx={{ textTransform: 'none' }}
                        >
                            Psu
                        </Button>
                        <Button
                            component={Link}
                            to="/hdds"
                            color="inherit"
                            sx={{ textTransform: 'none' }}
                        >
                            HDD
                        </Button>
                        <Button
                            component={Link}
                            to="/ssds"
                            color="inherit"
                            sx={{ textTransform: 'none' }}
                        >
                            SSD
                        </Button>
                        <Button
                            component={Link}
                            to="/ram"
                            color="inherit"
                            sx={{ textTransform: 'none' }}
                        >
                            RAM
                        </Button>
                        <Button
                            component={Link}
                            to="/pcCases"
                            color="inherit"
                            sx={{ textTransform: 'none' }}
                        >
                            PcCases
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
                            placeholder="Search..."
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                            sx={{ width: 200 }}
                        />
                    </Box>

                    <Link to="/cart" style={{ textDecoration: 'none', color: 'inherit' }}>
                        <IconButton color="inherit">
                            <Badge badgeContent={cartCount} color="error">
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
                                <MenuItem onClick={handleLogout}>Log out</MenuItem>
                            </>
                        ) : (
                            <MenuItem component={Link} to="/login" onClick={handleClose}>Log in</MenuItem>
                        )}
                    </Menu>
                </Box>
            </Toolbar>
        </AppBar>
    )
}

export default NavBar
