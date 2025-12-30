package io.will.springbootv4poc.client;

import org.springframework.web.service.annotation.GetExchange;

public interface QuickTestApisClient {
    @GetExchange("/ping")
    String ping();
}
