# Project Overview and Caddy Configuration Fix

This document provides an overview of the project's frontend architecture, an analysis of the Caddy configuration issue, and the steps taken to resolve it.

## Frontend Architecture

The frontend is a [Next.js](https://nextjs.org/) application. It uses a component-based architecture with a clear separation of concerns.

- **`app/`**: This directory contains the main application logic, including pages, layouts, and contexts.
- **`components/`**: This directory contains reusable UI components.
- **`lib/`**: This directory contains utility functions, including a centralized `axios` instance for making API calls.
- **`public/`**: This directory contains static assets like fonts.

### Backend Communication

The frontend communicates with the backend via a REST API. 

- **API Client**: The frontend uses the `axios` library to make HTTP requests to the backend. A centralized `axios` instance is configured in `lib/utils.ts`.
- **Base URL**: The base URL for the API is configured using the `NEXT_PUBLIC_API_URL` environment variable. This is a good practice as it allows the API endpoint to be easily changed between different environments (development, staging, production).
- **API Routes**: The frontend makes calls to relative API routes like `/api/dashboard` and `/api/login`.

## Caddy Configuration Issue

The original `Caddyfile` was configured to serve the site over HTTP, not HTTPS.

**Original `Caddyfile`:**
```caddy
http://localhost

log {
    output stdout
    format console
}

reverse_proxy /api/* http://localhost:8080
reverse_proxy /* http://localhost:3000
```

When you tried to change `http://localhost` to `https://localhost` to enable HTTPS, you encountered issues. The dashboard would not load data, and the login would fail.

The root cause of this issue is a **mixed content error**. When the frontend is loaded over HTTPS, browsers block any requests made to HTTP endpoints for security reasons. 

Your frontend was likely configured with `NEXT_PUBLIC_API_URL` set to `http://localhost:8080`. When the frontend, loaded from `https://localhost`, tried to make a request to `http://localhost:8080/api/dashboard`, the browser blocked it.

## Solution

The solution involves two steps:

1.  **Correct the `Caddyfile` to properly handle HTTPS for local development.**
2.  **Ensure the frontend makes API calls to the same origin as the website.**

### 1. Corrected `Caddyfile`

The `Caddyfile` has been updated to the following:

```caddy
{
    # Global options block
    log {
        output stdout
        format console
    }
    # Tell Caddy to issue a self-signed certificate for localhost
    # because it's not a public domain.
    # In a production environment with a real domain, Caddy would
    # automatically get a certificate from Let's Encrypt.
    tls internal
}

localhost {
    # Reverse proxy API requests to the backend service
    reverse_proxy /api/* http://localhost:8080

    # Reverse proxy all other requests to the frontend service
    reverse_proxy /* http://localhost:3000
}
```

**Changes:**

- **`tls internal`**: This directive tells Caddy to generate a self-signed certificate for `localhost`. This is necessary for local development because `localhost` is not a public domain.
- **`localhost` block**: The site address is now `localhost`, and Caddy will serve it over HTTPS by default. The `reverse_proxy` directives are placed inside this block.

With this configuration, Caddy will:
- Listen on `https://localhost`.
- Proxy all requests starting with `/api/` to your backend service at `http://localhost:8080`.
- Proxy all other requests to your Next.js frontend at `http://localhost:3000`.

### 2. Frontend Configuration

To ensure the frontend makes API calls to the correct endpoint, you need to make sure that the `NEXT_PUBLIC_API_URL` environment variable is **not set** or is set to an **empty string**.

When the `baseURL` in the `axios` configuration is empty, `axios` will use the current window's origin for any relative URLs. This means that when you access the site at `https://localhost`, the API calls will be correctly sent to `https://localhost/api/...`.

**How to run your frontend:**

When you start your Next.js development server, make sure you are not setting the `NEXT_PUBLIC_API_URL` environment variable.

For example, if you have a `package.json` script like this:

```json
"scripts": {
  "dev": "next dev"
}
```

You can just run `npm run dev`.

If you were previously setting the variable like this:

```bash
NEXT_PUBLIC_API_URL=http://localhost:8080 npm run dev
```

You should now run it without the environment variable:

```bash
npm run dev
```

By following these steps, Caddy will handle HTTPS termination, and your frontend will make API calls to the correct endpoints, resolving the mixed content issue.
