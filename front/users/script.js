document.addEventListener('DOMContentLoaded', () => {
    // token no localstorage
    const token = localStorage.getItem('token');

    if (token) {
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        };

        fetch('http://localhost:8080/user', {
            method: 'GET',
            headers: headers
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao obter dados dos usuários');
            }
            return response.json();
        })
        .then(usersData => {
            console.log('users: ', usersData);
            const userList = document.getElementById('userList');

            userList.innerHTML = '';

            usersData.forEach(user => {
                const userCard = document.createElement('div');
                userCard.classList.add('user-item');

                const username = document.createElement('p');
                username.textContent = `Username: ${user.username}`;
                
                const role = document.createElement('p');
                role.textContent = `Role: ${user.role}`;

                userCard.appendChild(username);
                userCard.appendChild(role);

                userList.appendChild(userCard);
            });
        })
        .catch(error => {
            console.error('Erro ao obter dados dos usuários:', error.message);
        });
    } else {
        console.error('Token JWT não encontrado no localStorage');
    }
});
