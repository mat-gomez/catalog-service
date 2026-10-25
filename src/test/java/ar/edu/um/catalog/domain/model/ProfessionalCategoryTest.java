package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.ProfessionalCategory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProfessionalCategoryTest {

    @Test
    void testProfessionalCategoryGettersAndSetters() {
        ProfessionalCategory category = new ProfessionalCategory();
        category.setId(1L);
        category.setName("Cardiología");

        assertEquals(1L, category.getId());
        assertEquals("Cardiología", category.getName());
    }
}
