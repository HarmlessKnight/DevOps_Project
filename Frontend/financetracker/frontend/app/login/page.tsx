'use client'

import { Card, CardHeader, CardTitle, CardDescription, CardContent } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useRouter } from 'next/navigation';
import axios from 'axios';
import { useState } from 'react';

export default function LoginComponent() {
  const router = useRouter();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/login', {
        username,
        password,
      }, { withCredentials: true });
      

      if (response.status === 200) {
        
        const { token } = response.data;
        const expirationTime = Date.now() + 20 * 60 * 1000; 
        sessionStorage.setItem('jwt_token', token);
        sessionStorage.setItem('token_expiry', expirationTime.toString());

        router.push('/dashboard'); 
      }
    } catch (err) {
      if (axios.isAxiosError(err)) {
        console.error(err.response?.data); 
        setError(err.response?.data?.error || 'Login failed');
      } else {
        setError('An unexpected error occurred');
      }
    }
  };

  return (
    <Card className="mx-auto max-w-sm">
      <CardHeader className="space-y-1 text-center">
        <CardTitle className="text-2xl font-bold">Login</CardTitle>
        <CardDescription>Enter your username and password to login to your account</CardDescription>
      </CardHeader>
      <CardContent>
        <div className="space-y-4">
          <div className="space-y-2 text-center">
            <Label htmlFor="username">Username</Label>
            <Input
              id="username"
              type="text"
              placeholder="John_Doe"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="space-y-2 text-center">
            <Label htmlFor="password">Password</Label>
            <Input
              id="password"
              type="password"
              placeholder="********"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          {error && <p className="text-red-500">{error}</p>}
          <Button type="button" className="w-full" onClick={handleLogin}>
            Login
          </Button>
        </div>
      </CardContent>
    </Card>
  );
}
