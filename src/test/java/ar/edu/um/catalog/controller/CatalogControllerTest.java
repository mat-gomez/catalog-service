package ar.edu.um.catalog.controller;

import ar.edu.um.catalog.adapters.in.rest.CatalogController;
import ar.edu.um.catalog.ports.in.CatalogSyncUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CatalogControllerTest {

    @Mock
    private CatalogSyncUseCase catalogSyncUseCase;

    @InjectMocks
    private CatalogController catalogController;

    @Test
    void testControllerInstantiation() {
        assertNotNull(catalogController);
    }
}