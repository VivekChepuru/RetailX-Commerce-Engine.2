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

## 🗂️ Modules Breakdown

👤 Customer Module
- CRUD operations for customer.
- Customers can place orders and write reviews.

📦 Product Module
- Stores product metadata.
- Validates stock before confirming order.

🛒 Order Module
- Order creation and status management.
- Calculates subtotal, tax (8%), shipping charges (free > $35), and total.
- Allows cancellation and address updates.

💳 Payment Module
- Captures payment info.
- Mocks confirmation with a random UUID substring.

✍️ Review Module
- Customers can review purchased products.
- Retrieve reviews by product or customer.

⚠️ **Exception Handling**  
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

## 🔄 Sample API Flow

Step 1: Register a Customer
`POST /customer/enroll`
```JSON
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "mobileNumber": "1234567890"
}
```
Step 2: Add Products
`POST /product/add`
```JSON
{
  "productName": "Water Bottle",
  "price": 5.99,
  "description": "500ml Mineral Water",
  "availableQuantity": 100
}
```
Step 3: Place an Order
`POST /order/create`
```JSON
{
  "customerId": 1,
  "items": [
    { "productId": 1, "orderQuantity": 2 }
  ],
  "shippingAddress": {
    "street": "123 Elm St",
    "city": "Springfield",
    "zipCode": "62704"
  },
  "payment": {
    "paymentMethod": "CREDIT",
    "cardNumber": "1111222233334444",
    "cvv": "123",
    "expirationDate": "1230"
  }
}
```
Step 4: Cancel Order
`PUT /order/status/cancel?orderId=1`

Step 5: Add a Review
`POST /review/write`
```JSON
{
  "customerId": 1,
  "productId": 1,
  "comment": "Great product!",
  "rating": "FIVE"
}
```
---

## 🔐 Security Note

> Do not store sensitive payment information like raw card numbers or CVV. Always use tokenized or encrypted formats for production systems.

## 🚀 Running the App

### **Prerequisites**
- Java 17+
- Maven 3.8+
- MySQL/PostgreSQL (or your preferred DB)

### **Steps**
1. **Clone the repository**  
   ```bash
   git clone https://github.com/VivekChepuru/RetailX-Commerce-Engine.git
   cd repo

2. Set DB config in `application.properties`
   
3. Run:
   ```bash
   mvn spring-boot:run

## 🚧 Future Enhancements

- Role-based authentication (JWT)
- Order tracking
- Inventory sync with suppliers
- Product image storage (via AWS S3 or Cloudinary)

**Feel free to clone the repository and explore the project structure and features!**
