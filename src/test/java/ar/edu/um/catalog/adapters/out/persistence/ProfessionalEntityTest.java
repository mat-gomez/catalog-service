package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProfessionalEntityTest {

    @Test
    void testGettersAndSetters() {
        ProfessionalEntity entity = new ProfessionalEntity();
        Long id = 1L;
        Long categoryId = 2L;
        String firstName = "Juan";
        String lastName = "Pérez";
        Boolean enabled = true;
        Instant now = Instant.now();

        entity.setId(id);
        entity.setCategoryId(categoryId);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setEnabled(enabled);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        assertEquals(id, entity.getId());
        assertEquals(categoryId, entity.getCategoryId());
        assertEquals(firstName, entity.getFirstName());
        assertEquals(lastName, entity.getLastName());
        assertEquals(enabled, entity.getEnabled());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }

    @Test
    void testAllArgsConstructor() {
        Instant now = Instant.now();
        ProfessionalEntity entity = new ProfessionalEntity(1L, 2L, "María", "Gómez", false, now, now);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(2L, entity.getCategoryId());
        assertEquals("María", entity.getFirstName());
        assertEquals("Gómez", entity.getLastName());
        assertFalse(entity.getEnabled());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }

    @Test
    void testBuilder() {
        Instant now = Instant.now();
        ProfessionalEntity entity = ProfessionalEntity.builder()
                .id(10L)
                .categoryId(5L)
                .firstName("Carlos")
                .lastName("López")
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(entity);
        assertEquals(10L, entity.getId());
        assertEquals(5L, entity.getCategoryId());
        assertEquals("Carlos", entity.getFirstName());
        assertEquals("López", entity.getLastName());
        assertTrue(entity.getEnabled());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }
}