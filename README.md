# Product Recommendation System with MongoDB

This project is a backend application designed to manage a product database, handle user interactions such as liking and purchasing products, and provide personalized product recommendations based on user activity. The system is implemented using modern Java frameworks and connected to a NoSQL MongoDB database for storage.

---

## Features
- User Management: User registration, authentication, and authorization.
- Product Management: Add, update, retrieve, and delete products.
- Likes and Recommendations: Users can like products, and recommendations are provided based on categories they have interacted with.
- Purchase Handling: Users can purchase products, reducing the product quantity and storing purchase history.
- Search Functionality: Search for products by name or category, supporting partial matches.
- Performance Testing: Measure the performance of the system, such as loading all products from the database.

---

## Technologies Used

### Backend
- Java 21: The core programming language for the application.
- Spring Boot: A framework for building the backend REST API.
  - Spring Data MongoDB: To interact with the MongoDB database.
  - Spring Security: To handle authentication and authorization with JWT.
  - Springdoc OpenAPI: For automatic API documentation and Swagger UI.

### Database
- MongoDB: A NoSQL database used for storing users, products, likes, and purchases.

### Testing
- JUnit: For writing unit tests to validate application behavior.
- MockMvc: For testing API endpoints without deploying the application.

---

## How It Works

1. User Authentication:
   - Users can sign up and log in with secure password hashing (BCrypt).
   - JWT tokens are issued upon login for session management.

2. Product Management:
   - Products are stored in MongoDB and categorized (e.g., phones, laptops).
   - Each product has attributes like name, description, price, quantity, and a list of users who liked it.

3. Likes and Recommendations:
   - Users can like products, which increases the like count.
   - Recommendations are provided based on product categories that the user liked or purchased, sorted by popularity (like count).

4. Purchases:
   - Users can purchase products, which reduces the available quantity.
   - Each purchase is recorded in a purchase history, storing the product ID and category.

5. Performance Testing:
   - Performance of fetching all products can be measured programmatically using JUnit or through load-testing tools like JMeter.

6. API Documentation:
   - Swagger UI is available at /swagger-ui/index.html for interactive API exploration.

---

## How to Run

1. Clone the Repository:
   ```bash
   git clone https://github.com/your-repository-url.git```
   cd your-repository-folder
2. Set Up MongoDB:
   - Ensure MongoDB is installed and running locally or on a server.
   - Update the ```application.properties``` file with your MongoDB connection string.
3. Build and Run the Application:
   ```bash
   mvn clean install
   mvn spring-boot:run```
4. Access the Application:
   - Swagger UI: ```http://localhost:8080/swagger-ui/index.html```
   - API Endpoints: Use Postman or Curl to interact with the endpoints.
