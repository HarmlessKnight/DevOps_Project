import { Separator } from "@/components/ui/separator"
import { SidebarTrigger } from "@/components/ui/sidebar"
import { useDashboard } from '../app/context/DataContext';  
import { usePathname } from "next/navigation";

export function SiteHeader() {
  const { dashboardData, loading } = useDashboard(); 
  
  if (loading) return <p>Loading...</p>;
  if (!dashboardData) return <p>No data available</p>;

  
  const pageMessage: {[key:string]:string} ={
         "/dashboard": `Welcome, ${dashboardData.username}`,
         "/investments": "Portfolio",
         "/transactions": "Transaction Information",
         "/accounts": "Your Accounts",
        };

  const headerMessage = pageMessage[usePathname()] || 'Welcome';

 

  return (
    <header className="group-has-data-[collapsible=icon]/sidebar-wrapper:h-12 flex h-12 shrink-0 items-center gap-2 border-b transition-[width,height] ease-linear">
      <div className="flex w-full items-center gap-1 px-4 lg:gap-2 lg:px-6">
        <SidebarTrigger className="-ml-1" />
        <Separator
          orientation="vertical"
          className="mx-2 data-[orientation=vertical]:h-4"
        />
       
        <h1 className="text-base font-big font-bold">{headerMessage}</h1>
      </div>
    </header>
  )
}
