package ar.edu.um.catalog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JacksonConfigTest {

    @Test
    void testObjectMapperBeanCreation() {
        JacksonConfig config = new JacksonConfig();
        ObjectMapper mapper = config.objectMapper();

        assertNotNull(mapper);
    }
}