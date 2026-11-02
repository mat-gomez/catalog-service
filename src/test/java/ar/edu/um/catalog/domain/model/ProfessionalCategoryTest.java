package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.ProfessionalCategory;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProfessionalCategoryTest {

    @Test
    void testProfessionalCategoryGettersAndSetters() {
        ProfessionalCategory category = new ProfessionalCategory();

        Long id = 1L;
        String name = "Cardiología";
        String description = "Especialista en cardiología clínica";
        Boolean enabled = true;
        Instant now = Instant.now();

        category.setId(id);
        category.setName(name);
        category.setDescription(description);
        category.setEnabled(enabled);
        category.setCreatedAt(now);
        category.setUpdatedAt(now);

        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertEquals(description, category.getDescription());
        assertEquals(enabled, category.getEnabled());
        assertEquals(now, category.getCreatedAt());
        assertEquals(now, category.getUpdatedAt());
    }

    @Test
    void testProfessionalCategoryAllArgsConstructor() {
        Instant now = Instant.now();
        ProfessionalCategory category = new ProfessionalCategory(2L, "Pediatría", "Atención infantil", false, now, now);

        assertNotNull(category);
        assertEquals(2L, category.getId());
        assertEquals("Pediatría", category.getName());
        assertEquals("Atención infantil", category.getDescription());
        assertFalse(category.getEnabled());
        assertEquals(now, category.getCreatedAt());
        assertEquals(now, category.getUpdatedAt());
    }

    @Test
    void testProfessionalCategoryBuilder() {
        Instant now = Instant.now();
        ProfessionalCategory category = ProfessionalCategory.builder()
                .id(3L)
                .name("Neurología")
                .description("Sistema nervioso")
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(category);
        assertEquals(3L, category.getId());
        assertEquals("Neurología", category.getName());
        assertEquals("Sistema nervioso", category.getDescription());
        assertTrue(category.getEnabled());
        assertEquals(now, category.getCreatedAt());
        assertEquals(now, category.getUpdatedAt());
    }
}