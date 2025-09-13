package edu.demo.restaurant.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @PostMapping("/reserve")
    public String reserveFood(@RequestBody Map<String, Object> orderRequest) {
        System.out.println("Reserving food for order: " + orderRequest);
        return "RESERVED";
    }

    @PostMapping("/release")
    public String releaseFood(@RequestBody Map<String, Object> orderRequest) {
        System.out.println("Releasing food for order: " + orderRequest);
        return "RELEASED";
    }
}