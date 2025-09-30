# Chatbot UI

This is a modern, responsive frontend for the Quarkus AI Chatbot application, built with React, TypeScript, and Vite.

## Features

-   **Real-time Streaming:** Connects to the backend's Server-Sent Events (SSE) endpoints to display AI responses as they are generated.
-   **TypeScript:** Type-safe and maintainable codebase.
-   **React & Vite:** Fast development experience and optimized builds.
-   **Tailwind CSS:** A utility-first CSS framework for rapid, custom styling.
-   **Markdown Support:** Renders the AI's responses, which may include Markdown formatting.

## Prerequisites

-   Node.js (v18 or later)
-   npm or yarn
-   The main backend application (`conversation-ai-ws`) must be running.

## Getting Started

1.  **Navigate to the UI directory:**

    ```bash
    cd chatbot-ui
    ```

2.  **Install dependencies:**

    ```bash
    npm install
    ```

3.  **Run the development server:**

    ```bash
    npm run dev
    ```

    The application will be available at `http://localhost:3000` (or another port if 3000 is busy).

## Building for Production

To create an optimized production build:

```bash
npm run build