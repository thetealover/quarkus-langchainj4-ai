/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            colors: {
                'dark-bg': '#1a1a1a',
                'dark-card': '#2a2a2a',
                'dark-input': '#3a3a3a',
                'accent': '#6a45d7',
            }
        },
    },
    plugins: [],
}