
# 🚀 Spring Boot Microservices Ecosystem

A robust, enterprise-grade Microservices architecture built with Spring Boot 3.3.x and Spring Cloud. This project demonstrates centralized routing, security, resilient communication, and shared library management.

---

## 🏗 System Architecture
<img width="100%" alt="Image" src="https://github.com/user-attachments/assets/59607c99-33cd-4780-b256-e7c2d92ac0b6" />
The ecosystem consists of the following components:

- **Gateway Service (Port: 9000):** Central entry point using Spring Cloud Gateway with Auth Filter and Circuit Breaker (Resilience4j).
- **Auth Service:** Manages JWT-based authentication and user authorization.
- **Employee Service:** Handles employee-specific CRUD operations and business logic.
- **Address Service:** Manages location and address data for entities.
- **Common Library:** A shared Maven module containing reusable DTOs, Exception handlers, and Base Entities (AuditableEntity).



---

## 🛠 Tech Stack

* **Java:** 17
* **Framework:** Spring Boot 3.3.6
* **Service Mesh/Routing:** Spring Cloud Gateway
* **Resilience:** Resilience4j (Circuit Breaker & Time Limiter)
* **Database:** MySQL
* **Build Tool:** Maven
* **Utility:** Lombok, Spring Data JPA

---

## 📂 Project Structure

```text
├── common-lib/          # Shared models, exceptions, and utilities
├── api-gateway/         # Routing, AuthFilter, and Fallback logic
├── auth-service/        # Security and Token management
├── employee-service/    # Employee domain logic
└── address-service/     # Address domain logic
🚀 Getting Started
1. Prerequisites
JDK 17

MySQL Server

Maven 3.8+

2. Build the Common Library
Since other services depend on common-lib, you must install it to your local Maven repository first:

Bash

cd common-lib
mvn clean install


3. Run the Services
Start the services in the following order:

  1.Auth Service

  2.Employee/Address Services

  3.API Gateway

🛡 Features
    .Centralized Security: All requests pass through the AuthFilter in the Gateway.

    .Fault Tolerance: Circuit Breaker patterns implemented to prevent cascading failures using Resilience4j.

    .Audit Logging: Shared AuditableEntity in common-lib automatically tracks record creation and updates.

    .Global Exception Handling: Standardized error responses across all microservices.

🧪 Testing with Postman
Direct Call: POST http://localhost:8081/employees

Gateway Call: POST http://localhost:9000/employees (Requires JWT Token)
├── auth-service/        # Security and Token management
├── employee-service/    # Employee domain logic
└── address-service/     # Address domain logic
