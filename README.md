# Shopix
## Shopix – Modular E-commerce REST Backend

### Technológiák:
- Java 17+
- Spring Boot 3.5.5
- Spring Web
- Spring Data JPA (Hibernate)
- Lombok
- REST API's
- Jakarta Validation
- Spring Security (JWT, előkészítve)
- Swagger (Springdoc OpenAPI)
- Unit tesztek (JUnit 5 & Mockito & Spring Boot Test, később)

---

## Mi ez?  
Saját gyakorló projekt, ahol egy webshop backend rendszerét építem fel modulárisan Spring Boot segítségével.  
A projekt célja, hogy **valós e-commerce funkciókat** gyakoroljak (felhasználó kezelés, termékek, kategóriák, kosár, rendelések, fizetések).  
A kód Postman segítségével tesztelhető frontend nélkül.

---

## Jelenleg kész:
- **Alap konfigurációk**
  - Spring Boot alkalmazás indítása
  - Adatbázis kapcsolat (MySQL)
  - Global Exception Handling

- **Modellek**
  - Address, Cart, CartItem, Category, Inventory, Order, OrderItem, Payment, Product, User

- **Repository réteg**
  - Minden entitáshoz JPA repository

- **Category modul**
  - DTO-k (Request, Response)
  - Mapper
  - Service (lapozás, ID alapú lekérés)
  - Controller + Swagger dokumentáció
  - Tesztadatok az adatbázisban

- **Product modul**
  - DTO-k (Request, Response)
  - Mapper
  - Service (lapozás, ID, név, kategória szerinti lekérés, aktív szűrés)
  - Controller + Swagger dokumentáció
  - Tesztadatok (5 termék)

---

## Következő lépések:
- User regisztráció & autentikáció (Spring Security + JWT)
- Kosár (Cart, CartItem) műveletek
- Rendelések kezelése
- Fizetések (mock integration)
- Swagger bővítése és végpontok részletes dokumentálása
- Unit és integrációs tesztek írása

