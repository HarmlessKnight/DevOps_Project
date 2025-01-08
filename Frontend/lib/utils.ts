import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"
import axios from 'axios';

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export const getToken = () => {
  const token = sessionStorage.getItem('jwt_token');
  const expirationTime = sessionStorage.getItem('token_expiry');
  
  if (!token || !expirationTime) {
    return null; 
  }

  const currentTime = Date.now();
  const tokenExpiry = parseInt(expirationTime, 10); 
  
  if (currentTime > tokenExpiry) {
    sessionStorage.removeItem('jwt_token');
    sessionStorage.removeItem('token_expiry');
    return null;  
  }

  return token;  
}


export const remove_token = () => {
  sessionStorage.removeItem('jwt_token');
  sessionStorage.removeItem('token_expiry');
}

export const logoutApi = async () => {
  const token = getToken();
  
  if (token) {
    try {
      await axiosInstance.post('/logout', {}, { headers: { Authorization: `Bearer ${token}` } });
      
      remove_token(); 
      
      if (typeof window !== 'undefined') {
        window.location.href = '/login';
      }

    } catch (error) {
      
      console.error('Error during logout API call:', error);
      
      remove_token(); 
      
      if (typeof window !== 'undefined') {
        window.location.href = '/login'; 
      }
    }
  }
}



export const axiosInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL || 'http://localhost/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    
    if (error.response && error.response.status === 401) {
      remove_token();
      window.location.href = '/login';  
    }
    return Promise.reject(error);
  }
);
