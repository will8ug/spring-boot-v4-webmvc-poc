package io.will.springbootv4poc.service;

import io.will.springbootv4poc.client.QuickTestApisClient;
import org.springframework.stereotype.Service;

@Service
public class QuickTestApisService {

    private final QuickTestApisClient quickTestApisClient;

    public QuickTestApisService(QuickTestApisClient quickTestApisClient) {
        this.quickTestApisClient = quickTestApisClient;
    }

    public String ping() {
        return quickTestApisClient.ping();
    }
}
