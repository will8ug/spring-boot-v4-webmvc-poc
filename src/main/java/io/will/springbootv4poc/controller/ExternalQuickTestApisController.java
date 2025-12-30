package io.will.springbootv4poc.controller;

import io.will.springbootv4poc.service.QuickTestApisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExternalQuickTestApisController {
    private final QuickTestApisService quickTestApisService;

    public ExternalQuickTestApisController(QuickTestApisService quickTestApisService) {
        this.quickTestApisService = quickTestApisService;
    }

    @GetMapping(value = "/external/ping", version = "1")
    public ResponseEntity<String> pingExternal() {
        String response = quickTestApisService.ping();
        return ResponseEntity.ok("External API response: " + response);
    }
}
