# CS490 - Project Management - Car Rental System

**Professor** - Somesh Rao Pullapantula

**Team Members** 
1.  Hesham
2.  Kevin
2.  Luan Tran

## Scope
Our team will create an online car rental system where the car rental company can manage its car fleet, car rentals and its employees as well as enable the customers to rent cars and do the payment process using Visa or Mastercard. The customer shall be able to either register as a normal customer or as a frequent renter. The frequent renter account shall be one of 3 types: Bronze, Silver, or Gold. and deliver the minimum number of reports required that meets the customer requirements.

### Domain Driven Design (Essential Entities)
- Car
- User
- Reservation
- Payment
- Payment Method/Card

###  Functional  Requirements
#### Car Fleet Service
- Add Car
- Remove Car
- Update Car along with status (available, reserved, picked, under-maintenance)
- Status update upon car reservation (Kafka).
- Search Car (A filter search for cars based on model, make, base cost (per day))
- View/Fetch car information
- Get car rental history.
- Get car maintenance history.
- Product monthly, quarterly, and annual car rental reports.
- Export monthly, quarterly, and annual car rental reports.

#### Rental Service
- Get all cars.
- Search Car (A filter search for cars based on model, make, base cost (per day))
- Reserve a car (Send Kafka message to car fleet).
- Pick up a car (Send Kafka message to car fleet).
- Return a car (Payment included, Send Kafka message to car fleet).
- Process Payment
- Fetch/View customer rental history

#### Customer/User Service
- Add manager (Admin).
- Get customer car rental history (Manager).
- Disable customer account (Manager).
- Add Payment method (Customer, Frequent Renter)
- Current Reservations (Customer)
- Rental History (Customer)
- Login (JWT)
- Register

### Tools & Technologies used:
---
* Java
* HTML and CSS, JavaScript, TypeScript,  React
* MySQL - MySQL Workbench
* RESTful API
* Maven
* Spring Initializer
* Spring Security / JWT
* Spring Boot / Spring Data / Spring Cloud
* Microservices Stack
* Kafka
* Swagger UI
* Jasper Reports
* Manual Testing / JUnit Testing
* Iterative Software Development Life Cycle
* MS Project / Trello
* StarUML
* GitHub / Docker /  Postman
* IntelliJ / Visual Studio Code
* MaterialUI / React Bootstrap
