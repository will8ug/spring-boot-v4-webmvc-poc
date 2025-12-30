package io.will.springbootv4poc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(group = "external", basePackages = {"io.will.springbootv4poc.client"})
public class HttpClientConfig {
    private static final String QUICK_TEST_APIS_URL = "http://localhost:10001";

    @Bean
    public RestClientHttpServiceGroupConfigurer groupConfigurer() {
        return groups -> groups.filterByName("external")
                .forEachClient((_, builder) ->
                        builder.baseUrl(QUICK_TEST_APIS_URL));
    }
}
