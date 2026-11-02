package ar.edu.um.catalog.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

class SecurityConfigTest {

    @Test
    @DisplayName("Debe crear correctamente el bean de SecurityFilterChain sin errores")
    void testSecurityFilterChainBean() throws Exception {
        SecurityConfig securityConfig = new SecurityConfig();

        HttpSecurity http = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        SecurityFilterChain filterChain = securityConfig.securityFilterChain(http);

        assertNotNull(filterChain);
    }
}