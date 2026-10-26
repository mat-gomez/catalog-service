package ar.edu.um.catalog.domain.model;

import ar.edu.um.catalog.domain.Professional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProfessionalTest {

    @Test
    void testProfessionalInstantiation() {
        Professional professional = new Professional();
        assertNotNull(professional);
    }
}
