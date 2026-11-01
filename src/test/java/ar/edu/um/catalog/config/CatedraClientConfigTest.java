package ar.edu.um.catalog.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CatedraClientConfigTest {

    @Test
    void testCatedraRestClientBeanCreation() {
        CatedraClientConfig config = new CatedraClientConfig();

        ReflectionTestUtils.setField(config, "baseUrl", "https://api.example.com");
        ReflectionTestUtils.setField(config, "jwtToken", "test-token-123");

        RestClient client = config.catedraRestClient();

        assertNotNull(client);
    }
}