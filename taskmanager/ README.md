# 📝 Task Management System (Backend)

A secure and scalable **REST API based Task Management platform** designed to help users organize their workflow efficiently.

This project was developed during my internship at **Amdocs**, focusing on backend development best practices, security, and layered architecture.

---

# 🚀 Key Features

* **Secure Authentication**
  User registration and login protected by **JWT (JSON Web Tokens)**.

* **Role-Based Access Control**
  Strict access management based on user roles (e.g., `ADMIN`, `CUSTOMER`).

* **Task Lifecycle Management**
  APIs to create, update, and track task progress.

* **Relational Database Management**
  Data persistence using **MySQL** with **Hibernate JPA**.

* **Clean Layered Architecture**
  Implements the **Controller → Service → Repository** design pattern.

---

# 🛠️ Tech Stack

| Category    | Technology            |
| ----------- | --------------------- |
| Language    | Java 17               |
| Framework   | Spring Boot 3.x       |
| Security    | Spring Security + JWT |
| Database    | MySQL                 |
| ORM         | Hibernate JPA         |
| Build Tool  | Maven                 |
| IDE         | Eclipse IDE           |
| API Testing | Postman               |

---

# 🏗️ Project Architecture

The project follows a **layered architecture** to maintain separation of concerns.

```
src/main/java/com/taskmanager
│
├── controller   → API Endpoints (Handles HTTP requests)
├── service      → Business Logic Layer
├── repository   → Data Access Layer
├── entity       → Database Models (JPA Entities)
├── dto          → Data Transfer Objects
├── security     → JWT & Spring Security Configuration
└── TaskmanagerApplication.java  → Main Application Entry Point
```

---

# 🔐 Key API Endpoints

## Authentication Module

| Method | Endpoint                            | Description                              |
| ------ | ----------------------------------- | ---------------------------------------- |
| POST   | /api/Authentication/register        | Register a new user                      |
| POST   | /api/Authentication/login           | Authenticate user and generate JWT token |
| POST   | /api/Authentication/forgot_password | Trigger password reset email             |

---

## Task Management Module

| Method | Endpoint          | Description                            |
| ------ | ----------------- | -------------------------------------- |
| GET    | /api/tasks        | Fetch all tasks (Requires valid token) |
| POST   | /api/tasks/create | Create a new task                      |

---

# ⚙️ Setup & Installation

## 1️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/task-management-system.git
```

---

## 2️⃣ Database Configuration

Navigate to:

```
src/main/resources/application.properties
```

Update your **MySQL credentials**:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

---

## 3️⃣ Run the Application

In **Eclipse IDE**:

Right click:

```
TaskmanagerApplication.java
```

Then select:

```
Run As → Spring Boot App
```

The application will start on:

```
http://localhost:8080
```

---

# 👨‍💻 Author

**Srushti Wadkar**
BCA Student | Backend Developer Intern

GitHub Profile
LinkedIn
