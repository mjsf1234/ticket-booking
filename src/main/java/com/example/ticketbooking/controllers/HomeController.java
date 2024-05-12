package com.example.ticketbooking.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String homeController(){
        return ("<h2> welcome </h2>");
    }
}
