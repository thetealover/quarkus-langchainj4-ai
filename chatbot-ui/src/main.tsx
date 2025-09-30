import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
import {teal, grey} from "@mui/material/colors";

// Create a custom theme for a nice, teal-colored UI
const theme = createTheme({
    typography: {
        fontFamily: 'Roboto, Arial, sans-serif',
    },
    palette: {
        mode: 'light',
        primary: {
            main: teal[500], // A vibrant teal for primary elements
            contrastText: '#ffffff',
        },
        background: {
            default: grey[100], // A very light grey for the page background
            paper: '#ffffff', // White for paper elements like the chat window
        },
    },
    components: {
        // Apply a global style override for the background
        MuiCssBaseline: {
            styleOverrides: {
                body: {
                    // A subtle gradient background for a more modern feel
                    background: `linear-gradient(135deg, ${grey[50]} 0%, ${teal[50]} 100%)`,
                },
            },
        },
        // Style the main chat window Paper component
        MuiPaper: {
            styleOverrides: {
                root: {
                    // Make the paper slightly transparent to show the gradient behind it
                    backgroundColor: 'rgba(255, 255, 255, 0.8)',
                    backdropFilter: 'blur(10px)', // Frosted glass effect
                    border: `1px solid ${grey[200]}`,
                },
            },
        },
        // Style the main app container
        MuiContainer: {
            styleOverrides: {
                root: {
                    // Remove default background color to let the body gradient show through
                    backgroundColor: 'transparent',
                },
            },
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