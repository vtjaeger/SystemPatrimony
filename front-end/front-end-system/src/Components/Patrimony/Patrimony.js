// Patrimony.jsx
import React, { useEffect, useState } from 'react';
import './Patrimony.css';

const Patrimony = () => {
    const [patrimonyName, setPatrimonyName] = useState('');
    const [patrimonies, setPatrimonies] = useState([]);
    const [isAdmin, setIsAdmin] = useState(false);
    const token = localStorage.getItem('token');
    const loginSalvo = localStorage.getItem('login');

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
        .then(userData => {
            const isAdminUser = userData.some(user => user.username === loginSalvo && user.role === 'ADMIN');
            setIsAdmin(isAdminUser);
        })
        .catch(error => console.error('Erro ao obter dados do usuário:', error.message));

        fetch('http://localhost:8080/patrimony', {
            method: 'GET',
            headers: headers
        })
        .then(response => response.json())
        .then(data => setPatrimonies(data))
        .catch(error => console.error('Erro ao obter itens do patrimônio:', error.message));
    }, [token, loginSalvo]);

    const redirectToAdicionarPatrimonio = () => {
        window.location.href = '/patrimony/add';
    };

    const redirectGetUsers = () => {
        window.location.href = '/user';
    };

    return (
        <div>
            <h1>Patrimônio</h1>

            <div id="patrimonioList">
                {patrimonies.map((item) => (
                    <div key={item.id} className="patrimonyItem">
                        <p>Object: {item.object}</p>
                        <p>Object: {item.category}</p>
                        <p>Building: {item.building}</p>
                        <p>Department: {item.department}</p>
                        <p>Responsible: {item.responsible}</p>
                    </div>
                ))}
            </div>

            {isAdmin && <button className="btnAddPatrimonio" onClick={redirectToAdicionarPatrimonio}>Adicionar</button>}
            <button className="btnGetUsers" onClick={redirectGetUsers}>Ver Users</button>
        </div>
    );
};

export default Patrimony;
