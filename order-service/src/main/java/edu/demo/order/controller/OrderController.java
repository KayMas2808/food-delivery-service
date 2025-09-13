package edu.demo.order.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/place")
    public String placeOrder(@RequestBody Map<String, Object> orderRequest) {
        String customerId = (String) orderRequest.getOrDefault("customerId", "");

        // 1. Reserve food from the restaurant
        String restaurantResponse = restTemplate.postForObject("http://localhost:9003/restaurant/reserve", orderRequest,
                String.class);
        if (!"RESERVED".equals(restaurantResponse)) {
            return "Failed to reserve food";
        }
        System.out.println("Food Reserved");

        // 2. Deduct payment (Simulate failure for customerId "FAIL_PAYMENT")
        String paymentResponse = "SUCCESS";
        if ("FAIL_PAYMENT".equals(customerId)) {
            paymentResponse = "FAILURE";
        }

        if (!"SUCCESS".equals(paymentResponse)) {
            // Compensate: release food reservation
            restTemplate.postForObject("http://localhost:9003/restaurant/release", orderRequest, String.class);
            System.out.println("Payment failed, compensating by releasing food.");
            return "Payment failed, order rolled back";
        }
        System.out.println("Payment Successful");

        // 3. Assign delivery partner (Simulate failure for customerId "FAIL_DELIVERY")
        String deliveryResponse = "ASSIGNED";
        if ("FAIL_DELIVERY".equals(customerId)) {
            deliveryResponse = "FAILURE";
        }

        if (!"ASSIGNED".equals(deliveryResponse)) {
            // Compensate: release food reservation and refund payment
            restTemplate.postForObject("http://localhost:9003/restaurant/release", orderRequest, String.class);
            // refund logic would go here
            System.out.println("Delivery assignment failed, compensating by releasing food and refunding payment.");
            return "Failed to assign delivery partner, order rolled back";
        }
        System.out.println("Delivery Partner Assigned");

        return "Order placed successfully!";
    }
}