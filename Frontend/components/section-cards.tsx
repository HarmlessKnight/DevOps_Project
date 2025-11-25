import { Badge } from "@/components/ui/badge"
import {
  Card,
  CardAction,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

type Transaction = {
  id: number
  amount: number
  description: string
  timestamp: string
}

type Account = {
  accountId: number
  balance: number
  type: string
  transactions: Transaction[]
}

interface SectionCardsProps {
  accounts?: Account[]
}

export function SectionCards({ accounts }: SectionCardsProps) {
  
  if(accounts)
    {

    return (
    <div className="w-full max-w-screen-xl mx-auto grid grid-cols-1 gap-4 px-4 lg:px-6 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4">
      
      {accounts.map(account => (
        <Card key={account.accountId} className="aspect-square flex flex-col justify-between">
          <CardHeader>
            
            <CardTitle className="text-2xl font-semibold tabular-nums">
              {account.type}
            </CardTitle>
            <CardAction>
              <Badge variant="outline">${account.balance.toFixed(2)}</Badge>
            </CardAction>
          </CardHeader>
          <CardFooter className="flex-col items-start gap-1.5 text-sm">
            <div className="line-clamp-2 flex gap-2 font-medium">
              {account.transactions.length} transaction{account.transactions.length !== 1 ? 's' : ''}
            </div>
            <div className="text-muted-foreground">
              Latest: {account.transactions.length > 0
                ? new Date(account.transactions[0].timestamp).toLocaleDateString()
                : "No transactions"}
            </div>
          </CardFooter>
        </Card>
      ))}
    </div>
  )}
  else{
    return(
      <div className="w-full max-w-screen-xl mx-auto grid grid-cols-1 gap-4 px-4 lg:px-6 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4">
      
      
        <Card  className="aspect-square flex flex-col justify-between">
          <CardHeader>
            
            <CardTitle className="text-2xl font-semibold tabular-nums">
             
            </CardTitle>
            <CardAction>
              <Badge variant="outline">$0</Badge>
            </CardAction>
          </CardHeader>
          <CardFooter className="flex-col items-start gap-1.5 text-sm">
            <div className="line-clamp-2 flex gap-2 font-medium">
              
            </div>
            <div className="text-muted-foreground">
              
            </div>
          </CardFooter>
        </Card>
         
        </div>
         )}
      }
  
