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
  - SecurityConfig (Swagger, publikus endpointok engedélyezve)

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

- **Cart modul**
  - DTO-k (CartResponse, CartItemResponse, AddCartItemRequest, CartItemUpdateRequest)
  - Mapper
  - Repository-k bővítve
  - Service: kosár lekérés, termék hozzáadás, frissítés (mennyiség → update/törlés), teljes kiürítés
  - Controller: `/cart` endpointok (by-user, add, update, clear)
  - Postman tesztek sikeresen lefutottak

- **Security modul**
  - User regisztráció & autentikáció (Spring Security + JWT) 

- **Order modul**
  - DTO-k (OrderResponse, OrderItemResponse, CreateOrderRequest)
  - Mapper
  - Service: rendelés létrehozása kosárból (shipping/billing cím snapshot-olva), rendelés listázás, rendelés lekérés azonosító alapján
  - Controller: `/orders` endpointok (create, my, getById)
  - Order entitás bővítve cím snapshot mezőkkel (shipping/billing street/city/zip/country)
  - Cart lezárása rendelés létrehozásakor

- **Payment modul**
  - DTO (PaymentResponse)
  - Mapper
  - Service: fizetés mock-kal, providerRef = `MOCK-{orderId}`
  - Fizetés után a rendelés státusza `PAID` lesz
  - Idempotens működés: ha már létezik Payment, ugyanazt adja vissza, és a rendelés státuszát garantáltan `PAID`-re állítja
  - Controller: `/orders/{id}/pay`

- **Address modul**
  - DTO-k (AddressRequest, AddressResponse)
  - Mapper
  - Service: saját címek kezelése
  - Controller: `/addresses/me` CRUD végpontok
  - Validációk és tulajdon-ellenőrzés

---

## Következő lépések:
- Swagger bővítése és végpontok részletes dokumentálása
- Unit és integrációs tesztek írása
- Inventory modul bevezetése (készletkezelés)
- Admin funkciók (pl. rendelés státuszok kezelése)
