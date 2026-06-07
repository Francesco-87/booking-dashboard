# Booking & Operations Admin Dashboard

## Business Problem

Service-based businesses often need to manage services, staff members, customers, and bookings through a centralized system. As booking volume increases, manual processes become difficult to maintain and can lead to scheduling conflicts, inconsistent customer information, and limited operational visibility.

This project simulates an internal operations platform designed to support these workflows. The system allows administrators to manage services, staff profiles, users, and bookings while enforcing business rules that help maintain scheduling accuracy and data integrity.

The application was built as a portfolio project to demonstrate full-stack software development using Spring Boot, React, PostgreSQL, and Docker.

---

## Project Goals

The goal of this project was not simply to build CRUD functionality, but to model realistic operational workflows commonly found in service businesses.

The project demonstrates:

* User and role management
* Staff profile administration
* Service catalog management
* Booking lifecycle management
* Scheduling validation
* Historical data preservation
* Full-stack architecture
* Containerized deployment

---

## Key Technical Challenges

### Booking Conflict Detection

The system prevents overlapping bookings for staff members.

When a booking is created or updated, the application validates existing bookings and rejects requests that would create scheduling conflicts.

### Support for Registered and Guest Customers

Bookings support two customer models:

* Registered customers linked to user accounts
* Guest customers without accounts

This reflects common business requirements where some customers prefer account-based management while others require only a one-time booking.

### Historical Data Preservation

Instead of deleting records, the application uses cancellation and deactivation workflows.

Benefits include:

* Preserving historical information
* Maintaining referential integrity
* Supporting future reporting capabilities
* Reflecting common enterprise application patterns

### Layered Validation

Validation is implemented at multiple levels:

* Frontend validation
* Backend business validation
* Database constraints

This helps ensure data integrity regardless of how the system is accessed.

---

## Design Decisions

### Separate Staff Profiles and User Accounts

Staff profiles are intentionally separated from user accounts.

Benefits:

* Staff users without performer profiles
* Cleaner booking relationships
* Greater flexibility in the domain model
* Easier future expansion

### Booking Cancellation Instead of Deletion

Bookings are cancelled rather than deleted.

Benefits:

* Historical tracking
* Auditing capabilities
* Data preservation

### Soft Deactivation

Users, services, and staff profiles can be deactivated rather than removed.

Benefits:

* Preserves historical references
* Prevents broken relationships
* Reflects real-world business systems

---

## Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Flyway

### Frontend

* React
* Vite
* JavaScript
* CSS

### Infrastructure

* Docker
* Docker Compose
* Nginx

### Development Tools

* Git
* Postman
* VS Code

---

## System Architecture

```text
React Frontend
       │
       ▼
Spring Boot REST API
       │
       ▼
PostgreSQL Database
```

Backend responsibilities:

* REST API endpoints
* Business rule validation
* Data persistence
* DTO mapping
* Exception handling

Frontend responsibilities:

* User interface
* Form validation
* State management
* API communication

---

## Project Structure

```text
booking-dashboard/
│
├── backend/
│   ├── controllers/
│   ├── services/
│   ├── repositories/
│   ├── dtos/
│   ├── entities/
│   └── exceptions/
│
├── frontend/
│   ├── pages/
│   ├── components/
│   ├── services/
│   └── styles/
│
├── docker-compose.yml
│
└── README.md
```

---

## Core Features

### Service Management

Administrators can:

* Create services
* Update services
* Activate services
* Deactivate services

Each service contains:

* Name
* Description
* Duration
* Price
* Active status

---

### User Management

Administrators can:

* Create users
* Update users
* Activate users
* Deactivate users

Supported roles:

* ADMIN
* STAFF
* CUSTOMER

---

### Staff Profile Management

Administrators can:

* Create staff profiles
* Update staff profiles
* Activate staff profiles
* Deactivate staff profiles

Each staff profile contains:

* Display name
* Description
* Linked user account
* Active status

---

### Booking Management

Administrators can:

* Create bookings
* Update bookings
* Cancel bookings

Bookings support:

#### Registered Customers

Bookings linked to CUSTOMER accounts.

#### Guest Customers

Bookings created using:

* Customer name
* Customer email

without requiring an account.

---

## Booking Validation

### Time Validation

Bookings cannot:

* Start in the past
* End before start time

### Staff Availability Validation

The system prevents overlapping bookings for staff members.

Conflicting bookings are rejected before being saved.

### Reference Validation

The application verifies that referenced entities exist before a booking can be created or updated.

Validation includes:

* Service exists
* Staff profile exists
* User references exist

---

## Booking Lifecycle

### Active Booking

Bookings are initially created with an active status.

### Booking Cancellation

Bookings are not deleted.

Endpoint:

```text
PATCH /api/bookings/{id}/cancel
```

Status becomes:

```text
CANCELLED
```

This preserves historical booking data.

---

## Frontend Features

### Dashboard Navigation

The application includes:

* Home Dashboard
* Admin Dashboard
* Customer Placeholder Page

### Reusable Components

Examples include:

* BackButton
* ServiceForm
* UserForm
* StaffForm
* BookingForm

### Dropdown-Based Booking Creation

Booking creation uses dropdown selections for:

* Services
* Staff profiles
* Users

This prevents invalid manual ID entry and improves usability.

### Role-Aware User Selection

Customer selection only displays users with the CUSTOMER role.

This prevents administrators and staff accounts from being selected as customers.

### Frontend Date Validation

The booking form prevents users from:

* Selecting start times in the past
* Selecting end times before the selected start time

Backend validation remains the final authority.

---

## Database Design

### Users

Stores user accounts and role information.

### Staff Profiles

Stores bookable staff members linked to user accounts.

### Services

Stores available services.

### Bookings

Stores scheduling and customer information.

Relationships:

```text
Users
   │
   └── Staff Profiles

Services
   │
   └── Bookings

Staff Profiles
   │
   └── Bookings

Users
   │
   └── Bookings
```

---

## Docker Support

The application is fully containerized using Docker and Docker Compose.

Deployment consists of:

* PostgreSQL database
* Spring Boot backend
* React frontend served through Nginx

The backend connects to PostgreSQL through Docker networking using service names rather than localhost.

A dedicated Spring Docker profile is used to separate containerized and local development configurations.

---

## Running the Application

### Prerequisites

* Docker Desktop
* Docker Compose

### Clone Repository

```bash
git clone <repository-url>
cd booking-dashboard
```

### Start Application

```bash
docker compose up --build
```

### Access Application

Frontend:

```text
http://localhost:5173
```

Backend:

```text
http://localhost:8080
```

Example API endpoint:

```text
http://localhost:8080/api/services
```

### Stop Application

```bash
docker compose down
```

---

## Future Improvements

Potential future enhancements:

* Authentication and authorization
* Role-based page access
* Customer self-service portal
* Staff scheduling
* Availability management
* Reporting dashboard
* Email notifications

These features were intentionally excluded to keep the project focused on core operational workflows and full-stack application development.

---

## Learning Outcomes

This project demonstrates practical experience with:

* Java application development
* Spring Boot architecture
* REST API design
* CRUD operations
* PostgreSQL database design
* Flyway database migrations
* Business rule implementation
* DTO-based API design
* Validation strategies
* Exception handling
* React frontend development
* Component reuse
* Frontend/backend integration
* Docker containerization
* Multi-container deployment
* Docker Compose orchestration
* Environment-specific configuration
* Full-stack application development
