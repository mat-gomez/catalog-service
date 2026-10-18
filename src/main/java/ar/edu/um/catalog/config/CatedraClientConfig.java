package ar.edu.um.catalog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class CatedraClientConfig {

    @Value("${catedra.api.base-url}")
    private String baseUrl;

    @Value("${catedra.api.jwt-token}")
    private String jwtToken;

    @Bean
    public RestClient catedraRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + jwtToken)
                .build();
    }
}
