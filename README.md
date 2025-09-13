# Food Delivery Platform Microservices

demonstration of a food delivery platform built using a microservices architecture., including the API Gateway, Saga, and Aggregator patterns.

## How to Run

1.  **prerequisites:** Java 17 and Maven.
2.  **make scripts executable:** Open a terminal in the project root and run `chmod +x *.sh`.
3.  **start services:** Run the appropriate script for your operating system to start all microservices in separate terminal tabs.
      * **fedora/KDE plasma:** `./start-services-fedora.sh`
4.  **stop services:** To stop all services, run `./stop-services.sh`.

## API Endpoints

all requests sent to the API Gateway at `http://localhost:8080`. You can use the provided Postman collection to test these endpoints.

### 1\. Successful Order (Happy Path)

  * **Endpoint:** `POST /order/place`
  * **Description:** Simulates a successful order placement where all steps in the saga complete.
  * **Sample Body:**
    ```json
    {
        "customerId": "123",
        "restaurantId": "456",
        "items": []
    }
    ```
  * **Expected Response:** A `200 OK` with the message `"Order placed successfully!"`.

### 2\. Order with Payment Failure

  * **Endpoint:** `POST /order/place`
  * **Description:** Simulates a payment failure, triggering a compensating transaction to roll back the food reservation.
  * **Sample Body:**
    ```json
    {
        "customerId": "FAIL_PAYMENT",
        "restaurantId": "456",
        "items": []
    }
    ```
  * **Expected Response:** A `200 OK` with the message `"Payment failed, order rolled back"`.

### 3\. Order with Delivery Assignment Failure

  * **Endpoint:** `POST /order/place`
  * **Description:** Simulates a failure to assign a delivery partner, triggering a full rollback of payment and food reservation.
  * **Sample Body:**
    ```json
    {
        "customerId": "FAIL_DELIVERY",
        "restaurantId": "456",
        "items": []
    }
    ```
  * **Expected Response:** A `200 OK` with the message `"Failed to assign delivery partner, order rolled back"`.
### 4\. Order summary

  * **Endpoint:** `GET /order/summary/ABC-123`
  * **Description:** gets summary of order id ABC-123.
  * **Expected Response:**
    ```json
    {
    "delivery": {
        "agent": "Bob",
        "orderId": "ABC-123",
        "status": "Out for Delivery"
    },
    "orderId": "ABC-123",
    "restaurant": {
        "id": "456",
        "cuisine": "Italian",
        "name": "The Food Palace"
    },
    "customer": {
        "address": "123 Main St",
        "name": "John Doe",
        "id": "123"
    }
    }
    ```
