export default async function handler(req, res) {
    if (req.method === 'POST') {
        const { username, password } = req.body;

        const response = await fetch('http://localhost:8080/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        const data = await response.json();

        if (response.ok) {
            // Save token and return success response
            res.status(200).json({ token: data.token }); // Return the token to the frontend
        } else {
            res.status(response.status).json({ error: data.error || 'Failed to login' });
        }
    } else {
        res.setHeader('Allow', ['POST']);
        res.status(405).end(`Method ${req.method} Not Allowed`);
    }
}
