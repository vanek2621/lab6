# Lab6 - Spring Boot REST API

This project implements a REST API using Spring Boot with the following features:

## Features

1. **Two REST Controllers**:
   - `ProductController` - manages products
   - `UserController` - manages users

2. **Service Layer**:
   - `ProductService` - business logic for products
   - `UserService` - business logic for users

3. **Model Classes**:
   - `Product` - with manual constructors and setters
   - `User` - using Lombok annotations

4. **AOP Logging**:
   - Logs all incoming parameters
   - Logs return values
   - Measures execution time

5. **Swagger/OpenAPI Documentation**:
   - Available at: http://localhost:8080/swagger-ui.html
   - API docs at: http://localhost:8080/api-docs

## Running the Application

```bash
./gradlew bootRun
```

Or using Gradle wrapper:
```bash
gradlew.bat bootRun
```

## API Endpoints

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/search?name={name}` - Search products by name

### Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `GET /api/users/username/{username}` - Get user by username

## Technologies

- Spring Boot 3.2.0
- Spring AOP
- Lombok
- Swagger/OpenAPI 3
- Java 17

