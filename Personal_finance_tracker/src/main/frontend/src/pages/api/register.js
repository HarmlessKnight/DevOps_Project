
export default async function handler(req, res) {
    if (req.method === 'POST') {
        const { username, password } = req.body;

        const response = await fetch('http://localhost:8080/api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        if (response.ok) {
            res.redirect('/login');
        } else {
            const data = await response.json();
            res.status(response.status).json({ error: data.error || 'Failed to register' });
        }
    } else {
        res.setHeader('Allow', ['POST']);
        res.status(405).end(`Method ${req.method} Not Allowed`);
    }
}
