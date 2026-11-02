package ar.edu.um.catalog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JacksonConfigTest {

    @Test
    void testObjectMapperBeanCreation() throws Exception {
        JacksonConfig config = new JacksonConfig();
        ObjectMapper mapper = config.objectMapper();

        assertNotNull(mapper);

        String json = mapper.writeValueAsString(LocalTime.of(12, 30));
        assertNotNull(json);

        LocalTime parsedTime = mapper.readValue(json, LocalTime.class);
        assertEquals(LocalTime.of(12, 30), parsedTime);
    }
}