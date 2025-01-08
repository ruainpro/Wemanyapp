# WemanayApp Implementation - README
## Introduction
WemanayApp is a feature-rich, modular application designed to facilitate seamless user interactions, CO2 emissions tracking, and product management. Built on a robust three-tier architecture, it ensures scalability, security, and ease of use.

## System Architecture
### Three-Tier Architecture
#### Presentation Tier: User-facing Android application built with Java, XML, and Gradle.
#### Business Logic Tier: Backend logic powered by Spring Boot to handle APIs and core functionalities.
##### Data Tier: MongoDB Atlas for dynamic, scalable, and cloud-based data storage.

## Core Features
#### User Authentication: Secure login and signup with form validations.
#### CO2 Emissions Tracker: Calculate emissions and generate reports.
#### Product Management: Search, view, and purchase products.
#### Cart Functionality: Add, manage, and checkout products with payment integration.
#### User Dashboard: Access purchase history and account details.
## Technology Stack
### Frontend: Android application using Java and XML.
### Backend: RESTful APIs with Spring Boot.
### Database: MongoDB Atlas for NoSQL storage.
### Build Tools: Maven and Gradle for dependency management.
### Testing Frameworks: JUnit for unit testing; Postman for API validation.

## Detailed Components
### Backend
Controller: API route handling.
Service Layer: Business logic implementation.
Repository: Database operations.
Security: JWT-based authentication for secure data access.
### Frontend
Manifest: App configuration settings.
Java Codebase: Activities, fragments, and event handlers.
Resources (res): UI elements such as layouts, icons, and styles.
Gradle Scripts: Dependency configurations and build settings.
### Database Design
Collections:
Users: Stores user credentials and roles.
Roles: Defines user access levels (ADMIN, USER).
Products: Contains product details like name, price, and CO2 emission values.
Carts: Tracks items added by users.
Purchasing: Logs purchase details, payment info, and receipts.

## Testing and Validation
### Black Box Testing
Validates functional features like login, signup, and cart operations.
Ensures proper error handling and input validation.
### White Box Testing
### Unit Testing: JUnit tests for backend methods.
### API Testing: Postman validation for endpoint functionality and performance.

## Setup and Deployment
### Backend:
Configure application.properties for MongoDB integration.
Start the Spring Boot application.
### Frontend:
Compile the Android project with Gradle.
Deploy the APK to an Android device.
### Contributions
We welcome contributions to enhance the app. Steps to contribute:

## Clone the repository.
Create a feature branch and make modifications.
Test locally and submit a pull request for review.
Contact and Support
For queries, issues, or feature requests, contact the maintainers directly or open an issue in the repository.

License
This project is licensed under [Insert License Name]. See LICENSE file for details.

"Designed to Simplify User Experience and Optimize Efficiency."
