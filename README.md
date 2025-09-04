# ManageHive Backend

A **Spring Boot** based backend system designed for managing organizational resources and data.  
This project demonstrates **Java, Spring Boot, PostgreSQL, and REST API development** skills.  
It is part of a larger system (`ManageHive`), where the backend handles data persistence, business logic, and API exposure.

---

## Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **Maven**

---

## Features Implemented

- Modular **layered architecture** (`controller`, `service`, `repository`, `model`).
- **RESTful APIs** for resource management.
- **CRUD operations** (Create, Read, Update, Delete) for core entities.
- Integration with **PostgreSQL** database using Spring Data JPA.
- Configurable via `application.properties`.

---

## Project Structure

```
src/main/java/com/managehive/
│
├── controller/     # REST controllers (API endpoints)
├── model/          # JPA entities
├── repository/     # Data access layer
├── service/        # Business logic layer
└── ManageHiveApp   # Main application class
```

---

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/sdas204221/ManageHiveBackend.git
cd ManageHiveBackend
```

### 2. Configure Database
- Install and run **PostgreSQL**.
- Create a database (e.g., `managehive_db`).
- Update `src/main/resources/application.properties` with your DB credentials:
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/managehive_db
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=update
  ```

### 3. Run the Application
Using Maven:
```bash
mvn spring-boot:run
```

Or run the main class:
```bash
ManageHiveApplication.java
```

The server will start at:  
`http://localhost:49152`

---

## Example API Endpoints

- **User APIs**
  - `GET /users` → fetch all users
  - `POST /users` → create a new user
  - `GET /users/{id}` → fetch user by ID
  - `PUT /users/{id}` → update user
  - `DELETE /users/{id}` → delete user

(Other modules follow similar CRUD structure)

---

## Related Repositories

- **Frontend Repository:** [ManageHive Frontend](https://github.com/sdas204221/manage_hive_frontend)

---

## License

This project is for **educational and internship demonstration purposes**.  
Feel free to explore, fork, and extend.

---

**Author:** [Subhra Das](https://github.com/sdas204221)  
Contact: *sdas204221@gmail.com*  
