

Real Estate Backend

Description

This repository contains the backend code for a Real Estate web application.
actually my application allows users to creaate accounts on this web application using frontend on any tools that test endpoint of a backend 
after he will receive a notification email that his account has been created, also it includes user authorization and authentication using spring security and jwt
it servers all user management all CRUD operations, also same for Houses and Bookings, other thing for houses it involves multipart file handling also when an admin upload new house
with images that image is saved in directory called images under my source folder in resources 	then the image path is what saved in database,!
this backend project involves the cross-origin configuration which allow this api to communicate with my frontend side also it include a staticResourceConfig class to handle 
the display of those images on frontend, It manages property data, user authentication, and serves as the backend for the associated frontend application.

Features

House Management: CRUD operations for managing property listings.
Booking Management: CRUD operations for managing property listings.
User Management: Authentication, registration, and user roles.
File Upload: Handle image uploads for property images.
RESTful API: Serve data to the frontend via API endpoints.
Security: Authorization and access control for different user roles.
Technologies Used
Spring Boot: Backend framework for Java applications.
Spring Security and JWT: Authentication and authorization management.
Spring Data JPA: Data access and management.
Caffeine: for caching 
Postgres: Database for storing property and user data.
Maven: Dependency management for Java projects.
JavaMail: for sending Emails.

API Endpoints
POST /houses/saveHouse: Add a new property to the database.
GET /houses/getAllHouse: Retrieve All Houses.
GET /houses/getOneHouse/{id}: Retrieve details of a specific property.
PUT /houses/updateHouse/{id}: Update details of a specific property.
DELETE /houses/deleteHouse/{id}: Delete a property.

POST /users/register: for sign up.
POST /users/authenticate: log in.
GET /users/allUsers: Retrieve all user.
GET /users/getOneUser/{id}: Retrieve details of a specific User.

POST /booking/saveBook: booking of new House.
GET /booking/allBooking: Retrieve All Houses.
GET /booking/getOneBook/{id}: Retrieve details of a specific property.
DELETE /booking/deleteBooking/{id}: Delete or cancel booking.

Setup Instructions

Clone the repository
Open the project in your preferred Java IDE (like IntelliJ IDEA).
Configure the database settings in application.properties.
Run the application.


Contributing
Contributions are welcome! Fork the repository and submit a pull request for new features or bug fixes.
