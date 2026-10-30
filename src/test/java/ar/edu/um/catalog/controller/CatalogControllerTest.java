package ar.edu.um.catalog.controller;

import ar.edu.um.catalog.adapters.in.rest.CatalogController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CatalogControllerTest {

    @Autowired
    private CatalogController catalogController;

    @Test
    void contextLoads() {
        assertThat(catalogController).isNotNull();
    }
}