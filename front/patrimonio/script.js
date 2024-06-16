// user logado
const loginSalvo = localStorage.getItem('login');
console.log('Login salvo:', loginSalvo);

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
                throw new Error('Erro ao obter dados do usuário');
            }
            return response.json();
        })
        .then(userData => {
            console.log('Dados do usuário:', userData);
            const isAdmin = userData.some(user => user.username === loginSalvo && user.role === 'ADMIN');

            if (isAdmin) {
                console.log('Admin');
                const btnAddPatrimonio = document.querySelector('.btnAddPatrimonio');
                
                if (btnAddPatrimonio) {
                    btnAddPatrimonio.addEventListener('click', redirectToAdicionarPatrimonio);
                }
            } else {
                console.log('Não é admin');
            }
        })
        .catch(error => {
            console.error('Erro ao obter dados do usuário:', error.message);
        });

        fetch('http://localhost:8080/patrimony', {
            method: 'GET',
            headers: headers
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao obter itens do patrimônio');
            }
            return response.json();
        })
        .then(data => {
            console.log('Itens do patrimônio:', data);
            const patrimonioList = document.getElementById('patrimonioList');

            patrimonioList.innerHTML = '';

            data.forEach(item => {
                const card = document.createElement('div');
                card.classList.add('patrimonio-item');

                const objectId = document.createElement('p');
                objectId.textContent = `ID: ${item.id}`;
                
                const objectName = document.createElement('p');
                objectName.textContent = `Objeto: ${item.object}`;
                
                const responsible = document.createElement('p');
                responsible.textContent = `Responsável: ${item.responsible}`;
                
                const building = document.createElement('p');
                building.textContent = `Building: ${item.building}`;
                
                const department = document.createElement('p');
                department.textContent = `Department: ${item.department}`;

                card.appendChild(objectId);
                card.appendChild(objectName);
                card.appendChild(responsible);
                card.appendChild(building);
                card.appendChild(department);

                patrimonioList.appendChild(card);
            });
        })
        .catch(error => {
            console.error('Erro ao obter itens do patrimônio:', error.message);
        });
        
        const btnGetUsers = document.querySelector('.btnGetUsers');
        if (btnGetUsers) {
            btnGetUsers.addEventListener('click', () => {
                redirectGetUsers();
            });
        }
    } else {
        console.error('Token JWT não encontrado no localStorage');
    }
});

const redirectToAdicionarPatrimonio = () => {
    window.location.href = 'add/index.html';
};

const redirectGetUsers = () => {
    window.location.href = '/users/index.html';
};
