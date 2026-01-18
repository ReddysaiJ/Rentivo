# Rentivo - Car Rental Management System

**Author:** Reddysai Jonnadula
**Tech Stack:** Java, Spring Boot, MySQL, Thymeleaf, Spring Security, Docker

---

## 📌 Project Overview

Rentivo is a secure, scalable **Car Rental Management System** built using Spring Boot.
It allows users to browse cars, make bookings, and manage their profiles, while administrators can manage cars, users, and bookings efficiently.

The system ensures **data security**, **role-based access control**, **email notifications**, and offline payment verification.

---

## 🎯 Scope

### Purpose

To automate car rentals by enabling secure user registration, booking management, and administrative control.

### Included

* User registration, login, and profile management
* Role-based access (ADMIN / USER)
* Car listing, availability, and management
* Booking creation, modification, and cancellation
* Offline payment confirmation
* Email notifications for bookings, updates, and cancellations
* Admin dashboard for managing users, cars, and bookings
* File uploads (user profile photos, car images)
* Flyway migration scripts for database version control

### Excluded

* Online payment gateway integration
* Dynamic pricing
* Dedicated mobile application

---

## ✨ Features

### 👤 User Features

* Register, login, and update profile
* Upload profile photo
* Browse available cars
* Book cars and view recent bookings
* Email notifications for bookings and updates

### 🛠 Admin Features

* Add, update, and remove cars with images
* Manage users (add, update, delete)
* View and manage bookings
* Confirm offline payments
* Dashboard with stats (total users, cars, bookings, available cars)

### 🔐 Security

* Spring Security
* BCrypt password hashing
* Role-based authorization (USER / ADMIN)

---

## 🧰 Technology Stack

| Layer              | Technology              |
| ------------------ | ----------------------- |
| Backend            | Java, Spring Boot       |
| ORM                | Hibernate (JPA)         |
| Database           | MySQL                   |
| Database Migration | Flyway                  |
| Security           | Spring Security, BCrypt |
| Frontend           | Thymeleaf, HTML, CSS    |
| Email Service      | Spring Boot Mail        |
| Build Tool         | Maven                   |
| Containerization   | Docker, Docker Compose  |

---

## ⚙️ Installation & Setup

### Prerequisites

* Java 17 or higher
* MySQL 8.x
* Maven
* Docker & Docker Compose (for containerized setup)

---

## ▶️ How to Run the Application

### 1️⃣ Run Locally

1. Clone the repository:

   ```bash
   git clone https://github.com/ReddysaiJ/Rentivo.git
   ```
2. Change directory:

   ```bash
   cd rentivo/Rentivo
   ```
3. Update database credentials in `src/main/resources/application.properties` (example):

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/rentivo
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```
4. Build & run:

   ```bash
   mvn clean package
   mvn spring-boot:run
   ```
5. Open the app:

   ```
   http://localhost:8080
   ```

---

### 2️⃣ Run Using Docker

1. Ensure Docker and Docker Compose are installed.
2. From the project root (where `docker-compose.yml` exists), run:

   ```bash
   docker-compose up --build
   ```
3. Default application endpoint:

   ```
   http://localhost:8080
   ```
4. Persistent file uploads:

   * `docker-compose.yml` mounts `./uploads` on the host to `/app/uploads` inside the container.
   * Set `app.images.dir=/app/uploads` in `application-docker.properties` or pass as env var so the app reads/writes images to the mounted folder.

---

## 🗄 Database & Migrations

* MySQL is the primary database.
* Flyway is integrated for schema versioning and migrations.
* Migration scripts are in:

  ```
  src/main/resources/db/migration
  ```
* If Flyway reports validation errors after changing migrations, either:

  * Revert the half-applied changes from the DB and re-run migrations, or
  * Use `flyway repair` (only after manual inspection and understanding).

---


## 🚀 Future Enhancements

* Integrate online payment gateway
* Dynamic pricing based on demand
* Mobile application (Android/iOS)
* Analytics & advanced reporting
* CI/CD for automated Docker image builds and deployments

---

## 📌 Author

**Reddysai Jonnadula**
**Email:** [reddysai@gmail.com](mailto:reddysai2107@gmail.com)
