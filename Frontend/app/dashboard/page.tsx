'use client'

import { useDashboard } from '../context/DataContext'
import { SidebarProvider } from '@/components/ui/sidebar'
import { AppSidebar } from '@/components/app-sidebar'
import { SidebarInset } from '@/components/ui/sidebar'
import { SiteHeader } from '@/components/site-header'
import { SectionCards } from '@/components/section-cards'

export default function DashboardPage() {
  const { dashboardData, loading } = useDashboard()
    

  return (
    <SidebarProvider>
      <AppSidebar variant="inset" />
      <SidebarInset>
        <SiteHeader />
        <div className="flex flex-1 flex-col">
          <div className="@container/main flex flex-1 flex-col gap-2">
            <div className="flex flex-col gap-4 py-4 md:gap-6 md:py-6">
              {loading}

              {!loading && dashboardData && dashboardData.accounts.length > 0 ? (
                <SectionCards accounts={dashboardData.accounts} />
              ) : !loading ? (
                <p className="text-black">No accounts found</p>
              ) : null}
            </div>
          </div>
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
}
