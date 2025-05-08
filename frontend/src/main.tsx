import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { CssBaseline, ThemeProvider, createTheme} from '@mui/material'
import React from 'react'

const theme = createTheme({
    palette: {
        primary: {
            main: '#1976d2',
        }
    }
})

createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
      <ThemeProvider theme={theme}>
          <CssBaseline /> {}
          <App />
      </ThemeProvider>
  </React.StrictMode>,
)
