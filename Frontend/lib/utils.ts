import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"
import axios from 'axios';

const baseURL = process.env.NEXT_PUBLIC_API_URL;

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
  baseURL: baseURL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
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

export const login = async (username: string, password: string) => {
  try {
    const response = await axiosInstance.post('/api/login', {
      username,
      password,
    }, {
      withCredentials: true,
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      }
    });

    if (response.status === 200) {
      const { accessToken: token, expiresIn } = response.data;
      const expirationTime = Date.now() + expiresIn * 1000;
      sessionStorage.setItem('jwt_token', token);
      sessionStorage.setItem('token_expiry', expirationTime.toString());
    }
    return response;
  } catch (error) {
    console.error('Error during login:', error);
    throw error;
  }
};
