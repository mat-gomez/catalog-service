package ar.edu.um.catalog.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CatalogRepositoryTest {

    @Autowired
    private CatalogSyncStateRepository syncStateRepository;

    @Autowired
    private ProfessionalCategoryRepository categoryRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Test
    void debeGuardarYRecuperarEstadoDeSincronizacion() {
        CatalogSyncStateEntity entity = new CatalogSyncStateEntity();
        entity.setId(1L);
        entity.setCurrentVersion(10L);
        entity.setStatus("OK");
        entity.setLastSyncedAt(Instant.now());

        syncStateRepository.save(entity);
        Optional<CatalogSyncStateEntity> found = syncStateRepository.findById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getCurrentVersion()).isEqualTo(10L);
        assertThat(found.get().getStatus()).isEqualTo("OK");
    }

    @Test
    void debeGuardarCategoriaYProfesionalConExito() {
        ProfessionalCategoryEntity category = new ProfessionalCategoryEntity();
        category.setId(1L);
        category.setName("Cardiología");
        category.setDescription("Especialidad en corazón");
        category.setEnabled(true);
        categoryRepository.save(category);

        ProfessionalEntity professional = new ProfessionalEntity();
        professional.setId(1L);
        professional.setCategoryId(1L);
        professional.setFirstName("Juan");
        professional.setLastName("Pérez");
        professional.setEnabled(true);
        professionalRepository.save(professional);


        var foundProfessional = professionalRepository.findById(1L);

        assertThat(foundProfessional).isPresent();
        assertThat(foundProfessional.get().getFirstName()).isEqualTo("Juan");
        assertThat(foundProfessional.get().getCategoryId()).isEqualTo(1L);
    }
}