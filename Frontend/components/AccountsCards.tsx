import { Card, CardHeader, CardTitle, CardDescription, CardFooter } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";

type Transaction = {
  id: number;
  amount: number;
  description: string;
  timestamp: string;
};

type Account = {
  accountId: number;
  balance: number;
  type: string;
  transactions: Transaction[];
};

export function AccountsCards({ accounts }: { accounts: Account[] }) {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
      {accounts.map(account => (
        <Card key={account.accountId} className="aspect-square p-4 flex flex-col justify-between">
          <CardHeader>
            <CardTitle className="text-lg font-semibold">
              Account #{account.accountId}
            </CardTitle>
            <CardDescription className="truncate">
              Type: {account.type}
            </CardDescription>
          </CardHeader>

          <div className="mt-2 mb-2">
            <p className="text-xl font-bold tabular-nums">${account.balance.toFixed(2)}</p>
          </div>

          <CardFooter className="text-sm text-muted-foreground">
            <p>{account.transactions.length} transactions</p>
            {account.transactions.length > 0 && (
              <Badge variant="outline" className="mt-1">
                Latest: {new Date(account.transactions[0].timestamp).toLocaleDateString()}
              </Badge>
            )}
          </CardFooter>
        </Card>
      ))}
    </div>
  );
}
