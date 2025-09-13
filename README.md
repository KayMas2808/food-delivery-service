# Food Delivery Platform Microservices

demonstration of a food delivery platform built using a microservices architecture., including the API Gateway, Saga, and Aggregator patterns.

## How to Run

1.  **Prerequisites:** Ensure you have Java 17 and Maven installed.
2.  **Make Scripts Executable:** Open a terminal in the project root and run `chmod +x *.sh`.
3.  **Start Services:** Run the appropriate script for your operating system to start all microservices in separate terminal tabs.
      * **For Fedora/KDE:** `./start-services-fedora.sh`
4.  **Stop Services:** To stop all services, run `./stop-services.sh`.

## API Endpoints for Testing

All requests should be sent to the API Gateway at `http://localhost:8080`. You can use the provided Postman collection to test these endpoints.

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
  * [cite\_start]**Description:** Simulates a payment failure, triggering a compensating transaction to roll back the food reservation[cite: 106].
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
  * [cite\_start]**Description:** Simulates a failure to assign a delivery partner, triggering a full rollback of payment and food reservation[cite: 106].
  * **Sample Body:**
    ```json
    {
        "customerId": "FAIL_DELIVERY",
        "restaurantId": "456",
        "items": []
    }
    ```
  * **Expected Response:** A `200 OK` with the message `"Failed to assign delivery partner, order rolled back"`.
