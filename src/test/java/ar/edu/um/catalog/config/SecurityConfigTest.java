package ar.edu.um.catalog.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.mock.web.MockHttpServletRequest; // <-- Importante

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Test
    void testSecurityFilterChainBean() {
        assertThat(securityFilterChain).isNotNull();
    }

    @Test
    void testCorsConfigurationSourceBean() {
        assertThat(corsConfigurationSource).isNotNull();

        // Creamos un request simulado para evitar el NullPointerException
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/catalog");
        assertThat(corsConfigurationSource.getCorsConfiguration(request)).isNotNull();
    }
}