# RTRN

RTRN is a simple application to create QR codes for personal items so that finders can return lost property.

This project uses a Java backend based on Spring Boot with a minimal HTML frontend rendered with Thymeleaf.

## Features
- User registration with a simple in-memory store
- Add items with a name, description, photo URL, and contact info
- Mark items as lost
- Generate QR codes linking to each item's page

The project is a minimal demonstration and does not include authentication or persistence.

## Running
Build the project with Maven:

```
mvn spring-boot:run
```

Then visit `http://localhost:8080` in your browser.
