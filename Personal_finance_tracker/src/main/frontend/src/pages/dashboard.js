import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';

const Dashboard = () => {
    const [userData, setUserData] = useState(null);
    const router = useRouter();

    useEffect(() => {
        const fetchUserData = async () => {
            const token = localStorage.getItem('token');

            if (!token) {
                router.push('/login'); // Redirect if no token
                return;
            }

            const response = await fetch(`http://localhost:8080/api/dashboard`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (response.ok) {
                const data = await response.json();
                setUserData(data);
            } else {
                console.error('Failed to fetch user data:', response.status, response.statusText);
                localStorage.removeItem('token'); // Clear invalid token
                router.push('/login'); // Redirect if token is invalid
            }
        };

        fetchUserData();
    }, [router]);

    if (!userData) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h1>User Dashboard</h1>
            <p>Username: {userData.username}</p>
            <p>Role: {userData.role.join(', ')}</p> {/* Assuming roles is an array */}
        </div>
    );
};

export default Dashboard;
