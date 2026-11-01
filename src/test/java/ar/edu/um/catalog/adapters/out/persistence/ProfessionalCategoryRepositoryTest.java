package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfessionalCategoryRepositoryTest {

    @Autowired
    private ProfessionalCategoryRepository repository;

    @Test
    void testSaveAndFindById() {
        Instant now = Instant.now();
        ProfessionalCategoryEntity entity = ProfessionalCategoryEntity.builder()
                .id(1L)
                .name("Cardiología")
                .description("Especialista en corazón")
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        repository.save(entity);

        Optional<ProfessionalCategoryEntity> found = repository.findById(1L);

        assertTrue(found.isPresent());
        assertEquals("Cardiología", found.get().getName());
        assertTrue(found.get().getEnabled());
    }
}