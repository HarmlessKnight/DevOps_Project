'use client';

import { Card, CardHeader, CardTitle, CardDescription, CardContent } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useRouter } from 'next/navigation';
import axios from 'axios';
import { useState } from 'react';

export default function Component() {
  const router = useRouter();
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);


 const handleRegister = async () => {
    setLoading(true);
    setError('');
    setSuccess('');

    try {
      const response = await axios.post('http://localhost:8080/api/register', {
        username,
        email,
        password,
      });

      if (response.status === 201) {
        setSuccess('Registration successful! Redirecting to login page...');
        
        setTimeout(() => {
          router.push('http://localhost:8080/api/login');
        }, 2000);
      }
    } catch (err) {
      if (axios.isAxiosError(err)) {
        setError(err.response?.data?.error || 'Registration failed');
      } else {
        setError('An unexpected error occurred');
      }
      console.error(err);
    } finally {
      setLoading(false); 
    }
  };

  return (
    <Card className="mx-auto max-w-sm">
      <CardHeader className="space-y-1 text-center">
        <CardTitle className="text-2xl font-bold">Register</CardTitle>
        <CardDescription>Create Your Regio Account</CardDescription>
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

          <div className="space-y-2 text-center">
            <Label htmlFor="email">Email</Label>
            <Input
              id="email"
              type="email"
              placeholder="m@example.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          {error && <p className="text-red-500">{error}</p>}
          <Button type="button" className="w-full" onClick={handleRegister}>
            Register
          </Button>
        </div>
      </CardContent>
    </Card>
  );
}
