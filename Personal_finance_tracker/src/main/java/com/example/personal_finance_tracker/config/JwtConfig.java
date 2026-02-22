package com.example.personal_finance_tracker.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfig {
    private SubConfig access;
    private SubConfig refresh;

    @Data
    public static class SubConfig {
        private String secret;
        private Duration expiration;  // java.time.Duration
    }
}
