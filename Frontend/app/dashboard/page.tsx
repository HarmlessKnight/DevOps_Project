'use client'

import { useState, useEffect } from 'react'
import { Bar, BarChart, ResponsiveContainer, XAxis, YAxis } from 'recharts'
import { PlusCircle, DollarSign, CreditCard, Wallet } from 'lucide-react'
import Link from 'next/link'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import { axiosInstance, getToken,logoutApi } from '@/lib/utils'
import {DashboardData,Account,Transaction} from '@/models/dashboard'
import { useRouter } from 'next/navigation' 


const expenseData = [
  { category: 'Food', amount: 400 },
  { category: 'Transportation', amount: 300 },
  { category: 'Entertainment', amount: 200 },
  { category: 'Utilities', amount: 500 },
  { category: 'Shopping', amount: 250 },
]

export default function Dashboard() {
  const [newExpenseAmount, setNewExpenseAmount] = useState('')
  const [newExpenseCategory, setNewExpenseCategory] = useState('')
  const [dashboardData, setDashboardData] = useState<DashboardData | null>(null)
  const [loading, setLoading] = useState(true)
  const router = useRouter()

  useEffect(() => {
    const token = getToken()
    
    if (!token) {
      router.push('/login')
      return
    }

    axiosInstance
      .get('/dashboard')
      .then((response) => {
        const data: DashboardData = response.data
        const updatedAccounts = data.accounts.map((account: Account) => ({
          ...account,
          transactions: account.transactions.map((transaction: Transaction) => ({
            ...transaction,
            date: new Date(transaction.timestamp.replace(' ', 'T')), 
          }))
        }))

        setDashboardData({
          ...data,
          accounts: updatedAccounts,
        })
        setLoading(false)
      })
      .catch((error) => {
        setLoading(false)
        if (error.response?.status === 401) {
         logoutApi()
        }
      })
  }, [router])

  const formatDate = (timestamp: string | undefined): string => {
    if (!timestamp) return 'No Date Available'
    try {
      return new Date(timestamp.replace(' ', 'T')).toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
      })
    } catch (error) {
      return 'Invalid Date'
    }
  }

  const handleLogout = async () => {
    const token = getToken()
  
    if (token) {
      try {
        const response = await fetch('/logout', {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        })
  
        if (response.ok) {
          logoutApi()
        } else {
          console.error('Failed to log out:', response.statusText)
        }
      } catch (error) {
        console.error('Logout request failed:', error)
      }
    }
  }
  

  const handleAddExpense = () => {
    // Handle expense later
    setNewExpenseAmount('')
    setNewExpenseCategory('')
  }

  const renderAccount = (accountType: string) => {
    const account = dashboardData?.accounts.find(
      (a) => a.type.toUpperCase() === accountType.toUpperCase()
    )
  
    const icons = {
      CHECKING: <CreditCard className="h-4 w-4 text-muted-foreground" />,
      SAVINGS: <Wallet className="h-4 w-4 text-muted-foreground" />,
      CREDIT_CARD: <DollarSign className="h-4 w-4 text-muted-foreground" />,
    }
  
    return (
      <Card>
        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
          <CardTitle className="text-sm font-medium">{accountType} Account</CardTitle>
          {icons[accountType.toUpperCase() as keyof typeof icons]}
        </CardHeader>
        <CardContent>
          {account ? (
            <>
              <div className="text-2xl font-bold">${account.balance.toFixed(2)}</div>
              <p className="text-xs text-muted-foreground">
                Last transaction: {account.transactions.length > 0 
                  ? formatDate(account.transactions[0].timestamp) 
                  : 'No transactions'}
              </p>
            </>
          ) : (
            <div className="text-2xl font-bold">No {accountType} Account</div>
          )}
        </CardContent>
      </Card>
    )
  }

  if (loading) {
    return (
      <div className="flex flex-col items-center justify-center py-10">
        <div className="w-64 h-8 bg-gray-300 animate-pulse mb-4"></div>
        <div className="w-48 h-6 bg-gray-300 animate-pulse mb-4"></div>
        <div className="w-60 h-8 bg-gray-300 animate-pulse mb-4"></div>
      </div>
    )
  }

  if (!dashboardData) {
    return <div>Failed to load data</div>
  }

  return (
    <div className="flex flex-col min-h-screen">
      <header className="px-4 lg:px-6 h-14 flex items-center">
        <Link className="flex items-center justify-center" href="#">
          <DollarSign className="h-6 w-6" />
          <span className="sr-only">Finance Dashboard</span>
        </Link>
        <nav className="ml-auto flex gap-4 sm:gap-6">
          <Link className="text-sm font-medium hover:underline underline-offset-4" href="#">
            Dashboard
          </Link>
          <Link className="text-sm font-medium hover:underline underline-offset-4" href="#">
            Accounts
          </Link>
          <Link className="text-sm font-medium hover:underline underline-offset-4" href="#">
            Transactions
          </Link>
          <Link className="text-sm font-medium hover:underline underline-offset-4" href=""  onClick={() => {logoutApi(); }}>
            Logout
          </Link>
        </nav>
      </header>
      <main className="flex-1 py-6 px-4 lg:px-6">
        <div>
          <div className="mb-6">
            <p className="text-xl font-semibold">Welcome, {dashboardData.username}</p>
            <p className="text-sm text-muted-foreground">
              Role: {dashboardData.roles.map((role) => role.authority).join(', ')}
            </p>
          </div>

          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
            {renderAccount('Checking')}
            {renderAccount('Savings')}
            {renderAccount('Credit_Card')}
          </div>
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7 mt-4">
            <Card className="col-span-4">
              <CardHeader>
                <CardTitle>Recent Transactions</CardTitle>
              </CardHeader>
              <CardContent>
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Description</TableHead>
                      <TableHead>Category</TableHead>
                      <TableHead>Amount</TableHead>
                      <TableHead>Date</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {dashboardData.accounts.flatMap(account => account.transactions).map((transaction) => (
                      <TableRow key={transaction.id}>
                        <TableCell>{transaction.description}</TableCell>
                        <TableCell>{transaction.amount < 0 ? 'Expense' : 'Income'}</TableCell>
                        <TableCell>{transaction.amount < 0 ? `-$${Math.abs(transaction.amount).toFixed(2)}` : `+$${transaction.amount.toFixed(2)}`}</TableCell>
                        <TableCell> {formatDate(transaction.timestamp)} </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </CardContent>
            </Card>
            <Card className="col-span-3">
              <CardHeader>
                <CardTitle>Expense by Category</CardTitle>
                <CardDescription>Your spending distribution for this month</CardDescription>
              </CardHeader>
              <CardContent className="pl-2">
                <ResponsiveContainer width="100%" height={300}>
                  <BarChart data={expenseData}>
                    <XAxis dataKey="category" />
                    <YAxis />
                    <Bar dataKey="amount" fill="#adfa1d" />
                  </BarChart>
                </ResponsiveContainer>
              </CardContent>
            </Card>
          </div>
          <Card className="mt-4">
            <CardHeader>
              <CardTitle>Add New Expense</CardTitle>
              <CardDescription>Quickly add a new expense to your tracker</CardDescription>
            </CardHeader>
            <CardContent>
              <form className="grid gap-4 sm:grid-cols-2 sm:gap-6">
                <div className="grid gap-2">
                  <Label htmlFor="amount">Amount</Label>
                  <Input
                    id="amount"
                    placeholder="Enter amount"
                    type="number"
                    value={newExpenseAmount}
                    onChange={(e) => setNewExpenseAmount(e.target.value)}
                  />
                </div>
                <div className="grid gap-2">
                  <Label htmlFor="category">Category</Label>
                  <Select value={newExpenseCategory} onValueChange={setNewExpenseCategory}>
                    <SelectTrigger id="category">
                      <SelectValue placeholder="Select Category" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="Food">Food</SelectItem>
                      <SelectItem value="Transportation">Transportation</SelectItem>
                      <SelectItem value="Entertainment">Entertainment</SelectItem>
                      <SelectItem value="Shopping">Shopping</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
              </form>
            </CardContent>
            <CardFooter className="justify-end">
              <Button variant="outline" onClick={handleAddExpense}>
                Add Expense
              </Button>
            </CardFooter>
          </Card>
        </div>
      </main>
    </div>
  )
}