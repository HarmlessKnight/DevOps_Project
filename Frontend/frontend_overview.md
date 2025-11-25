# Frontend Overview

This document provides an overview of the frontend architecture, authentication, data flow, and components of the application.

## Project Structure

- **`/app`**: Contains the core application logic, including pages, layouts, and context providers.
  - **`/app/context/DataContext.tsx`**: Provides a React context for managing dashboard data.
  - **`/app/dashboard`**: Contains the dashboard page and its components.
  - **`/app/login`**: Contains the login page.
  - **`/app/register`**: Contains the registration page.
- **`/components`**: Contains reusable React components.
  - **`/components/ui`**: Contains UI components from shadcn/ui.
  - **`/components/login-form.tsx`**: The login form component.
  - **`/components/register-form.tsx`**: The registration form component.
- **`/hooks`**: Contains custom React hooks.
- **`/lib`**: Contains utility functions.
  - **`/lib/utils.ts`**: Contains utility functions, including the `axiosInstance` for making API requests.
- **`/models`**: Contains data models for the application.
- **`/public`**: Contains static assets, such as fonts and images.

## Key Technologies

- **Next.js**: A React framework for building server-side rendered and statically generated web applications.
- **TypeScript**: A typed superset of JavaScript that compiles to plain JavaScript.
- **Tailwind CSS**: A utility-first CSS framework for rapidly building custom designs.
- **shadcn/ui**: A collection of reusable UI components.
- **axios**: A promise-based HTTP client for the browser and Node.js.
- **Caddy**: A powerful, enterprise-ready, open source web server with automatic HTTPS.

## Authentication

- Authentication is handled using JWTs (JSON Web Tokens).
- The `login` function in `lib/utils.ts` sends a POST request to the `/api/login` endpoint with the user's credentials.
- The server responds with a JWT, which is stored in `sessionStorage`.
- The `axiosInstance` is configured to automatically send the JWT in the `Authorization` header of all subsequent requests.
- The `logoutApi` function in `lib/utils.ts` sends a POST request to the `/api/logout` endpoint to invalidate the JWT.
- The `DashboardProvider` in `app/context/DataContext.tsx` ensures that only authenticated users can access the dashboard. It checks for a valid token in `sessionStorage` and redirects unauthenticated users to the login page.

## Data Flow

1.  The `DashboardProvider` in `app/context/DataContext.tsx` is responsible for fetching and managing dashboard data.
2.  When the `DashboardProvider` mounts, it checks for a valid JWT in `sessionStorage`.
3.  If a valid token is found, it makes a GET request to the `/api/dashboard` endpoint to fetch the dashboard data.
4.  The `useDashboard` hook provides access to the dashboard data and loading state to any component that needs it.
5.  The `DashboardPage` component uses the `useDashboard` hook to get the dashboard data and display it to the user.

## Components

- **`AppSidebar`**: Provides navigation between different sections of the application.
- **`SiteHeader`**: Displays the page title and user information.
- **`SectionCards`**: Displays a grid of cards with account information.
- **`DataTable`**: Displays a table of data with sorting, filtering, and pagination.
- **`LoginForm`**: A form for users to log in to the application.
- **`RegisterForm`**: A form for users to create a new account.
