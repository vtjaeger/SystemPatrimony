document.addEventListener('DOMContentLoaded', () => {
    // Token no localStorage
    const token = localStorage.getItem('token');

    if (token) {
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        };

        // Requisição para obter os patrimônios
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
            console.error('erro:', error.message);
        });

        // Adiciona evento de clique para redirecionar para a página 'adicionar-patrimonio.html'
        const redirectToAdicionarPatrimonio = () => {
            window.location.href = 'add/index.html';
        };

        
        const btnAddPatrimonio = document.querySelector('.btnAddPatrimonio');
        if (btnAddPatrimonio) {
            btnAddPatrimonio.addEventListener('click', redirectToAdicionarPatrimonio);
        } else {
            console.error('Botão de adicionar patrimônio não encontrado.');
        }
    } else {
        console.error('Token JWT não encontrado no localStorage');
    }
});
