package com.example.scp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/")
public class MainController {
    @GetMapping
    public void get(@RequestParam(defaultValue = "test") String test) {
        throw new ResponseStatusException(HttpStatus.OK);
    }
}
