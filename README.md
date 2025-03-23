# Rentivo - Car Rental Management System

## By Reddysai Jonnadula

## Project Overview
Rentivo is a secure and scalable car rental platform built using Spring Boot. It enables users to browse available cars, book rentals, and process payments while providing administrators with tools to manage the entire system. The platform prioritizes security with BCrypt encryption for passwords, role-based access control, and automated email notifications for critical actions like bookings, cancellations, and updates.

## Scope
### Purpose of the System
The purpose of Rentivo is to provide an efficient, secure, and user-friendly car rental management system. It simplifies the car rental process by automating bookings, enhancing security, and ensuring seamless communication through mailing services. The system maintains accurate transaction records and improves the overall rental experience for both customers and administrators.

### Included in the Scope
- **User Management**: Secure registration, authentication, and profile updates.
- **Car Management**: Adding, updating, and deleting car listings.
- **Booking System**: Creating, modifying, and canceling bookings with real-time availability checks.
- **Payment Handling**: Confirming offline payments securely.
- **Admin Dashboard**: Centralized management of users, bookings, and cars.
- **Security Features**:
  - **BCrypt Password Hashing**: Protects user credentials.
  - **Role-based Access Control**: Ensures restricted admin functionalities.
  - **Email Notifications**: Automated emails for bookings, cancellations, and updates.

### Outside the Scope
- **Dynamic Pricing**: The system does not support automated price adjustments based on demand.
- **Online Payment Integration**: Payments are manually confirmed by admins.
- **Mobile App**: The platform is web-based and does not include a mobile version.

## Features
- **User Authentication**: Secure login, registration, and password management using BCrypt.
- **Car Management**: Admins can add, update, and remove cars with real-time availability.
- **Booking & Payments**: Users can book cars, and offline payments are confirmed by the admin.
- **Role-based Access Control**: Prevents unauthorized actions by distinguishing admin and user privileges.
- **Email Notifications**: Automatic emails for bookings, cancellations, and updates to enhance user communication.
- **Admin Dashboard**: A comprehensive panel for managing users, bookings, and cars efficiently.

## Technology Stack
- **Backend**: Java, Spring Boot, Hibernate (JPA)
- **Frontend**: HTML, CSS, Thymeleaf
- **Database**: MySQL
- **Security**: BCrypt for password hashing, Spring Security
- **Mailing**: Spring Boot Mail for notifications
- **Development & Deployment**: Maven, Git, IntelliJ IDEA/Eclipse

## Directory Structure
```
reddysaij-rentivo/
    └── Rentivo/
        ├── pom.xml
        ├── src/
        │   ├── main/java/com/rental/demo/
        │   │   ├── RentivoApplication.java
        │   │   ├── config/ (Security & Web Configurations)
        │   │   ├── controller/ (Admin, Car, Booking, User Controllers)
        │   │   ├── model/ (Car, Booking, User Models)
        │   │   ├── repository/ (JPA Repositories)
        │   │   ├── service/ (Business Logic)
        │   │   ├── resources/
        │   │   │   ├── application.properties
        │   │   │   ├── templates/ (HTML Views)
        │   │   │   ├── static/css/ (Styling)
        │   └── test/java/com/rental/demo/ (Test Cases)
```

## API Endpoints
### Authentication & User Management
- `GET /login` - User login page
- `GET /user/register` - User registration page
- `POST /user/register` - Register a new user
- `POST /user/update` - Update user profile
- `GET /user/profile` - View user profile

### Admin Operations
- `GET /admin/dashboard` - Admin dashboard
- `GET /admin/manageUsers` - Manage users
- `POST /admin/user/delete/{id}` - Delete a user
- `GET /admin/manageCars` - Manage cars
- `POST /admin/addCar` - Add a new car
- `POST /admin/car/update/{id}` - Update car details
- `POST /admin/car/delete/{id}` - Delete a car
- `GET /admin/manageBookings` - Manage bookings
- `POST /admin/payment/finish/{id}` - Confirm offline payment

### Booking Operations
- `GET /booking/create` - Booking page
- `POST /booking/create` - Create a new booking
- `GET /booking/myBookings` - View user bookings
- `POST /booking/cancel/{id}` - Cancel a booking
- `GET /booking/update/{id}` - Update booking details
- `POST /booking/update/{id}` - Submit booking updates
- `GET /booking/view/{id}` - View a specific booking
- `GET /booking/checkCarAvailability` - Check car availability

### Car Operations
- `GET /car/list` - List available cars

## Why These Features Matter
### Authentication & Security
**Why:** Secure authentication with BCrypt prevents unauthorized access and strengthens password security.

### Car Management
**Why:** Keeping car listings updated ensures accurate availability tracking and prevents double bookings.

### Booking & Payments
**Why:** A structured booking system allows users to easily manage their rentals while admins handle payment confirmations securely.

### Role-based Access Control
**Why:** Preventing unauthorized actions enhances security and system integrity.

### Email Notifications
**Why:** Automatic emails improve user engagement and provide real-time updates on bookings and cancellations.

### Admin Dashboard
**Why:** Centralized management reduces administrative workload and streamlines operations.

## Installation & Setup
### Prerequisites
- Java 17+
- MySQL Database
- Maven

### Steps to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/reddysaij/rentivo.git
   ```
2. Navigate to the project folder:
   ```sh
   cd reddysaij-rentivo/Rentivo
   ```
3. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/rentivo
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```
4. Build and run the project:
   ```sh
   mvn spring-boot:run
   ```
5. Open `http://localhost:8080` in a browser.

## Future Enhancements
- **Online Payments**: Integrate payment gateways for seamless transactions.
- **Mobile App**: Develop an Android/iOS version for better accessibility.
- **AI-based Recommendations**: Suggest cars based on user preferences.
