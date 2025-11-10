# Как использовать приложение Lab6

## Запуск приложения

```powershell
# Убедитесь, что используете Java 17
$env:JAVA_HOME = "$env:USERPROFILE\Java\jdk-17"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Запустите приложение
.\gradlew.bat bootRun
```

## Доступ к приложению

После запуска откройте в браузере:

- **Swagger UI (рекомендуется)**: http://localhost:8080/swagger-ui.html
- **API документация**: http://localhost:8080/api-docs
- **API базовый URL**: http://localhost:8080

## Работа через Swagger UI

1. Откройте http://localhost:8080/swagger-ui.html в браузере
2. Вы увидите два раздела:
   - **Product Controller** - управление продуктами
   - **User Controller** - управление пользователями

### Пример работы с продуктами:

1. **Создать продукт:**
   - Разверните `POST /api/products`
   - Нажмите "Try it out"
   - Вставьте JSON:
   ```json
   {
     "name": "Ноутбук",
     "description": "Игровой ноутбук",
     "price": 89999.99,
     "stockQuantity": 5
   }
   ```
   - Нажмите "Execute"

2. **Получить все продукты:**
   - Разверните `GET /api/products`
   - Нажмите "Try it out"
   - Нажмите "Execute"

3. **Найти продукт по ID:**
   - Разверните `GET /api/products/{id}`
   - Нажмите "Try it out"
   - Введите ID (например, 1)
   - Нажмите "Execute"

4. **Обновить продукт:**
   - Разверните `PUT /api/products/{id}`
   - Нажмите "Try it out"
   - Введите ID
   - Вставьте обновленные данные
   - Нажмите "Execute"

5. **Удалить продукт:**
   - Разверните `DELETE /api/products/{id}`
   - Нажмите "Try it out"
   - Введите ID
   - Нажмите "Execute"

### Пример работы с пользователями:

1. **Создать пользователя:**
   ```json
   {
     "username": "ivan_petrov",
     "email": "ivan@example.com",
     "firstName": "Иван",
     "lastName": "Петров"
   }
   ```

2. **Получить пользователя по username:**
   - Используйте `GET /api/users/username/{username}`

## Работа через curl (командная строка)

### Создать продукт:
```powershell
curl -X POST http://localhost:8080/api/products `
  -H "Content-Type: application/json" `
  -d '{\"name\":\"Телефон\",\"description\":\"Смартфон\",\"price\":29999.99,\"stockQuantity\":10}'
```

### Получить все продукты:
```powershell
curl http://localhost:8080/api/products
```

### Создать пользователя:
```powershell
curl -X POST http://localhost:8080/api/users `
  -H "Content-Type: application/json" `
  -d '{\"username\":\"test_user\",\"email\":\"test@example.com\",\"firstName\":\"Тест\",\"lastName\":\"Тестов\"}'
```

## Примеры использования API

### 1. Создание и получение продуктов

```powershell
# Создать продукт
$product = @{
    name = "Планшет"
    description = "Графический планшет"
    price = 15999.99
    stockQuantity = 3
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/products" `
  -Method POST `
  -ContentType "application/json" `
  -Body $product

# Получить все продукты
Invoke-RestMethod -Uri "http://localhost:8080/api/products" -Method GET
```

### 2. Создание и получение пользователей

```powershell
# Создать пользователя
$user = @{
    username = "alex_smith"
    email = "alex@example.com"
    firstName = "Алексей"
    lastName = "Смирнов"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/users" `
  -Method POST `
  -ContentType "application/json" `
  -Body $user

# Получить пользователя по username
Invoke-RestMethod -Uri "http://localhost:8080/api/users/username/alex_smith" -Method GET
```

## Логирование

Все запросы к API автоматически логируются через AOP. В консоли вы увидите:
- Время выполнения запроса
- Входные параметры
- Возвращаемые значения
- Ошибки (если есть)

## Проверка работы

1. Откройте Swagger UI: http://localhost:8080/swagger-ui.html
2. Попробуйте создать продукт через `POST /api/products`
3. Получите список продуктов через `GET /api/products`
4. Проверьте логи в консоли - там будет информация о каждом запросе

## Возможные проблемы

- **Порт 8080 занят**: Измените порт в `src/main/resources/application.properties`
- **Ошибка подключения**: Убедитесь, что приложение запущено
- **Ошибка валидации**: Проверьте формат JSON и обязательные поля

