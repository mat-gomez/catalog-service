package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfessionalRepositoryTest {

    @Autowired
    private ProfessionalRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        Instant now = Instant.now();

        ProfessionalEntity p1 = ProfessionalEntity.builder()
                .id(1L)
                .categoryId(10L)
                .firstName("Juan")
                .lastName("Pérez")
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        ProfessionalEntity p2 = ProfessionalEntity.builder()
                .id(2L)
                .categoryId(20L)
                .firstName("María")
                .lastName("Gómez")
                .enabled(false)
                .createdAt(now)
                .updatedAt(now)
                .build();

        repository.saveAll(List.of(p1, p2));
    }

    @Test
    void testFindByFilters_NoFilters() {
        List<ProfessionalEntity> results = repository.findByFilters(null, null, null);
        assertEquals(2, results.size());
    }

    @Test
    void testFindByFilters_ByCategoryId() {
        List<ProfessionalEntity> results = repository.findByFilters(10L, null, null);
        assertEquals(1, results.size());
        assertEquals("Juan", results.get(0).getFirstName());
    }

    @Test
    void testFindByFilters_ByName() {
        List<ProfessionalEntity> results = repository.findByFilters(null, "mar", null);
        assertEquals(1, results.size());
        assertEquals("María", results.get(0).getFirstName());
    }

    @Test
    void testFindByFilters_ByEnabled() {
        List<ProfessionalEntity> results = repository.findByFilters(null, null, true);
        assertEquals(1, results.size());
        assertTrue(results.get(0).getEnabled());
    }
}