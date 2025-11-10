# Быстрый старт

## Проблема с Java 24

У вас установлена Java 24, которая слишком новая для текущих версий Gradle и Spring Boot.

## Решение 1: Установить Java 17 (рекомендуется)

### Автоматическая установка:
```powershell
.\install-java17.ps1
```

Затем установите переменную окружения:
```powershell
[System.Environment]::SetEnvironmentVariable('JAVA_HOME', 'C:\Program Files\Eclipse Adoptium\jdk-17', 'User')
```

Перезапустите терминал и запустите:
```powershell
.\gradlew.bat bootRun
```

### Ручная установка:
1. Скачайте Java 17: https://adoptium.net/temurin/releases/?version=17
2. Установите Java 17
3. Установите переменную окружения JAVA_HOME на путь к Java 17
4. Перезапустите терминал

## Решение 2: Использовать Java 21

Java 21 также поддерживается:
1. Скачайте Java 21: https://adoptium.net/temurin/releases/?version=21
2. Установите Java 21
3. Установите JAVA_HOME на путь к Java 21

## После установки Java 17/21:

```powershell
# Собрать проект
.\gradlew.bat build -x test

# Запустить приложение
.\gradlew.bat bootRun
```

Приложение будет доступно по адресу: http://localhost:8080

