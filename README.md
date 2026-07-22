# Library Management System

A RESTful Library Management System built with **Java** and **Spring Boot** that allows users to manage books and borrowing operations.
The project follows clean architecture principles and includes **Behavior-Driven Development (BDD)** tests using **Cucumber**.

---

## Technologies Used

- Java 17
- Spring Boot
- Maven
- JUnit, Mokhito and Web Layer Testing
- Cucumber (BDD)
- REST APIs
- Git & GitHub

---

## Project Structure

```
src
├── main
│   ├── java
|   |   ├── configuration
│   │   ├── controller
│   │   ├── model
│   │   ├── repository
│   │   └── service
│   │    
│   └── resources
│
└── test
    ├── java
    |   ├── com.example.librarymanagementrestapi
    |   |   ├── controller
    │   │   └── service
    │   │ 
    │   ├── runners
    │   └── stepDefinitions
    └── resources
        └── features
```


---

## Running the Project

### Clone the repository

```bash
git clone https://github.com/Almo3tasem200/Library-Management-REST-API
```

### Navigate to the project

```bash
cd library-management-rest-api
```

### Build the project

```bash
mvn clean install
```

### Run the application

```bash
mvn spring-boot:run
```

The application will start on:

```
http://localhost:8080
```


## Testing

The project uses
• **JUnit**
• **Mockito**
• **Web Layer Testing**
• **Behavior-Driven Development (BDD)** with **Cucumber**.


Run the tests with:

```bash
mvn test
```


---

## Author

**Almoatasem Elemam**

Computer Engineer | Software Developer

---

## License

This project is intended for educational and learning purposes.
