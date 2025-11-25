'use client'

import { axiosInstance } from "@/lib/utils";
import { useEffect, useState } from "react";
import { Skeleton } from "./ui/skeleton";


type Transaction={
    id: number;
    amount: number;
    description: string;
    timestamp: string;
}

type Account = {
    accountId: number;
    balance: number;
    type: string;
    transactions: Transaction[];
}

export function AccountsView() {
    const[accounts,setAccounts] = useState<Account[]>([]);
    const[loading,setLoading] = useState(true);
    const[error,setError] = useState<string |null>(null);
    const[userId,setUserid] = useState(); // neka sedi vaka ama naprajgo ili id da zemish so logiranjeto za da pulnuvash
    //sho mozhi da e korisno poshto ke ti treba i na drugi mesta za koristenje ako zemash nekoj raboti sprema id
    // ili naprajgo so tokenot da proverva dali si ti pa da ti isprati se ako tokenot e validen prashaj koe e podobro
    useEffect(()=>{
        const fetchAccounts = async() => {
            try{
                setLoading(true);
                const response = await axiosInstance.get(`api/accounts/user/${userId}`);
                setAccounts(response.data);
                setError(null);
            }catch(err)
            {
                setError("Failed to fetch accounts.")
                console.error(err);
            }finally{
                setLoading(false);
            }
        }
        fetchAccounts();
    },[])

    if(loading)
    {

        return(
             <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">                      
             {[...Array(4)].map((_, i) => (                                                                           
               <Skeleton key={i} className="aspect-square rounded-lg" />                                              
                 ))}                                                                                                      
                </div>        
        )
    }
    if(error){
        return <p className="text-red-500">{error}</p>
    }

    

}