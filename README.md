# 🛒 RetailX-Commerce-Engine
Backend service for RetailX e-commerce app using Spring Boot, JPA, and REST APIs.

## 📌 Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Web
- Lombok
- Controller Advice
- MySQL
- Maven
- Postman

## 📦 Core Entities

| Entity         | Description |
|----------------|-------------|
| `Customer`     | Stores customer details like name, mobile, and email |
| `CustomerAddress` | Linked 1:1 with customer, contains location data |
| `Order`        | Contains purchase order details and is linked to customer |
| `OrderItem`    | Line items for products in each order |
| `Product`      | Product catalog with stock and reviews |
| `Reviews`      | Customer reviews with rating and comment |
| `Payment`      | Handles payment information and generates a confirmation code |

## 🔄 Entity Relationships

- **Customer - Address**: One-to-One
- **Customer - Order**: One-to-Many
- **Order - OrderItems**: One-to-Many
- **Order - Payment**: One-to-One
- **Product - Reviews**: One-to-Many
- **Customer - Reviews**: Many-to-One
- **OrderItem - Product**: Many-to-One

- All custom exceptions are handled centrally in `APIExceptionHandler` using `@ControllerAdvice`
- Returns consistent error responses for:
  - Customer not found
  - Product not found
  - Product out of stock
  - Generic bad requests

## 🧪 Testing & API Usage
Use tools like **Postman** or **Swagger** (if enabled) to interact with:

- `/api/customers`
- `/api/products`
- `/api/orders`
- `/api/reviews`

---

## 🔐 Security Note

> Do not store sensitive payment information like raw card numbers or CVV. Always use tokenized or encrypted formats for production systems.

## 🚀 Running the App

1. Clone the repository
2. Set DB config in `application.properties`
3. Run:
   ```bash
   mvn spring-boot:run

## 🚧 Future Enhancements

- Role-based authentication (JWT)
- Order tracking
- Inventory sync with suppliers
- Product image storage (via AWS S3 or Cloudinary)
