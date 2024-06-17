// Users.jsx
import React, { useEffect, useState } from 'react';
import './User.css';

const Users = () => {
    const [users, setUsers] = useState([]);
    const token = localStorage.getItem('token');

    useEffect(() => {
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        };

        fetch('http://localhost:8080/user', {
            method: 'GET',
            headers: headers
        })
        .then(response => response.json())
        .then(data => setUsers(data))
        .catch(error => console.error('Erro ao obter dados dos usuários:', error.message));
    }, [token]);

    return (
        <div>
            <h1>Users</h1>
            <div id="userList">
                {users.map((user) => (
                    <div key={user.username} className="user-item">
                        <p>Username: {user.username}</p>
                        <p>Role: {user.role}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Users;
