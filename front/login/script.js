document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('loginForm').addEventListener('submit', function (event) {
        event.preventDefault();

        const login = document.getElementById('login').value;
        const senha = document.getElementById('senha').value;

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
            console.log(login)
            window.location.href= '/patrimonio/index.html'
        })
        .catch(error => {
            console.error('erro:', error.message);
        });
    });
});