package com.zakisupermarket.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    // Fail-fast timeouts so a future real WhatsApp/ETA/payment-gateway client
    // built on this bean can't hang the request thread when the store has no
    // internet - connect is tighter than read since a refused/unreachable host
    // should be declared dead quickly, while a reachable-but-slow API deserves
    // a bit more patience.
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }
}