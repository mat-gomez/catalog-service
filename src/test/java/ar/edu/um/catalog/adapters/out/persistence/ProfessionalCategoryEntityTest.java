package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProfessionalCategoryEntityTest {

    @Test
    void testGettersAndSetters() {
        ProfessionalCategoryEntity entity = new ProfessionalCategoryEntity();
        Long id = 1L;
        String name = "Cardiología";
        String description = "Especialista en cardiología";
        Boolean enabled = true;
        Instant now = Instant.now();

        entity.setId(id);
        entity.setName(name);
        entity.setDescription(description);
        entity.setEnabled(enabled);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
        assertEquals(enabled, entity.getEnabled());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }

    @Test
    void testAllArgsConstructor() {
        Instant now = Instant.now();
        ProfessionalCategoryEntity entity = new ProfessionalCategoryEntity(1L, "Pediatría", "Atención infantil", false, now, now);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Pediatría", entity.getName());
        assertEquals("Atención infantil", entity.getDescription());
        assertFalse(entity.getEnabled());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }

    @Test
    void testBuilder() {
        Instant now = Instant.now();
        ProfessionalCategoryEntity entity = ProfessionalCategoryEntity.builder()
                .id(2L)
                .name("Neurología")
                .description("Sistema nervioso")
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(entity);
        assertEquals(2L, entity.getId());
        assertEquals("Neurología", entity.getName());
        assertEquals("Sistema nervioso", entity.getDescription());
        assertTrue(entity.getEnabled());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }
}