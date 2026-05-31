# Booking & Operations Admin Dashboard

## Overview

The Booking & Operations Admin Dashboard is a full-stack web application built to simulate the core operational workflows of a service-based business.

The system allows administrators to manage services, staff profiles, users, and bookings through a centralized interface. The project focuses on practical business workflows, validation logic, CRUD operations, database design, and frontend/backend integration.

The application was developed as a portfolio project to demonstrate backend development with Spring Boot, relational database design with PostgreSQL, and frontend development with React.

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

### Development Tools

* Docker
* Docker Compose
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

Bookings support two customer types:

#### Registered Customers

Bookings can be linked directly to a CUSTOMER user account.

#### Guest Customers

Bookings can be created without a registered account using:

* Customer name
* Customer email

---

## Booking Validation

The system includes business validation rules on the backend.

### Time Validation

Bookings cannot:

* Start in the past
* End before start time

### Staff Availability Validation

A staff member cannot be assigned to overlapping bookings.

The system checks existing bookings and rejects conflicting reservations.

### Reference Validation

The system verifies:

* Service exists
* Staff profile exists
* User references exist

before a booking is created or updated.

---

## Booking Lifecycle

### Active Booking

A booking is initially created with an active status.

### Cancellation

Bookings are not deleted.

Instead, the system provides a dedicated cancellation workflow:

PATCH

/api/bookings/{id}/cancel

The booking status is changed to:

CANCELLED

This preserves historical booking information.

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

This replaces manual ID entry and improves usability.

### Frontend Validation

The booking form prevents:

* Selecting start times in the past
* Selecting end times before start time

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

Controller

↓

Service

↓

Repository

↓

Database

Responsibilities:

* Controllers expose REST endpoints
* Services contain business logic
* Repositories handle persistence
* DTOs separate API contracts from entities

---

### Frontend Structure

Pages

↓

Components

↓

API Services

Responsibilities:

* Pages manage state
* Components handle UI rendering
* API services communicate with backend endpoints

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

Customers can book without creating an account.

Benefits:

* Simpler booking flow
* Supports walk-in or one-time customers

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

These features were intentionally excluded to keep the project focused on demonstrating core business operations and full-stack development skills.

---

## Learning Outcomes

This project demonstrates:

* REST API design
* CRUD operations
* Spring Boot application architecture
* PostgreSQL database design
* Business rule validation
* React state management
* Component reuse
* Frontend/backend integration
* Error handling
* Full-stack application development
