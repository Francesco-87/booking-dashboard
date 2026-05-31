# Booking & Operations Admin Dashboard

## Overview

The Booking & Operations Admin Dashboard is a full-stack web application designed to simulate the operational workflows of a service-based business.

The system allows administrators to manage services, staff profiles, users, and bookings through a centralized interface. The project focuses on practical business processes, validation logic, CRUD operations, database design, frontend/backend integration, and containerized deployment.

The application was built as a portfolio project to demonstrate backend development with Spring Boot, relational database design with PostgreSQL, frontend development with React, and deployment using Docker Compose.

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

User accounts remain in the system even when deactivated.

---

### Staff Profile Management

Staff profiles are managed separately from user accounts.

This design allows:

* Staff users without a performer profile
* Performer profiles linked to user accounts
* Future expansion of operational roles

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

Bookings support two customer types.

#### Registered Customers

Bookings can be linked directly to a CUSTOMER user account.

#### Guest Customers

Bookings can be created without a registered account using:

* Customer name
* Customer email

---

## Booking Validation

The backend enforces several business rules.

### Time Validation

Bookings cannot:

* Start in the past
* End before start time

### Staff Availability Validation

A staff member cannot have overlapping bookings.

The system validates existing bookings and rejects scheduling conflicts.

### Reference Validation

The system verifies that referenced entities exist before a booking is created or updated.

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

Instead, the application provides a dedicated cancellation workflow.

Endpoint:

```text
PATCH /api/bookings/{id}/cancel
```

The booking status is updated to:

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

This improves usability and prevents invalid manual ID entry.

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

Stores system users.

Key fields:

* id
* fullName
* email
* role
* isActive

### Staff Profiles

Stores bookable performers.

Key fields:

* id
* userId
* displayName
* description
* isActive

### Services

Stores available services.

Key fields:

* id
* name
* description
* durationMinutes
* priceCents
* isActive

### Bookings

Stores booking information.

Key fields:

* serviceId
* staffProfileId
* createdByUserId
* customerUserId
* customerName
* customerEmail
* startTime
* endTime
* status

---

## Architecture

### Backend Structure

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Responsibilities:

* Controllers expose REST endpoints
* Services contain business logic
* Repositories handle persistence
* DTOs separate API contracts from entities

### Frontend Structure

```text
Pages
    ↓
Components
    ↓
API Services
```

Responsibilities:

* Pages manage state
* Components handle rendering and user interaction
* API services communicate with backend endpoints

---

## Docker Support

The application is fully containerized using Docker and Docker Compose.

The deployment consists of three services:

* PostgreSQL database
* Spring Boot backend
* React frontend served through Nginx

Docker networking is used for communication between services.

The backend connects to PostgreSQL through the Docker service name rather than localhost.

A dedicated Spring Docker profile is used to separate containerized configuration from local development settings.

---

## Running the Application

### Prerequisites

* Docker Desktop
* Docker Compose

### Start Application

From the project root:

```bash
docker compose up --build
```

### Access Application

Frontend:

```text
http://localhost:5173
```

Backend API:

```text
http://localhost:8080
```

Example endpoint:

```text
http://localhost:8080/api/services
```

### Stop Application

```bash
docker compose down
```

---

## Design Decisions

### Soft Deactivation

Services, users, and staff profiles are deactivated rather than deleted.

Benefits:

* Preserves historical data
* Prevents broken references
* Reflects real business systems

### Separate Staff Profiles

Staff profiles are separated from user accounts.

Benefits:

* More flexible business model
* Cleaner booking relationships
* Easier future expansion

### Booking Cancellation Instead of Deletion

Bookings remain in the database.

Benefits:

* Historical tracking
* Auditing
* Operational reporting

### Guest Booking Support

Customers can create bookings without creating an account.

Benefits:

* Simpler booking workflow
* Supports one-time customers
* More realistic business process

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

These features were intentionally excluded to keep the project focused on demonstrating core operational workflows and full-stack development principles.

---

## Learning Outcomes

This project demonstrates:

* REST API design
* CRUD operations
* Spring Boot application architecture
* PostgreSQL database design
* Flyway database migrations
* Business rule validation
* React state management
* Component reuse
* Frontend/backend integration
* Error handling
* Docker containerization
* Multi-container application deployment
* Docker Compose orchestration
* Environment-specific configuration
* Full-stack application development
