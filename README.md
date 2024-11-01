<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <h1>Managing a Company's Patrimonies</h1>
    <p>Fullstack Project. This project addresses a real-life problem faced by many companies: the need to manage, locate, catalog assets, and control inventory levels.</p>
    <h2>Features</h2>
    <ul>
        <li><strong>View Building Info:</strong> View all Company Buildings</li>
        <li><strong>View Department Info:</strong> View all Departments and their respective buildings</li>
        <li><strong>View Patrimony Info:</strong> View all Patrimonies, including their locations and responsible parties</li>
        <li><strong>Complete CRUD</strong> for all features, with role-based permissions</li>
        <li><strong>Inventory Management:</strong> Track and manage stock levels, quantities, and supply transfers across buildings</li>
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
    <p>The project follows a structured architecture to ensure code maintainability and scalability.</p>
    <h3>CONTROLLERS</h3>
    <p>Contains the Spring MVC controllers responsible for handling HTTP requests and responses.</p>
    <h3>DTOS</h3>
    <p>Contains Data Transfer Objects (DTOs) used to map request and response payloads between the client and server, including subfolders for request and response DTOs.</p>
    <h3>MODELS</h3>
    <p>Contains the domain models representing entities such as User, Building, Department, Patrimony, and Supply for inventory management.</p>
    <h3>REPOSITORIES</h3>
    <p>Contains the Spring Data JPA repositories for interacting with the database.</p>
    <h3>SECURITY</h3>
    <p>Contains configurations related to security, including Spring Security settings for authentication and authorization.</p>
    <h3>SERVICES</h3>
    <p>Contains the service layer responsible for implementing business logic and coordinating data access between controllers and repositories.</p>
    <hr>
    <p>Developed individually by a Computer Science student to learn how to work with diverse data types, data persistence, software architecture, enhance language techniques, 
       programming logic, and solve real-world problems.</p>
    <h2>Sources</h2>
    <ul>
        <li><a href="https://chat.openai.com/">Chat GPT</a></li>
        <li><a href="https://pt.stackoverflow.com/">Stack Overflow</a></li>
        <li><a href="https://www.youtube.com/">YouTube</a></li>
        <li><a href="https://ebaconline.com.br/">EBAC</a></li>
        <li><a href="https://cursos.alura.com.br/dashboard">Alura</a></li>
    </ul>
</body>
</html>
