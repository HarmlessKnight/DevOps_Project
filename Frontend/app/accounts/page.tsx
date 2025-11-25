'use client'

import { AppSidebar } from "@/components/app-sidebar"
import { SiteHeader } from "@/components/site-header"
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar"
import { AccountsView } from "@/components/accounts-view" 


export default function AccountsPage(){

    return(
        <SidebarProvider>
            <AppSidebar variant="inset"/>
                <SidebarInset>
                    <SiteHeader/>
                    <AccountsView /> 
                </SidebarInset>
        </SidebarProvider>
    );
}