# Lab6 - Spring Boot REST API

This project implements a REST API using Spring Boot with the following features:

## Requirements

- **Java 17** (рекомендуется) или **Java 21** (проект настроен на Java 17)
- **Gradle 8.11+** (Gradle Wrapper включен)

⚠️ **Важно:** Если у вас установлена Java 24, рекомендуется установить Java 17 или Java 21, так как Gradle может иметь проблемы с Java 24.

## Quick Start

### Windows

1. **Убедитесь, что установлена Java 17 или Java 21:**
   ```powershell
   java -version
   ```
   
   ⚠️ Если у вас Java 24, установите Java 17 или Java 21 с [Adoptium](https://adoptium.net/)

2. **Соберите проект:**
   ```powershell
   .\gradlew.bat build -x test
   ```

3. **Запустите приложение:**
   ```powershell
   .\gradlew.bat bootRun
   ```
   
   Приложение будет доступно по адресу: http://localhost:8080

### Linux/Mac

1. **Убедитесь, что установлена Java 17 или Java 21:**
   ```bash
   java -version
   ```

2. **Соберите проект:**
   ```bash
   ./gradlew build -x test
   ```

3. **Запустите приложение:**
   ```bash
   ./gradlew bootRun
   ```
   
   Приложение будет доступно по адресу: http://localhost:8080

## Access the Application

Once the application is running:

- **API Base URL:** http://localhost:8080
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API Docs:** http://localhost:8080/api-docs

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

## Example API Requests

### Create a Product
```json
POST /api/products
Content-Type: application/json

{
  "name": "Laptop",
  "description": "Gaming laptop",
  "price": 1299.99,
  "stockQuantity": 10
}
```

### Create a User
```json
POST /api/users
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

## Technologies

- Spring Boot 3.2.0
- Spring AOP
- Lombok
- Swagger/OpenAPI 3
- Java 17
- Gradle 8.10+

## Troubleshooting

### Проблемы с версией Java

Если вы получаете ошибку "Unsupported class file major version":
- Убедитесь, что используете Java 17 или Java 21 (не Java 24)
- Проверьте переменную окружения JAVA_HOME
- Проект требует Java 17 (sourceCompatibility и targetCompatibility установлены на Java 17)
- Если у вас установлена Java 24, установите Java 17 или Java 21 с [Adoptium](https://adoptium.net/)

### Установка Java 17

1. Скачайте Java 17 с [Adoptium](https://adoptium.net/temurin/releases/?version=17)
2. Установите Java 17
3. Установите переменную окружения JAVA_HOME на путь к Java 17
4. Перезапустите терминал

### Port Already in Use

If port 8080 is already in use:
- Change the port in `src/main/resources/application.properties`:
  ```
  server.port=8081
  ```
