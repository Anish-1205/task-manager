# Task Manager – Spring Boot + MySQL

A clean, full-stack Task Manager application built with **Spring Boot**, **Spring Data JPA**, and **MySQL**, featuring a lightweight vanilla JavaScript frontend served from the backend.

This project focuses on **correct REST design, clean layering, and maintainable code**, rather than over-engineering.

---

## Features

### Backend
- RESTful API with full CRUD support
- Endpoints:
  - `GET /tasks`
  - `POST /tasks`
  - `PUT /tasks/{id}`
  - `PATCH /tasks/{id}` (DTO-based partial updates)
  - `DELETE /tasks/{id}`
- Server-side validation
- Clean Controller → Service → Repository layering
- JPA + Hibernate ORM
- MySQL as persistence layer

### Frontend
- Served from `src/main/resources/static`
- Vanilla HTML, CSS, and JavaScript
- Features:
  - Add tasks
  - Toggle completion status
  - Delete tasks
  - Inline edit task title (double-click)
  - Filter tasks (All / Active / Completed)
- Clean, minimal UI with custom CSS
- No frontend framework (intentional)

---

## Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **HTML / CSS / JavaScript**

---

## Project Structure
src/main/java
└── controller
└── service
└── repository
└── model
└── dto

src/main/resources
├── static
│ ├── index.html
│ ├── app.js
│ └── style.css
└── application.yaml


---

## Running the Project Locally

### Prerequisites
- Java 17+
- MySQL
- Maven (or Maven Wrapper)

### Database Setup

Create a MySQL database:
```sql
CREATE DATABASE taskmanager_db;

Update environment variables (recommended):

Windows (PowerShell):

$env:DB_URL="jdbc:mysql://localhost:3306/taskmanager_db?useSSL=false&serverTimezone=UTC"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"


Linux / macOS:

export DB_URL="jdbc:mysql://localhost:3306/taskmanager_db?useSSL=false&serverTimezone=UTC"
export DB_USERNAME="root"
export DB_PASSWORD="your_password"

Run the Application
./mvnw spring-boot:run


Then open:

http://localhost:8080


---

