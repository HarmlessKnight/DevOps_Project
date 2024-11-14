export interface Role {
    authority: string;
  }
  
export interface Transaction {
  id: number;
  description: string;
  amount: number;
  timestamp: string;
  date?: Date;
}
  
  export interface Account {
    accountId: number;
    type: string;
    balance: number;
    transactions: Transaction[];
  }
  
  export interface DashboardData {
    username: string;
    roles: Role[];
    accounts: Account[];
  }
  