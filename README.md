Product Discount Management System

Requirements

For building and running the application you need:

•	Java 11

•	Maven

•	Postgral

Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the com.product.discount.DiscountApplication class from your IDE.
mvn spring-boot:run

API Documentation

POST /product/discount

This endpoint calculates and applies discounts to products based on the provided discount type (flat or percentage).

Response:

•	200 OK: The response will return the total discounted product price after applying the discount.

•	400 BAD REQUEST: If the request data is invalid (e.g., invalid product ID, discount type, or out-of-stock product).

GET /product/{productId}

This endpoint fetches the product details by its product id, including the total discounted product price after any applicable discounts.

Response:

•	200 OK: Returns product details with the discounted price.

•	404 NOT FOUND: If the product with the provided product id does not exist.

Testing the Application:

Using Postman or cURL

You can test the endpoints using Postman or cURL.

Example cURL for POST /product/discount:

curl --location 'http://localhost:8087/product/discount' \
--header 'Content-Type: application/json' \
--data '{
  "productId":10,
  "discountType":"percentage",
  "discountValue":20,
  "seasonalDiscountActive":true,
  "productPrice":1,
  "quantity":5
}'


Example cURL for GET /product/{productId}:

curl --location 'http://localhost:8087/product/1'a

