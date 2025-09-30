import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import {CssBaseline, createTheme, ThemeProvider} from "@mui/material";
import {grey, teal} from "@mui/material/colors";

const theme = createTheme({
    palette: {
        primary: {
            main: teal[700],
            contrastText: '#ffffff',
        },
        secondary: {
            main: grey[600],
        },
        background: {
            default: '#29292d',
        },
    },
});

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <ThemeProvider theme={theme}>
            <CssBaseline/>
            <App/>
        </ThemeProvider>
    </React.StrictMode>,
)