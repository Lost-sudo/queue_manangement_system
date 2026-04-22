package com.example.queue_management_system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "You are authenticated";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin(){
        return "You are allowed to access this resource";
    }

    @GetMapping("/staff")
    @PreAuthorize("hasRole('STAFF')")
    public String staff(){
        return "You are allowed to access this resource";
    }
}
