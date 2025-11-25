'use client';

import { DashboardProvider } from './context/DataContext';

export function Providers({ children }: { children: React.ReactNode }) {
  return <DashboardProvider>{children}</DashboardProvider>;
}
