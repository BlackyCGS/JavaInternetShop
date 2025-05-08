import { Box, Typography } from '@mui/material'

const Footer = () => {
    return (
        <Box
            component="footer"
            sx={{
                py: 2,
                px: 4,
                mt: 'auto',
                backgroundColor: 'grey.200',
                textAlign: 'center'
            }}
        >
            <Typography variant="body2" color="text.secondary">
                © 2025 TechStore. No Rights Reserved.
            </Typography>
        </Box>
    )
}

export default Footer
