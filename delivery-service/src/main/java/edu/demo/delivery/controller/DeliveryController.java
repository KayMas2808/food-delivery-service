package edu.demo.delivery.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @PostMapping("/assign")
    public String assignDeliveryPartner(@RequestBody Map<String, Object> orderRequest) {
        System.out.println("Assigning delivery partner for order: " + orderRequest);
        return "ASSIGNED";
    }

    @GetMapping("/status/{orderId}")
    public Map<String, Object> getDeliveryStatus(@PathVariable String orderId) {
        return Map.of("orderId", orderId, "status", "Out for Delivery", "agent", "Bob");
    }
}