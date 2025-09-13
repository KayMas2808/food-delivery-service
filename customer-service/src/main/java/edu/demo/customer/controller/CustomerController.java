package edu.demo.customer.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/{id}")
    public Map<String, Object> getCustomerDetails(@PathVariable String id) {
        // In a real application, you would fetch this from a database
        return Map.of("id", id, "name", "John Doe", "address", "123 Main St");
    }
}