package io.will.springbootv4poc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping(value = "/ping", version = "1")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}

