import React, { useState } from 'react';
import './Login.css';

const Login = () => {
    const [login, setLogin] = useState('');
    const [senha, setSenha] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();

        fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ login, password: senha })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao fazer login');
            }
            return response.json();
        })
        .then(data => {
            console.log('token:', data.token);
            localStorage.setItem('token', data.token); 
            localStorage.setItem('login', login);
            window.location.href = '/patrimony';
        })
        .catch(error => {
            console.error('erro:', error.message);
        });
    };

    return (
        <section>
            <div className="div_login">
                <form id="loginForm" onSubmit={handleSubmit}>
                    <label htmlFor="login">Login</label>
                    <input type="text" id="login" name="login" value={login} onChange={(e) => setLogin(e.target.value)} />

                    <label htmlFor="senha">Senha</label>
                    <input type="password" id="senha" name="senha" value={senha} onChange={(e) => setSenha(e.target.value)} />

                    <button id="checkUserBtn">Entrar</button>
                </form>
            </div>
        </section>
    );
};

export default Login;
