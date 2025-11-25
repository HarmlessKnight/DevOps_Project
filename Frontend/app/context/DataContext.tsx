'use client';

import React, { createContext, useState, useEffect, useContext, Children } from 'react';
import { getToken, axiosInstance, remove_token, login as apiLogin } from '@/lib/utils';
import { DashboardData } from '@/models/dashboard';
import { useRouter } from 'next/navigation';

interface DashboardContextType {
  dashboardData: DashboardData | null;
  loading: boolean;
  reloadData: () => void;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
}

const DashboardContext = createContext<DashboardContextType>({
  dashboardData: null,
  loading: true,
  reloadData: () => {},
  login: async () => {},
  logout: () => {},
});

export const DashboardProvider = ({ children }: { children: React.ReactNode }) => {
  const [dashboardData, setDashboardData] = useState<DashboardData | null>(null);
  const [loading, setLoading] = useState(true);
  const router = useRouter();

  const fetchDashboard = () => {

    const token = getToken();
    if (!token) {
      router.push('/login');
      return;
    }

    setLoading(true);
    axiosInstance
      .get('/api/dashboard')
      .then((res) => {
        const data = res.data as DashboardData;
        data.accounts = data.accounts.map((a) => ({
          ...a,
          transactions: a.transactions.map((t) => ({
            ...t,
            timestamp: t.timestamp.replace(' ', 'T'),
          })),
        }));
        setDashboardData(data);
      })
      .catch((err) => {
        if (err.response?.status === 401) logout();
      })
      .finally(() => {
        setLoading(false)
        
      });
  };

  const logout = async () => {
    const token = getToken();
    if (token) {
      try {
        await axiosInstance.post('/logout', {});
      } catch (error) {
        console.error('Error during logout API call:', error);
      } finally {
        setDashboardData(null);
        remove_token();
        router.push('/login');
      }
    } else {
      setDashboardData(null);
      router.push('/login');
    }
  };

  const login = async (username: string, password: string) => {
    
    setDashboardData(null);
    await apiLogin(username, password);
    fetchDashboard();
    router.push('/dashboard');

  };

  useEffect(() => {
    const token = getToken();
    if (token) {
      fetchDashboard();
    } else {
      router.push('/login');
    }
  }, []);

  return (
    <DashboardContext.Provider value={{ dashboardData, loading, reloadData: fetchDashboard, login, logout }}>
      {children}
    </DashboardContext.Provider>
  );
};

export const AccountsProvider = ({children}:{children: React.ReactNode}) =>{
    
};

export const useDashboard = () => useContext(DashboardContext);