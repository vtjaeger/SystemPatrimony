</head>
<body>
    <h1>Managing a Company's Patrimonies</h1>
    <p>Fullstack Project. This project is based on a real-life problem faced by many companies: the need to manage, locate, and catalog assets.</p>
    <h2>Features</h2>
    <ul>
        <li><strong>View Building info: </strong>View all Company Buildings</li>
        <li><strong>View Deparments info: </strong>View all Departaments, and your building</li>
        <li><strong>View Patrimony info: </strong>View all Patrimonies, their location and responsible</li>
        <li><strong>Complete CRUD </strong>for all features, and their respective permissions</li>
    </ul>
    <h2>Technologies Used</h2>
    <ul>
        <li>Spring Security</li>
        <li>JWT Token</li>
        <li>Spring Data JPA</li>
        <li>Validation</li>
        <li>Database</li>
    </ul>
    <h2>Project Architecture</h2>
    <p>The project follows a structured architecture to ensure the maintainability and durability of the code.</p>
    <h3>CONTROLLERS</h3>
    <p>Contains the Spring MVC controllers responsible for handling HTTP requests and responses.</p>
    <h3>DTOS</h3>
    <p>Contains Data Transfer Objects (DTOs) used for mapping request and response payloads between the client and the server. It includes subfolders for request and response DTOs.</p>
    <h3>MODELS</h3>
    <p>Contains the domain models representing entities such as User, Building, Department, and Patrimony.</p>
    <h3>REPOSITORIES</h3>
    <p>Contains the Spring Data JPA repositories for interacting with the database.</p>
    <h3>SECURITY</h3>
    <p>Contains configurations related to security, including Spring Security configurations such as authentication and authorization.</p>
    <h3>SERVICES</h3>
    <p>Contains the service layer responsible for implementing business logic and coordinating data access between controllers and repositories.</p>
    <hr>
    <p>Developed individually by a Computer Science student to learn how to deal with different types of data, data persistence, software architecture, improve language techniques used, 
      programming logic, solve real problems.</p>
    <p>Sources:</p>
    <ul>
        <li><a href="https://chat.openai.com/">Chat GPT</a></li>
        <li><a href="https://pt.stackoverflow.com/">Stack Overflow</a></li>
        <li><a href="https://www.youtube.com/">Youtube</a></li>
        <li><a href="https://ebaconline.com.br/">EBAC</a></li>
        <li><a href="https://cursos.alura.com.br/dashboard">Alura</a></li>
    </ul>
</body>
</html>
