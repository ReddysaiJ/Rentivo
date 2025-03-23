# Rentivo - Car Rental Management System

## By Reddysai Jonnadula

## Project Overview
Rentivo is a secure and scalable car rental platform built using Spring Boot. It enables efficient car bookings, user management, and offline payment verification. The system ensures data security with BCrypt encryption, role-based access control, and email notifications for user transactions.

## Scope
### Purpose of the System
The purpose of this system is to facilitate car rentals by allowing users to browse available cars, make bookings, and process payments while providing administrators with tools to manage cars, users, and bookings. The system enhances efficiency by automating rental processes, ensuring secure authentication, and maintaining accurate records of transactions. Additionally, it integrates security mechanisms to protect user data and provides an email notification system for booking confirmations and updates.

### Included in the Scope
- **User Management**: Registration, authentication, and profile updates.
- **Car Management**: Adding, updating, and deleting car listings.
- **Booking System**: Creating, modifying, and canceling bookings.
- **Payment Handling**: Confirming offline payments.
- **Admin Dashboard**: Managing users, bookings, and cars.
- **Security Features**: BCrypt password hashing, Spring Security, and role-based access control.
- **Email Notifications**: Automated emails for bookings, cancellations, and updates.

### Outside the Scope
- **Dynamic Pricing**: The system does not support automated price changes based on demand.
- **Online Payment Integration**: Currently, payments are manually confirmed by admins.
- **Mobile App**: The platform is web-based without a dedicated mobile application.

## Features
- **User Authentication**: Secure login, registration, and password management using BCrypt.
- **Car Management**: Admins can add, update, and remove cars with real-time availability.
- **Booking & Payments**: Users can book cars, and offline payments are confirmed by the admin.
- **Role-based Access Control**: Ensures secure admin and user functionalities.
- **Email Notifications**: Automated emails for bookings, cancellations, and updates.
- **Admin Dashboard**: Centralized control for managing users, bookings, and cars.

## Technology Stack
- **Backend**: Java, Spring Boot, Hibernate (JPA)
- **Frontend**: HTML, CSS, Thymeleaf
- **Database**: MySQL
- **Security**: BCrypt for password hashing, Spring Security
- **Mailing Service**: Spring Boot Mail for automated emails
- **Development & Deployment**: Maven, Git, IntelliJ IDEA/Eclipse

## Directory Structure
```
└── reddysaij-rentivo/
    ├── README.md
    └── Rentivo/
        ├── mvnw
        ├── mvnw.cmd
        ├── pom.xml
        ├── .gitattributes
        ├── .gitignore
        ├── src/
        │   ├── main/
        │   │   ├── java/com/rental/demo/
        │   │   │   ├── RentivoApplication.java
        │   │   │   ├── config/
        │   │   │   │   ├── SecurityConfig.java
        │   │   │   │   └── WebConfig.java
        │   │   │   ├── controller/
        │   │   │   │   ├── AdminController.java
        │   │   │   │   ├── CarBookingController.java
        │   │   │   │   ├── CarController.java
        │   │   │   │   ├── HomeController.java
        │   │   │   │   └── UserController.java
        │   │   │   ├── exception/
        │   │   │   │   ├── UserAlreadyExistsException.java
        │   │   │   │   └── UserNotFoundException.java
        │   │   │   ├── model/
        │   │   │   │   ├── Car.java
        │   │   │   │   ├── CarBooking.java
        │   │   │   │   ├── User.java
        │   │   │   │   └── UserDetailsImpl.java
        │   │   │   ├── repository/
        │   │   │   │   ├── CarBookingRepo.java
        │   │   │   │   ├── CarRepo.java
        │   │   │   │   └── UserRepo.java
        │   │   │   ├── service/
        │   │   │   │   ├── AdminService.java
        │   │   │   │   ├── CarBookingService.java
        │   │   │   │   ├── CarService.java
        │   │   │   │   ├── EmailService.java
        │   │   │   │   ├── UserDetailsServiceImpl.java
        │   │   │   │   └── UserService.java
        │   │   ├── resources/
        │   │   │   ├── application.properties
        │   │   │   ├── data.sql
        │   │   │   ├── static/css/style.css
        │   │   │   ├── templates/
        │   │   │   │   ├── addCar.html
        │   │   │   │   ├── adminDashboard.html
        │   │   │   │   ├── manageBookings.html
        │   │   │   │   ├── manageCars.html
        │   │   │   │   ├── manageUsers.html
        │   │   │   │   ├── my-bookings.html
        │   │   │   │   ├── registerUser.html
        │   │   │   │   ├── userHome.html
        │   │   │   │   └── mail/
        │   │   │   │       ├── booking.html
        │   │   │   │       ├── bookingCancellation.html
        │   │   │   │       ├── paymentFinished.html
        │   │   │   │       ├── updated.html
        │   │   │   │       └── welcome.html
        │   │   └── webapp/images/
        │   │       ├── cars/
        │   │       └── users/
        │   └── test/java/com/rental/demo/
        │       └── RentivoApplicationTests.java
        └── .mvn/wrapper/maven-wrapper.properties
```

## Installation & Setup
### Prerequisites
- Java 17+
- MySQL Database
- Maven

### Steps to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/ReddysaiJ/Rentivo.git
   ```
2. Navigate to the project folder:
   ```sh
   cd rentivo
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
- **Online Payments**: Integrate payment gateways.
- **Mobile App**: Develop an Android/iOS version.
- **AI-based Recommendations**: Suggest cars based on user preferences.

