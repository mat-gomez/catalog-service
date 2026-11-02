package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.Professional;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProfessionalTest {

    @Test
    void testProfessionalGettersAndSetters() {
        Professional professional = new Professional();

        Long id = 1L;
        Long categoryId = 2L;
        String firstName = "Juan";
        String lastName = "Pérez";
        Boolean enabled = true;
        Instant now = Instant.now();

        professional.setId(id);
        professional.setCategoryId(categoryId);
        professional.setFirstName(firstName);
        professional.setLastName(lastName);
        professional.setEnabled(enabled);
        professional.setCreatedAt(now);
        professional.setUpdatedAt(now);

        assertEquals(id, professional.getId());
        assertEquals(categoryId, professional.getCategoryId());
        assertEquals(firstName, professional.getFirstName());
        assertEquals(lastName, professional.getLastName());
        assertEquals(enabled, professional.getEnabled());
        assertEquals(now, professional.getCreatedAt());
        assertEquals(now, professional.getUpdatedAt());
    }

    @Test
    void testProfessionalAllArgsConstructor() {
        Instant now = Instant.now();
        Professional professional = new Professional(1L, 2L, "María", "Gómez", false, now, now);

        assertNotNull(professional);
        assertEquals(1L, professional.getId());
        assertEquals(2L, professional.getCategoryId());
        assertEquals("María", professional.getFirstName());
        assertEquals("Gómez", professional.getLastName());
        assertFalse(professional.getEnabled());
        assertEquals(now, professional.getCreatedAt());
        assertEquals(now, professional.getUpdatedAt());
    }

    @Test
    void testProfessionalBuilder() {
        Instant now = Instant.now();
        Professional professional = Professional.builder()
                .id(10L)
                .categoryId(5L)
                .firstName("Carlos")
                .lastName("López")
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(professional);
        assertEquals(10L, professional.getId());
        assertEquals(5L, professional.getCategoryId());
        assertEquals("Carlos", professional.getFirstName());
        assertEquals("López", professional.getLastName());
        assertTrue(professional.getEnabled());
        assertEquals(now, professional.getCreatedAt());
        assertEquals(now, professional.getUpdatedAt());
    }
}