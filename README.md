## 🛒 OpenStall Marketplace API

A "Smart" Backend for Modern E-commerce
OpenStall is a robust, scalable backend system built with Spring Boot 3. It bridges the gap between traditional e-commerce logic and AI-enhanced features, focusing on strict data integrity, secure transactions, and modular architecture.

## 🚀 Key Features

📦 Marketplace Engine
-Dual-User Role System: Supports both Buyers and Suppliers with distinct profiles.
-Inventory & Order Logic: * Atomic Transactions: Prevents stock overselling using database-level snapshots.
-Soft Delete: Sophisticated deactivation logic that cascades from Suppliers to their active products.
-Persistent Cart Management: Seamlessly tracks items for buyers.
-Database Security: Implemented role-based and mandatory access control (MAC) using MY-SQL.

🔐 Security & Identity
-JWT Authentication: Transitioning to a stateless, token-based system.
-Password Protection: Industry-standard hashing using BCrypt.
-Granular Authorization: Using @PreAuthorize to ensure users can only access resources permitted by their roles.

## 🛠️ Technical Stack
Framework: Spring Boot 3.x
Language: Java 21
Database: MySQL 
Security: Spring Security 6, JWT (io.jsonwebtoken)
Persistence: Spring Data JPA (Hibernate)

Tools: Lombok, Postman, Maven

## 🚦 Getting Started

Prerequisites
-JDK 17 or higher
-Maven
-MySQL

Installation
Clone the repository:

Bash
git clone https://github.com/leawi-t/open-stall.git

Configure the database:
Update src/main/resources/application.properties with your SQL credentials.

Run the application:

Bash
mvn spring-boot:run

## 🧪 API Examples (Testing via Postman)

1. Placing an Order
POST /api/orders/place/{userId}
```
JSON
{
  "street": "123 Tech Lane",
  "city": "Addis Ababa",
  "zipCode": "1000"
}
```

2. Authentication (JWT)
POST /api/auth/login
Returns: {"token": "eyJhbG..."}
