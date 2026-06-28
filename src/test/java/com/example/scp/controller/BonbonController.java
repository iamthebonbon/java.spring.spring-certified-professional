package com.example.scp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/bonbon")
public class BonbonController {
    @GetMapping
    public Map<String, String> get() {
        return Map.of("status", "health");
    }
}
