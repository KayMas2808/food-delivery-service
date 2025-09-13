package edu.demo.order.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        String restaurantResponse = restTemplate.postForObject("http://localhost:9003/restaurant/reserve", orderRequest,
                String.class);
        if (!"RESERVED".equals(restaurantResponse)) {
            return "Failed to reserve food";
        }
        System.out.println("Food Reserved");

        String paymentResponse = "SUCCESS";
        if ("FAIL_PAYMENT".equals(customerId)) {
            paymentResponse = "FAILURE";
        }

        if (!"SUCCESS".equals(paymentResponse)) {
            restTemplate.postForObject("http://localhost:9003/restaurant/release", orderRequest, String.class);
            System.out.println("Payment failed, compensating by releasing food.");
            return "Payment failed, order rolled back";
        }
        System.out.println("Payment Successful");

        String deliveryResponse = "ASSIGNED";
        if ("FAIL_DELIVERY".equals(customerId)) {
            deliveryResponse = "FAILURE";
        }

        if (!"ASSIGNED".equals(deliveryResponse)) {
            restTemplate.postForObject("http://localhost:9003/restaurant/release", orderRequest, String.class);

            System.out.println("Delivery assignment failed, compensating by releasing food and refunding payment.");
            return "Failed to assign delivery partner, order rolled back";
        }
        System.out.println("Delivery Partner Assigned");

        return "Order placed successfully!";
    }
    @GetMapping("/summary/{orderId}")
    public Map<String, Object> getOrderSummary(@PathVariable String orderId) {
        System.out.println("Aggregating data for order summary: " + orderId);

        // ideally from a DB
        String customerId = "123";
        String restaurantId = "456";

        // fetch data from multiple services
        Object customerDetails = restTemplate.getForObject("http://localhost:9002/customer/" + customerId, Object.class);
        Object restaurantDetails = restTemplate.getForObject("http://localhost:9003/restaurant/" + restaurantId, Object.class);
        Object deliveryStatus = restTemplate.getForObject("http://localhost:9004/delivery/status/" + orderId, Object.class);

        // aggregate into a single response
        Map<String, Object> summary = new HashMap<>();
        summary.put("orderId", orderId);
        summary.put("customer", customerDetails);
        summary.put("restaurant", restaurantDetails);
        summary.put("delivery", deliveryStatus);

        return summary;
    }
}