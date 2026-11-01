package ar.edu.um.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class CatalogServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testMainMethod() {
		assertDoesNotThrow(() -> CatalogServiceApplication.main(new String[]{}));
	}
}