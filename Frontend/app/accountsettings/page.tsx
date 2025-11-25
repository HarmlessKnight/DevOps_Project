'use client'

import { AppSidebar } from "@/components/app-sidebar"
import { SiteHeader } from "@/components/site-header"
import { SidebarProvider } from "@/components/ui/sidebar"
import { SidebarInset } from '@/components/ui/sidebar'


export default function TransactionsPage(){

    return(
    <SidebarProvider>
      <AppSidebar variant="inset" />
      <SidebarInset>
        <SiteHeader />
        
      </SidebarInset>
    </SidebarProvider>
    );
}