# SaaS Access & License Optimization Platform

A backend system designed to help organizations manage SaaS applications, assign software licenses to employees, track license usage, and detect unused licenses to reduce unnecessary software costs.

Many companies purchase hundreds of SaaS licenses for tools like Slack, GitHub, Zoom, or Salesforce. Over time, a large number of these licenses remain unused, resulting in wasted money. This platform helps IT teams centrally manage SaaS applications, track license assignments, and identify unused licenses.


# Problem Statement

Organizations often subscribe to multiple SaaS tools and purchase many licenses for employees. However:

* Some licenses remain unused
* IT teams lack visibility into license usage
* Companies overspend on SaaS subscriptions

This platform provides a **centralized backend system** to manage SaaS applications and optimize license usage.

---

# Key Features

### User Management

* Add employees
* View employee information
* Manage employee SaaS access

### SaaS Application Management

* Add SaaS tools used in the organization
* Store vendor and license information
* Track total available licenses

### License Assignment

* Assign licenses to employees
* Prevent assigning licenses beyond availability
* Maintain license records

### License Usage Tracking

* Store last usage timestamp
* Track whether licenses are actively used

### Unused License Detection

* Detect licenses that have not been used for a specific time period
* Helps organizations reclaim and reuse licenses

### Admin Control

* Centralized management of SaaS tools
* Visibility of all assigned licenses
* License optimization insights

---

# Tech Stack

### Backend

* Java
* Spring Boot
* Spring Data JPA
* Hibernate

### Database

* MySQL / PostgreSQL

### Tools

* Maven
* Git
* GitHub
* Postman

### Optional Future Tools

* Swagger
* Docker
* Redis

---

# System Architecture

The system follows a **Layered Architecture**, commonly used in enterprise backend applications.

```
Client (Postman / Frontend)
        |
        v
Controller Layer
        |
        v
Service Layer
        |
        v
Repository Layer
        |
        v
Database (MySQL/PostgreSQL)
```

---

# Architecture Explanation

### Controller Layer

Handles HTTP requests and responses.

Responsibilities:

* Receive API requests
* Validate request data
* Call service layer methods
* Return API responses

Example controllers:

```
UserController
SaaSAppController
LicenseController
```

---

### Service Layer

Contains business logic.

Responsibilities:

* License assignment logic
* SaaS application management
* Unused license detection
* Data validation

Example services:

```
UserService
SaaSAppService
LicenseService
```

---

### Repository Layer

Handles communication with the database using **Spring Data JPA**.

Responsibilities:

* CRUD operations
* Query database tables
* Use JPA methods

Example repositories:

```
UserRepository
SaaSAppRepository
LicenseRepository
```

---

### Model Layer

Defines **database entities**.

Each entity maps to a database table.

Example models:

```
User
SaaSApplication
License
```

---

# Project Folder Structure

```
saas-platform
│
├── src/main/java/com/company/saas
│
│   ├── controller
│   │       UserController.java
│   │       SaaSAppController.java
│   │       LicenseController.java
│   │        
│   ├── service
│   │       UserService.java
│   │       SaaSAppService.java
│   │       LicenseService.java
│   │
│   ├── repository
│   │       UserRepository.java
│   │       SaaSAppRepository.java
│   │       LicenseRepository.java
│   │
│   ├── model
│   │       User.java
│   │       SaaSApplication.java
│   │       License.java
│   │
│   └── SaasPlatformApplication.java
│
├── src/main/resources
│       application.properties
│
└── pom.xml
```

---

# Database Schema

The system uses **three main entities**.

```
User
SaaSApplication
License
```

---

# Database Tables

## 1 Users Table

Stores employee information.

| Column     | Type   | Description         |
| ---------- | ------ | ------------------- |
| id         | Long   | Primary key         |
| name       | String | Employee name       |
| email      | String | Employee email      |
| department | String | Employee department |

---

## 2 SaaS Applications Table

Stores SaaS tool information.

| Column         | Type    | Description              |
| -------------- | ------- | ------------------------ |
| id             | Long    | Primary key              |
| name           | String  | Application name         |
| vendor         | String  | SaaS provider            |
| total_licenses | Integer | Total licenses purchased |

---

## 3 Licenses Table

Stores license assignment records.

| Column        | Type   | Description             |
| ------------- | ------ | ----------------------- |
| id            | Long   | Primary key             |
| user_id       | Long   | Assigned employee       |
| app_id        | Long   | SaaS application        |
| assigned_date | Date   | License assignment date |
| last_used     | Date   | Last usage timestamp    |
| status        | String | ACTIVE / UNUSED         |

---

# Entity Relationship Diagram

```
User
  |
  | 1
  |
  |------< License >------|
                           |
                           | 1
                           |
                     SaaSApplication
```

Explanation:

* One user can have multiple licenses
* One SaaS application can have multiple licenses
* License acts as a mapping between users and SaaS applications

---

# API Endpoints

## User APIs

Create User

```
POST /users
```

Get All Users

```
GET /users
```

Get User By ID

```
GET /users/{id}
```

Delete User

```
DELETE /users/{id}
```

---

## SaaS Application APIs

Add SaaS Application

```
POST /apps
```

Get All Applications

```
GET /apps
```

Get Application By ID

```
GET /apps/{id}
```

Delete Application

```
DELETE /apps/{id}
```

---

## License APIs

Assign License

```
POST /licenses/assign
```

Get All Licenses

```
GET /licenses
```

Detect Unused Licenses

```
GET /licenses/unused
```

---

# Example Workflow

### Step 1

Admin adds a SaaS application

Example

```
Name: Slack
Vendor: Slack Inc
Total Licenses: 100
```

---

### Step 2

Admin registers employees

Example

```
John
Sarah
David
```

---

### Step 3

Admin assigns license

Example

```
John -> Slack License
```

---

### Step 4

System tracks license usage

Example

```
last_used = 2026-03-01
```

---

### Step 5

System detects unused licenses

Example rule

```
If license unused for 30+ days
mark as UNUSED
```

---

# Installation & Setup

### 1 Clone Repository

```
git clone https://github.com/your-username/saas-platform.git
```

---

### 2 Navigate to Project

```
cd saas-platform
```

---

### 3 Configure Database

Edit `application.properties`

```
spring.datasource.url=jdbc:mysql://localhost:3306/saas_db
spring.datasource.username=root
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 4 Run Application

```
mvn spring-boot:run
```

Server will start on

```
http://localhost:8080
```

---

# API Testing

Use **Postman** to test APIs.

Example

Create User

```
POST http://localhost:8080/users
```

Request Body

```
{
"name": "John",
"email": "john@company.com",
"department": "Engineering"
}
```

---

# Future Improvements
* Frontend dashboard (React)
* SaaS usage analytics
* Slack / Google Workspace integration
* Automated license reclamation
* Notification system for unused licenses
* SaaS spending reports

---

# Learning Outcomes

This project demonstrates:

* Enterprise backend development using Spring Boot
* Layered architecture design
* REST API development
* Database design with JPA
* License management business logic
* Real-world SaaS management workflow

---

# Author

Naveed Ahmed

Java Developer | Backend Development | Spring Boot

