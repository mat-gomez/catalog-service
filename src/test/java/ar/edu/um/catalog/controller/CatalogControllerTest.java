package ar.edu.um.catalog.controller;

import ar.edu.um.catalog.ports.in.CatalogSyncUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogController.class)
class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CatalogSyncUseCase catalogSyncUseCase;

    @Test
    void testTriggerSyncEndpoint() throws Exception {
        mockMvc.perform(post("/api/catalog/sync"))
                .andExpect(status().isOk());
    }
}